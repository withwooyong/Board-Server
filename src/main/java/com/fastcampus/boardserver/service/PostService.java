package com.fastcampus.boardserver.service;


import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;

    @CacheEvict(value = "getProducts", allEntries = true)
    public void register(PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(String.valueOf(postDTO.getId())); // TODO Ted exist query 로 변경되어야 할 듯.
        if (memberInfo == null) throw new RuntimeException("register ERROR! 상품 등록 메서드를 확인해주세요\n" + "Params : " + postDTO);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreatedAt(LocalDateTime.now());
        postMapper.register(postDTO);
    }

    public List<PostDTO> getMyProducts(int accountId) {
        return postMapper.selectMyProducts(accountId);
    }

    @Transactional
    public void updateProducts(PostDTO postDTO) {
        postMapper.updateProducts(postDTO);
    }

    @Transactional
    public void deleteProduct(int userId, int productId) {
        if (userId != 0 && productId != 0) {
            postMapper.deleteProduct(productId);
        } else {
            log.error("deleteProudct ERROR! {}", productId);
            throw new RuntimeException("updateProducts ERROR! 물품 삭제 메서드를 확인해주세요\n" + "Params : " + productId);
        }
    }
}
