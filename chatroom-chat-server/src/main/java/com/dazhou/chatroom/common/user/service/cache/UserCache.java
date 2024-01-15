package com.dazhou.chatroom.common.user.service.cache;

import com.dazhou.chatroom.common.user.dao.BlackDao;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.dao.UserRoleDao;
import com.dazhou.chatroom.common.user.domain.entity.Black;
import com.dazhou.chatroom.common.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *用户权限缓存
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-15 20:37
 */
@Component
public class UserCache {

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private BlackDao blackDao;

    /**
     * 获取用户权限
     * @param uid
     */
    @Cacheable(cacheNames = "user",key = "'roles:'+#uid")
    public Set<Long> getRoleSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.listByUid(uid);
        return userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());
    }

    /**
     * 返回所有被拉黑的列表
     */
    @Cacheable(cacheNames = "user", key = "'blackList'")
    public Map<Integer, Set<String>> getBlackMap() {
        Map<Integer, List<Black>> collect = blackDao.list().stream().collect(Collectors.groupingBy(Black::getType));
        Map<Integer, Set<String>> result = new HashMap<>(collect.size());
        for (Map.Entry<Integer, List<Black>> entry : collect.entrySet()) {
            result.put(entry.getKey(), entry.getValue().stream().map(Black::getTarget).collect(Collectors.toSet()));
        }
        return result;
    }

    @CacheEvict(cacheNames = "user", key = "'blackList'")
    public Map<Integer, Set<String>> evictBlackMap() {
        return null;
    }

}
