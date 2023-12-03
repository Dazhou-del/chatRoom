package com.dazhou.chatroom.common.user.domain.vo.resp;

import com.dazhou.chatroom.common.user.domain.enums.WSRespTypeEnum;

/**
 * @author dazhou
 * @title
 * @create 2023-11-30 22:54
 */
public class WsBaseResp<T> {
    /**
     * @see WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
