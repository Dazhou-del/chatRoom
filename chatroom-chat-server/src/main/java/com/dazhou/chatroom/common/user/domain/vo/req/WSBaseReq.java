package com.dazhou.chatroom.common.user.domain.vo.req;


import com.dazhou.chatroom.common.user.domain.enums.WSReqTypeEnum;
import lombok.Data;

/**
 * @author dazhou
 * @title
 * @create 2023-11-30 22:50
 */
@Data
public class WSBaseReq {
    /**
     * @see WSReqTypeEnum;
     */
    private Integer type;
    private String data;
}
