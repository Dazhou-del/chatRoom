package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.exception.BusinessException;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.dao.UserBackpackDao;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.entity.UserBackpack;
import com.dazhou.chatroom.common.user.domain.enums.ItemEnum;
import com.dazhou.chatroom.common.user.domain.vo.resp.UserInfoResp;
import com.dazhou.chatroom.common.user.service.UserService;
import com.dazhou.chatroom.common.user.service.adapter.UserAdapter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-17 22:48
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    @Transactional
    public Long register(User insert) {

        boolean save = userDao.save(insert);
        //todo 用户注册的事件
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
}
