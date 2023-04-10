package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.MerchantCategoryDTO;
import com.dnc.sariapi.repositories.MerchantCategoryRepository;
import com.dnc.sariapi.repositories.MerchantRepository;
import com.dnc.sariapi.services.MerchantCategoryService;
import com.dnc.sariapi.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static com.dnc.sariapi.utils.Validation.isMissing;

/**
 * TODO: remove some functionalities on production, merchant categorization are
 * defined by administrator
 */
@Service
public class MerchantCategoryServiceImpl implements MerchantCategoryService {
    @Autowired
    private MerchantCategoryRepository merchantCategoryRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public MerchantCategoryDTO create(MerchantCategoryDTO merchantCategoryDTO) {
        return merchantCategoryRepository.save(merchantCategoryDTO);
    }

    @Override
    public Page<MerchantCategoryDTO> getAllMerchantCategories(Integer maxResult, Integer page) {
        // page
        Pageable pageable = PageRequest.of(page, maxResult);

        return merchantCategoryRepository.findAll(pageable);
    }

    @Override
    public MerchantCategoryDTO getCategoryById(String categoryId) {
        // check if id is missing
        if (isMissing(categoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category id is missing");
        }

        return merchantCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is not existing"));
    }

    @Override
    public MerchantCategoryDTO update(String categoryId, MerchantCategoryDTO body) {
        // check if id is missing
        if (isMissing(categoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category id is missing");
        }

        MerchantCategoryDTO currentCategory = merchantCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is not existing"));

        if (!isMissing(body.getLabel()) && !body.getLabel().equals(currentCategory.getLabel())) {
            currentCategory.setLabel(body.getLabel());
        }

        // set modified time
        currentCategory.setModifiedTime(LocalDateTime.now());

        return merchantCategoryRepository.save(currentCategory);
    }

    @Override
    public MerchantCategoryDTO remove(String categoryId) {
        MerchantCategoryDTO category = merchantCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is non existent"));

        // delete product record
        category.setDeletedTime(LocalDateTime.now());
        category.setIsDeleted(true);
        category.setIsActivated(false);
        category.setModifiedTime(LocalDateTime.now());

        return merchantCategoryRepository.save(category);
    }

    @Override
    public MerchantCategoryDTO activateCategory(String categoryId) {
        MerchantCategoryDTO category = merchantCategoryRepository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is non existent"));

        if (Boolean.TRUE.equals(category.getIsActivated())) {
            throw new ResponseStatusException(HttpStatus.OK, "Category is already activated");
        }

        // activate product
        category.setIsActivated(true);

        // set modified time
        category.setModifiedTime(LocalDateTime.now());

        return merchantCategoryRepository.save(category);
    }
}
