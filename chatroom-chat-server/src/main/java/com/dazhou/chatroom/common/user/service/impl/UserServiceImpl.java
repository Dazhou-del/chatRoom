package com.dazhou.chatroom.common.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dazhou.chatroom.common.common.annotation.RedisssonLock;
import com.dazhou.chatroom.common.common.event.UserBlackEvent;
import com.dazhou.chatroom.common.common.event.UserRegisterEvent;
import com.dazhou.chatroom.common.common.exception.BusinessException;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.dao.BlackDao;
import com.dazhou.chatroom.common.user.dao.ItemConfigDao;
import com.dazhou.chatroom.common.user.dao.UserBackpackDao;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.*;
import com.dazhou.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.dazhou.chatroom.common.user.domain.enums.ItemEnum;
import com.dazhou.chatroom.common.user.domain.enums.ItemTypeEnum;
import com.dazhou.chatroom.common.user.domain.vo.req.BlackReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.BadgeResp;
import com.dazhou.chatroom.common.user.domain.vo.resp.UserInfoResp;
import com.dazhou.chatroom.common.user.service.UserService;
import com.dazhou.chatroom.common.user.service.adapter.UserAdapter;
import com.dazhou.chatroom.common.user.service.cache.ItemCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-17 22:48
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;
    @Autowired
    private ItemConfigDao itemConfigDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private BlackDao blackDao;

    @Override
    @Transactional
    public Long register(User insert) {

        boolean save = userDao.save(insert);
        //todo 用户注册的事件
        //发布事件
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this,insert));
        return null;
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        //有多少个改名卡就有多少次改名次数
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisssonLock(key = "#uid")
    public void modifyName(Long uid, String name) {
        //看名字是否重复
        User oldUser = userDao.getByName(name);
        //使用断言
        AssertUtil.isEmpty(oldUser,"名字重复了 换个名字把");
        //没使用断言时
/*        if (Objects.nonNull(oldUser)){
            throw new BusinessException("名字重复换个名字把");
        }*/
        //是否拥有改名卡
        UserBackpack modifyNameItem = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(modifyNameItem,"改名卡不够了");
        //使用改名卡
        boolean success = userBackpackDao.useItem(modifyNameItem);
        if (success){
            //改名
            userDao.modifyName(uid,name);
        }
    }

    @Override
    public List<BadgeResp> badges(Long uid) {
        //查询所有徽章
        List<ItemConfig> itemConfigs = itemCache.getByType(ItemTypeEnum.BADGE.getType());
        //查询用户拥有徽章
        List<UserBackpack> backPacks = userBackpackDao.getByItemIds(uid, itemConfigs.stream().map(ItemConfig::getId).collect(Collectors.toList()));
        //查询用户佩戴的徽章
        User user = userDao.getById(uid);
        return UserAdapter.buildBadgeResp(itemConfigs,backPacks,user);
    }

    @Override
    public void WearingBadge(Long uid, Long itemId) {
        //确保有徽章
        UserBackpack firstValidItem = userBackpackDao.getFirstValidItem(uid, itemId);
        //不能为空 为空会报错
        AssertUtil.isNotEmpty(firstValidItem,"您还没有这个徽章 快去获得吧");
        //确保这个物品是徽章
        ItemConfig itemConfig = itemConfigDao.getById(firstValidItem.getItemId());
        AssertUtil.equal(itemConfig.getType(),ItemTypeEnum.BADGE.getType(),"只有徽章才能佩戴");
        //佩戴徽章
        userDao.WearingBadge(uid,itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void black(BlackReq req) {
        String uid = req.getUid();
        Black user = new Black();
        user.setType(BlackTypeEnum.UIP.getId());
        user.setTarget(uid.toString());
        //保存到拉黑表中
        blackDao.save(user);
        //拉黑ip ip有创建时的ip和更新时的ip
        User byId = userDao.getById(uid);
        blackIp(Optional.ofNullable(byId.getIpInfo()).map(IpInfo::getCreateIp).orElse(null));
        blackIp(Optional.ofNullable(byId.getIpInfo()).map(IpInfo::getUpdateIp).orElse(null));
        //发送拉黑事件
        applicationEventPublisher.publishEvent(new UserBlackEvent(this,byId));
    }

    private void blackIp(String ip) {
        if (StrUtil.isBlank(ip)) {
            return;
        }
        try {
            Black user = new Black();
            user.setTarget(ip);
            user.setType(BlackTypeEnum.IP.getId());
            blackDao.save(user);
        } catch (Exception e) {
            log.error("duplicate black ip:{}", ip);
        }
    }
}
