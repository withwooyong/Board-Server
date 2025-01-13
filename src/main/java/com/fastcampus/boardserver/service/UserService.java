package com.fastcampus.boardserver.service;


import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.UserLoginRequest;
import com.fastcampus.boardserver.dto.request.UserUpdatePasswordRequest;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserProfileMapper userProfileMapper;

    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    public void register(UserDTO payload) {
        boolean duplIdResult = isDuplicatedId(payload.getUserId());
        if (duplIdResult) throw new DuplicateIdException("중복된 아이디입니다.");
        payload.setCreatedAt(LocalDateTime.now());
        payload.setPassword(SHA256Util.encryptSHA256(payload.getPassword()));
        int insertCount = userProfileMapper.register(payload);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", payload);
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + payload);
        }
    }

    public UserDTO login(UserLoginRequest payload) {
        String cryptoPassword = SHA256Util.encryptSHA256(payload.getPassword());
        return userProfileMapper.findByUserIdAndPassword(payload.getUserId(), cryptoPassword);
    }

    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordRequest payload) {
        String cryptoPassword = SHA256Util.encryptSHA256(payload.getPassword());
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(payload.getUserId(), cryptoPassword);
        if (memberInfo == null) {
            log.error("updatePasswrod ERROR! {}", payload);
            throw new IllegalArgumentException("updatePasswrod ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + payload);
        }
        memberInfo.setPassword(SHA256Util.encryptSHA256(payload.getChangePassword()));
        int insertCount = userProfileMapper.updatePassword(memberInfo);
        log.info("updatePassword : {}", insertCount);
    }

    @Transactional
    public void deleteId(String id, String passWord) {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        if (memberInfo == null) {
            log.error("deleteId ERROR! {} {}", id, passWord);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + id);
        }
        int i = userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        log.info("deleteId : {}", i);
    }
}
