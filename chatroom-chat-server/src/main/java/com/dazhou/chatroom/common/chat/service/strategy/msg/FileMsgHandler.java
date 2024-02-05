package com.dazhou.chatroom.common.chat.service.strategy.msg;


import com.dazhou.chatroom.common.chat.dao.MessageDao;
import com.dazhou.chatroom.common.chat.domain.entity.Message;
import com.dazhou.chatroom.common.chat.domain.entity.msg.FileMsgDTO;
import com.dazhou.chatroom.common.chat.domain.entity.msg.MessageExtra;
import com.dazhou.chatroom.common.chat.domain.enums.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Description:图片消息
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-05 18:59
 */
@Component
public class FileMsgHandler extends AbstractMsgHandler<FileMsgDTO> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.FILE;
    }

    @Override
    public void saveMsg(Message msg, FileMsgDTO body) {
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        Message update = new Message();
        update.setId(msg.getId());
        update.setExtra(extra);
        extra.setFileMsg(body);
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getFileMsg();
    }

    @Override
    public Object showReplyMsg(Message msg) {
        return "文件:" + msg.getExtra().getFileMsg().getFileName();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[文件]" + msg.getExtra().getFileMsg().getFileName();
    }
}
