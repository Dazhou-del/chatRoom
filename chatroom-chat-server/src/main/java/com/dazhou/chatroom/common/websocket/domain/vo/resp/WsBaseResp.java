package com.dazhou.chatroom.common.websocket.domain.vo.resp;


import com.dazhou.chatroom.common.websocket.domain.enums.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dazhou
 * @title
 * @create 2023-11-30 22:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsBaseResp<T> {
    /**
     * @see WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
