package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.dto.request.CategoryRequest;
import com.fastcampus.boardserver.enums.SortStatus;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    @PostMapping("/categories")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.register(categoryDTO);
        return ResponseEntity.ok().build();
    }

    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<?> updateCategories(@PathVariable int categoryId, @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDTO);
        return ResponseEntity.ok().build();
    }

    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> updateCategories(@PathVariable int categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
