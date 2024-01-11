package com.dazhou.chatroom.common.user.service.cache;

import com.dazhou.chatroom.common.user.dao.ItemConfigDao;
import com.dazhou.chatroom.common.user.domain.entity.ItemConfig;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-11 22:05
 */
@Component
public class ItemCache {
    @Autowired
    private ItemConfigDao itemConfigDao;

    /**
     * 根据物品类型获取物品列表
     * @param itemType
     * @return
     */
    @Cacheable(cacheNames = "item",key = "'itemsByType:'+#itemType")
    public List<ItemConfig> getByType(Integer itemType){
        return itemConfigDao.getByType(itemType);
    }
    //@Cacheable 会先开是否有缓存，如果没有才会进入方法内
//    @CachePut 添加缓存
//    @CacheEvict 清除缓存

    @CacheEvict(cacheNames = "item",key = "'itemsByType:'+#itemType")
    public void evictByType(Integer integer){

    }
}
