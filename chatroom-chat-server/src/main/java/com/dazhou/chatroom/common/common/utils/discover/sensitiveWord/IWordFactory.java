package com.dazhou.chatroom.common.common.utils.discover.sensitiveWord;

import java.util.List;

/**
 * 敏感词
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-09 21:55
 */
public interface IWordFactory {
    /**
     * 返回敏感词数据源
     *
     * @return 结果
     * @since 0.0.13
     */
    List<String> getWordList();
}
