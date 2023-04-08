package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.ProductSubCategoryDTO;
import com.dnc.sariapi.repositories.MerchantRepository;
import com.dnc.sariapi.repositories.ProductSubCategoryRepository;
import com.dnc.sariapi.services.ProductSubCategoryService;
import com.dnc.sariapi.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static com.dnc.sariapi.utils.Extension.getMerchantId;
import static com.dnc.sariapi.utils.Validation.isMissing;

@Service
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService {
    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public ProductSubCategoryDTO create(ProductSubCategoryDTO productSubCategoryDTO) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // set product merchant id
        productSubCategoryDTO.setMerchantId(merchantId);

        return productSubCategoryRepository.save(productSubCategoryDTO);
    }

    @Override
    public Page<ProductSubCategoryDTO> getAllProductSubCategories(Integer maxResult, Integer page) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // page
        Pageable pageable = PageRequest.of(page, maxResult);

        return productSubCategoryRepository.findAllByMerchantId(merchantId, pageable);
    }

    @Override
    public ProductSubCategoryDTO getSubCategoryById(String subCategoryId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // check if id is missing
        if (isMissing(subCategoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory id is missing");
        }

        return productSubCategoryRepository.findBySubCategoryIdAndMerchantId(subCategoryId, merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory is not existing"));
    }

    @Override
    public ProductSubCategoryDTO update(String subCategoryId, ProductSubCategoryDTO body) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // check if id is missing
        if (isMissing(subCategoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory id is missing");
        }

        ProductSubCategoryDTO currentSubCategory =
                productSubCategoryRepository.findBySubCategoryIdAndMerchantId(subCategoryId, merchantId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory is not existing"));

        if (!isMissing(body.getLabel()) && !body.getLabel().equals(currentSubCategory.getLabel())) {
            currentSubCategory.setLabel(body.getLabel());
        }

        // set modified time
        currentSubCategory.setModifiedTime(LocalDateTime.now());

        return productSubCategoryRepository.save(currentSubCategory);
    }

    @Override
    public ProductSubCategoryDTO remove(String subCategoryId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        ProductSubCategoryDTO subCategory =
                productSubCategoryRepository.findBySubCategoryIdAndMerchantId(subCategoryId, merchantId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory is non existent"));

        // delete product record
        subCategory.setDeletedTime(LocalDateTime.now());
        subCategory.setIsDeleted(true);
        subCategory.setIsActivated(false);
        subCategory.setModifiedTime(LocalDateTime.now());

        return productSubCategoryRepository.save(subCategory);
    }

    @Override
    public ProductSubCategoryDTO activateSubCategory(String subCategoryId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        ProductSubCategoryDTO subCategory =
                productSubCategoryRepository.findBySubCategoryIdAndMerchantId(subCategoryId, merchantId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory is non existent"));

        if (Boolean.TRUE.equals(subCategory.getIsActivated())) {
            throw new ResponseStatusException(HttpStatus.OK, "Subcategory is already activated");
        }

        // activate product
        subCategory.setIsActivated(true);

        // set modified time
        subCategory.setModifiedTime(LocalDateTime.now());

        return productSubCategoryRepository.save(subCategory);
    }
}
