package com.accountbook.service;

import java.util.List;
import java.util.stream.Collectors;

import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRankDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 목록 조회
     *
     * @return 카테고리 목록
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryList() throws Exception {

        var result = categoryRepository.findAll().stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());

        return result;
    }

    public List<CategoryRankDto> getCategoryRank(String userId) {
        var rank = CategoryRankDto.rank(categoryRepository.findByUserId(userId));
        return rank;
    }
}