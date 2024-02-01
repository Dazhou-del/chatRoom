package com.dazhou.chatroom.common.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 记录和前端连接的一些映射信息
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-01 23:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSChannelExtraDTO {
    /**
     * 前端如果登录了，记录uid
     */
    private Long uid;
}
