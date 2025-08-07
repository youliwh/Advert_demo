package com.example.advert.service;

import com.example.advert.dao.UserRepository;
import com.example.advert.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户服务层
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    /**
     * 查询所有激活的用户
     */
    public List<User> findAllActive() {
        return userRepository.findByIsActiveTrue();
    }
    
    /**
     * 根据ID查询用户
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * 根据用户名查询
     */
    public List<User> findByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCaseAndIsActiveTrue(username);
    }
    
    /**
     * 根据邮箱查询
     */
    public List<User> findByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCaseAndIsActiveTrue(email);
    }
    
    /**
     * 根据角色查询
     */
    public List<User> findByRole(String role) {
        return userRepository.findByRoleAndIsActiveTrue(role);
    }
    
    /**
     * 保存用户
     */
    public User save(User user) {
        if (user.getId() == null) {
            // 新增时设置创建时间
            user.setCreatedTime(LocalDateTime.now());
        }
        user.setUpdatedTime(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    /**
     * 更新用户
     */
    public User update(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User existing = existingUser.get();
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());
            existing.setRealName(user.getRealName());
            existing.setPhone(user.getPhone());
            existing.setRole(user.getRole());
            existing.setIsActive(user.getIsActive());
            existing.setUpdatedTime(LocalDateTime.now());
            return userRepository.save(existing);
        }
        return null;
    }
    
    /**
     * 根据ID删除用户（软删除）
     */
    public boolean deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existing = user.get();
            existing.setIsActive(false);
            existing.setUpdatedTime(LocalDateTime.now());
            userRepository.save(existing);
            return true;
        }
        return false;
    }
    
    /**
     * 激活用户
     */
    public boolean activateById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existing = user.get();
            existing.setIsActive(true);
            existing.setUpdatedTime(LocalDateTime.now());
            userRepository.save(existing);
            return true;
        }
        return false;
    }
    
    /**
     * 获取用户统计信息
     */
    public Map<String, Object> getUserStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总用户数
        long totalUsers = userRepository.count();
        statistics.put("totalUsers", totalUsers);
        
        // 激活用户数
        long activeUsers = userRepository.countByIsActiveTrue();
        statistics.put("activeUsers", activeUsers);
        
        // 按角色统计
        List<Object[]> roleStats = userRepository.countByRole();
        Map<String, Long> roleStatistics = new HashMap<>();
        for (Object[] result : roleStats) {
            User.UserRole role = (User.UserRole) result[0];
            Long count = (Long) result[1];
            roleStatistics.put(role.name(), count);
        }
        statistics.put("roleStats", roleStatistics);
        
        // 最近注册的用户数（最近7天）
        long recentUsers = userRepository.countRecentUsers();
        statistics.put("recentUsers", recentUsers);
        
        return statistics;
    }
} 