package com.dazhou.chatroom.common.user.service;

import com.dazhou.chatroom.common.user.domain.vo.req.FriendApplyReq;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendApproveReq;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendCheckReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.FriendCheckResp;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-23 22:23
 * 好友相关service
 */
public interface FriendService {

    /**
     * 检查是否有自己的好友
     * @param uid
     * @param request
     * @return
     */
    FriendCheckResp check(Long uid, FriendCheckReq request);

    /**
     * 申请好友
     * @param uid
     * @param request
     */
    void apply(Long uid, FriendApplyReq request);

    /**
     * 同意好友申请
     *
     * @param uid     uid
     * @param request 请求
     */
    void applyApprove(Long uid, FriendApproveReq request);
}
