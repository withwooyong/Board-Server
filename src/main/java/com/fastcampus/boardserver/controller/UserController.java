package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.UserDeleteId;
import com.fastcampus.boardserver.dto.request.UserLoginRequest;
import com.fastcampus.boardserver.dto.request.UserUpdatePasswordRequest;
import com.fastcampus.boardserver.dto.response.LoginResponse;
import com.fastcampus.boardserver.dto.response.UserInfoResponse;
import com.fastcampus.boardserver.enums.Status;
import com.fastcampus.boardserver.service.UserService;
import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);

    //    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDTO payload) {
//        if (UserDTO.hasNullDataBeforeSignup(userDTO)) throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다."); // TODO Ted @Valid 로 대체
        userService.register(payload);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/sign-in")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest payload, HttpSession session) {
        UserDTO userInfo = userService.login(payload);
        if (userInfo == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        LoginResponse loginResponse = LoginResponse.success(userInfo);
        if (userInfo.getStatus() == Status.ADMIN) {
            SessionUtil.setLoginAdminId(session, userInfo.getUserId());
        } else { // Status.USER
            SessionUtil.setLoginMemberId(session, userInfo.getUserId());
        }
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/users/my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null) id = SessionUtil.getLoginAdminId(session);
        UserDTO memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("/users/logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @PatchMapping("/users/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserUpdatePasswordRequest payload) {
        userService.updatePassword(payload);
        UserDTO userInfo = userService.login(new UserLoginRequest(payload.getUserId(), payload.getPassword()));
        LoginResponse loginResponse = LoginResponse.success(userInfo);
        return ResponseEntity.ok(loginResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteId(@RequestBody UserDeleteId payload, HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        UserDTO userInfo = userService.login(new UserLoginRequest(id, payload.getPassword()));
        userService.deleteId(id, payload.getPassword());
        LoginResponse loginResponse = LoginResponse.success(userInfo);
        return ResponseEntity.ok(loginResponse);
    }
}