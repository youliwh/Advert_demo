package com.example.advert.controller;

import com.example.advert.model.Advert;
import com.example.advert.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 广告控制器
 */
@RestController
@RequestMapping("/api/adverts")
@CrossOrigin(origins = "*")
public class AdvertController {
    
    private final AdvertService advertService;
    
    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }
    
    /**
     * 获取所有广告
     */
    @GetMapping
    public ResponseEntity<List<Advert>> getAllAdverts() {
        List<Advert> adverts = advertService.findAll();
        return ResponseEntity.ok(adverts);
    }
    
    /**
     * 获取所有激活的广告
     */
    @GetMapping("/active")
    public ResponseEntity<List<Advert>> getActiveAdverts() {
        List<Advert> adverts = advertService.findAllActive();
        return ResponseEntity.ok(adverts);
    }
    
    /**
     * 根据ID获取广告
     */
    @GetMapping("/{id}")
    public ResponseEntity<Advert> getAdvertById(@PathVariable Long id) {
        Optional<Advert> advert = advertService.findById(id);
        return advert.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据标题搜索广告
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<Advert>> searchByTitle(@RequestParam String title) {
        List<Advert> adverts = advertService.findByTitle(title);
        return ResponseEntity.ok(adverts);
    }
    
    /**
     * 根据内容关键词搜索广告
     */
    @GetMapping("/search/content")
    public ResponseEntity<List<Advert>> searchByContent(@RequestParam String keyword) {
        List<Advert> adverts = advertService.findByContentKeyword(keyword);
        return ResponseEntity.ok(adverts);
    }
    
    /**
     * 根据标签搜索广告
     */
    @GetMapping("/search/tags")
    public ResponseEntity<List<Advert>> searchByTags(@RequestParam String tag) {
        List<Advert> adverts = advertService.findByTag(tag);
        return ResponseEntity.ok(adverts);
    }
    
    /**
     * 根据扩展属性搜索广告
     */
    @GetMapping("/search/extended-property")
    public ResponseEntity<List<Advert>> searchByExtendedProperty(
            @RequestParam String property, 
            @RequestParam String value) {
        List<Advert> adverts = advertService.findByExtendedProperty(property, value);
        return ResponseEntity.ok(adverts);
    }
    
    /**
     * 创建新广告
     */
    @PostMapping
    public ResponseEntity<Advert> createAdvert(@RequestBody Advert advert) {
        Advert savedAdvert = advertService.save(advert);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvert);
    }
    
    /**
     * 更新广告
     */
    @PutMapping("/{id}")
    public ResponseEntity<Advert> updateAdvert(@PathVariable Long id, @RequestBody Advert advert) {
        Advert updatedAdvert = advertService.update(id, advert);
        if (updatedAdvert != null) {
            return ResponseEntity.ok(updatedAdvert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 删除广告（软删除）
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvert(@PathVariable Long id) {
        boolean deleted = advertService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 硬删除广告
     */
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteAdvert(@PathVariable Long id) {
        boolean deleted = advertService.hardDeleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 激活广告
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateAdvert(@PathVariable Long id) {
        boolean activated = advertService.activateById(id);
        if (activated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Advert Service is running!");
    }
} 