package com.example.advert.dao;

import com.example.advert.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问层
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据用户名和激活状态查找用户
     */
    Optional<User> findByUsernameAndIsActiveTrue(String username);
    
    /**
     * 根据角色查找用户
     */
    List<User> findByRole(User.UserRole role);
    
    /**
     * 根据激活状态查找用户
     */
    List<User> findByIsActive(Boolean isActive);
    
    /**
     * 根据用户名模糊查询
     */
    List<User> findByUsernameContainingIgnoreCase(String username);
    
    /**
     * 根据真实姓名模糊查询
     */
    List<User> findByRealNameContainingIgnoreCase(String realName);
    
    /**
     * 根据邮箱模糊查询
     */
    List<User> findByEmailContainingIgnoreCase(String email);
    
    /**
     * 查找最近登录的用户
     */
    @Query("SELECT u FROM User u WHERE u.lastLoginTime >= :since ORDER BY u.lastLoginTime DESC")
    List<User> findRecentlyActiveUsers(@Param("since") LocalDateTime since);
    
    /**
     * 统计各角色用户数量
     */
    @Query("SELECT u.role, COUNT(u) FROM User u WHERE u.isActive = true GROUP BY u.role")
    List<Object[]> countUsersByRole();
    
    /**
     * 查找创建时间在指定范围内的用户
     */
    @Query("SELECT u FROM User u WHERE u.createdTime BETWEEN :startTime AND :endTime ORDER BY u.createdTime DESC")
    List<User> findUsersByCreatedTimeRange(@Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计用户总数
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    Long countActiveUsers();
    
    /**
     * 查找指定时间后注册的用户
     */
    @Query("SELECT u FROM User u WHERE u.createdTime >= :since AND u.isActive = true")
    List<User> findUsersRegisteredAfter(@Param("since") LocalDateTime since);
    
    /**
     * 根据用户名和密码查找用户（用于登录验证）
     */
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.isActive = true")
    Optional<User> findByUsernameAndPassword(@Param("username") String username, 
                                           @Param("password") String password);
    
    /**
     * 查找长时间未登录的用户
     */
    @Query("SELECT u FROM User u WHERE u.lastLoginTime < :since OR u.lastLoginTime IS NULL")
    List<User> findInactiveUsers(@Param("since") LocalDateTime since);
    
    /**
     * 统计各时间段注册的用户数量
     */
    @Query("SELECT CAST(u.createdTime AS date), COUNT(u) FROM User u " +
           "WHERE u.createdTime >= :startDate " +
           "GROUP BY CAST(u.createdTime AS date) " +
           "ORDER BY CAST(u.createdTime AS date)")
    List<Object[]> countUsersByRegistrationDate(@Param("startDate") LocalDateTime startDate);
    
    /**
     * 查找激活的用户
     */
    List<User> findByIsActiveTrue();
    
    /**
     * 根据用户名模糊查询激活用户
     */
    List<User> findByUsernameContainingIgnoreCaseAndIsActiveTrue(String username);
    
    /**
     * 根据邮箱模糊查询激活用户
     */
    List<User> findByEmailContainingIgnoreCaseAndIsActiveTrue(String email);
    
    /**
     * 根据角色查找激活用户
     */
    List<User> findByRoleAndIsActiveTrue(String role);
    
    /**
     * 统计激活用户数量
     */
    Long countByIsActiveTrue();
    
    /**
     * 按角色统计用户数量
     */
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countByRole();
    
    /**
     * 统计最近注册的用户数量（最近7天）
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdTime >= :since")
    Long countRecentUsers(@Param("since") LocalDateTime since);
    
    /**
     * 统计最近注册的用户数量（最近7天）- 默认方法
     */
    default Long countRecentUsers() {
        return countRecentUsers(LocalDateTime.now().minusDays(7));
    }
} 