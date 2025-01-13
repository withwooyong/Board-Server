package com.fastcampus.boardserver.dto.response;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {

    private LoginStatus result;
    private UserDTO userDTO;

//    @Builder.Default
//    private final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL, null);

    public static LoginResponse success(UserDTO userDTO) {
        return new LoginResponse(LoginStatus.SUCCESS, userDTO);
    }

    public static LoginResponse fail(UserDTO userDTO) {
        return new LoginResponse(LoginStatus.FAIL, userDTO);
    }
}
