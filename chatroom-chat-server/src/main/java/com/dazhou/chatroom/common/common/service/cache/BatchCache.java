package com.dazhou.chatroom.common.common.service.cache;

import java.util.List;
import java.util.Map;

/**
 * 批量缓存接口
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-01 23:43
 */
public interface BatchCache<IN, OUT> {
    /**
     * 获取单个
     */
    OUT get(IN req);

    /**
     * 获取批量
     */
    Map<IN, OUT> getBatch(List<IN> req);

    /**
     * 修改删除单个
     */
    void delete(IN req);

    /**
     * 修改删除多个
     */
    void deleteBatch(List<IN> req);
}
