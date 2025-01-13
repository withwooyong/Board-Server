package com.fastcampus.boardserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortStatus {

    CATEGORIES,
    NEWEST,
    OLDEST,
    HIGHPRICE,
    LOWPRICE,
    GRADE
}
