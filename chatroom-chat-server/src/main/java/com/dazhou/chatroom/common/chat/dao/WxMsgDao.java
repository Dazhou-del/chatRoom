package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.WxMsg;
import com.dazhou.chatroom.common.chat.mapper.WxMsgMapper;
import org.springframework.stereotype.Service;

/**
 * 微信消息表 服务实现类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:36
 */
@Service
public class WxMsgDao extends ServiceImpl<WxMsgMapper, WxMsg> {
}
