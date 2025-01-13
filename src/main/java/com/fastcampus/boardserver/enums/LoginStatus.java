package com.fastcampus.boardserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginStatus {
    SUCCESS,
    FAIL,
    DELETED
}
