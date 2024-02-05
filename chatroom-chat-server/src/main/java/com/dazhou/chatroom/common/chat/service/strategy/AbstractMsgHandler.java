package com.dazhou.chatroom.common.chat.service.strategy;

import cn.hutool.core.bean.BeanUtil;
import com.dazhou.chatroom.common.chat.dao.MessageDao;
import com.dazhou.chatroom.common.chat.domain.entity.Message;
import com.dazhou.chatroom.common.chat.domain.enums.MessageTypeEnum;
import com.dazhou.chatroom.common.chat.domain.vo.request.ChatMessageReq;
import com.dazhou.chatroom.common.chat.service.adapter.MessageAdapter;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;

/**
 * 消息处理器抽象类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-05 18:59
 */

public abstract class AbstractMsgHandler<Req> {
    @Resource
    private MessageDao messageDao;
    private Class<Req> bodyClass;

    @PostConstruct
    private void init() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.bodyClass = (Class<Req>) genericSuperclass.getActualTypeArguments()[0];
        MsgHandlerFactory.register(getMsgTypeEnum().getType(), this);
    }

    /**
     * 消息类型
     */
    abstract MessageTypeEnum getMsgTypeEnum();

    protected void checkMsg(Req body, Long roomId, Long uid) {

    }

    @Transactional
    public Long checkAndSaveMsg(ChatMessageReq request, Long uid) {
        Req body = this.toBean(request.getBody());
        //统一校验
        AssertUtil.allCheckValidateThrow(body);
        //子类扩展校验
        checkMsg(body, request.getRoomId(), uid);
        Message insert = MessageAdapter.buildMsgSave(request, uid);
        //统一保存
        messageDao.save(insert);
        //子类扩展保存
        saveMsg(insert, body);
        return insert.getId();
    }

    private Req toBean(Object body) {
        //如果传的class是这个 那么就返回这个类型
        if (bodyClass.isAssignableFrom(body.getClass())) {
            return (Req) body;
        }
        return BeanUtil.toBean(body, bodyClass);
    }

    protected abstract void saveMsg(Message message, Req body);

    /**
     * 展示消息
     */
    public abstract Object showMsg(Message msg);

    /**
     * 被回复时——展示的消息
     */
    public abstract Object showReplyMsg(Message msg);

    /**
     * 会话列表——展示的消息
     */
    public abstract String showContactMsg(Message msg);

}

