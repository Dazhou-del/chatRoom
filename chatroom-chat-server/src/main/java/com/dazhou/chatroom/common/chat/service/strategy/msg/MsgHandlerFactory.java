package com.dazhou.chatroom.common.chat.service.strategy.msg;

import com.dazhou.chatroom.common.common.exception.CommonErrorEnum;
import com.dazhou.chatroom.common.common.utils.AssertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-05 18:56
 */
public class MsgHandlerFactory {
    private static final Map<Integer, AbstractMsgHandler> STRATEGY_MAP = new HashMap<>();

    public static void register(Integer code, AbstractMsgHandler strategy) {
        STRATEGY_MAP.put(code, strategy);
    }

    public static AbstractMsgHandler getStrategyNoNull(Integer code) {
        AbstractMsgHandler strategy = STRATEGY_MAP.get(code);
        AssertUtil.isNotEmpty(strategy, CommonErrorEnum.PARAM_INVALID);
        return strategy;
    }
}
