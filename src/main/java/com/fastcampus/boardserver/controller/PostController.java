package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.PostDeleteRequest;
import com.fastcampus.boardserver.dto.request.PostRequest;
import com.fastcampus.boardserver.service.PostService;
import com.fastcampus.boardserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final UserService userService;

    //    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    @PostMapping("/posts")
    public ResponseEntity<?> registerPost(@RequestBody PostDTO postDTO) {
        postService.register(postDTO);
        return ResponseEntity.ok().build(); // TODO Ted : ResponseEntity 로 리턴하고 있는데 CommonResponse 로 한번 더 감싸는게 맞지 않다고 판단됨. 논의 필요
//        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);
//        return ResponseEntity.ok(commonResponse);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @GetMapping("/posts/my-posts")
    public ResponseEntity<?> myPostInfo(@RequestParam String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyProducts(memberInfo.getId());
        return ResponseEntity.ok(postDTOList);
//        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);
//        return ResponseEntity.ok(commonResponse);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<?> updatePosts(@RequestParam String accountId, @PathVariable int postId, @RequestBody PostRequest postRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updatedAt(LocalDateTime.now())
                .build();
        postService.updateProducts(postDTO);
        return ResponseEntity.ok(postDTO);
//        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", postDTO);
//        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("/posts/{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<?> deleteposts(String accountId, @PathVariable int postId, @RequestBody PostDeleteRequest postDeleteRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deleteProduct(memberInfo.getId(), postId);
        return ResponseEntity.ok(postDeleteRequest);
//        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteposts", postDeleteRequest);
//        return ResponseEntity.ok(commonResponse);
    }
}
