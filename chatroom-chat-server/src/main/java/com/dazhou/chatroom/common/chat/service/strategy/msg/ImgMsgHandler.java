package com.dazhou.chatroom.common.chat.service.strategy.msg;



import com.dazhou.chatroom.common.chat.dao.MessageDao;
import com.dazhou.chatroom.common.chat.domain.entity.Message;
import com.dazhou.chatroom.common.chat.domain.entity.msg.ImgMsgDTO;
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
public class ImgMsgHandler extends AbstractMsgHandler<ImgMsgDTO> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.IMG;
    }

    @Override
    public void saveMsg(Message msg, ImgMsgDTO body) {
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        Message update = new Message();
        update.setId(msg.getId());
        update.setExtra(extra);
        extra.setImgMsgDTO(body);
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getImgMsgDTO();
    }

    @Override
    public Object showReplyMsg(Message msg) {
        return "图片";
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[图片]";
    }
}
