package com.dazhou.chatroom.common.user.service;

import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.vo.req.BlackReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.BadgeResp;
import com.dazhou.chatroom.common.user.domain.vo.resp.UserInfoResp;

import java.util.List;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2023-12-03
 */
public interface UserService {

    /**
     * 用户注册
     * @param insert
     */
    Long register(User insert);

    /**
     * 获取用户详细信息
     * @param uid
     * @return
     */
    UserInfoResp getUserInfo(Long uid);

    /**
     * 修改用户名
     * @param uid
     * @param name
     */
    void modifyName(Long uid, String name);

    /**
     * 查看用户徽章列表
     * @param uid
     * @return
     */
    List<BadgeResp> badges(Long uid);

    /**
     * 佩戴徽章
     * @param uid
     * @param itemId
     */
    void WearingBadge(Long uid, Long itemId);

    /**
     * 拉黑用户
     * @param req
     */
    void black(BlackReq req);
}
