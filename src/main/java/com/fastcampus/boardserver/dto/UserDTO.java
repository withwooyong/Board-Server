package com.fastcampus.boardserver.dto;

import com.fastcampus.boardserver.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @Builder.Default
    private boolean isAdmin = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private boolean isWithDraw = false; // 탈퇴 여부
    private Status status;
    private LocalDateTime updatedAt;

    public static boolean hasNullDataBeforeSignup(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null
                || userDTO.getNickName() == null;
    }
}
