package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.ProductCategoryDTO;
import com.dnc.sariapi.models.dtos.ProductSubCategoryDTO;
import com.dnc.sariapi.repositories.MerchantRepository;
import com.dnc.sariapi.repositories.ProductCategoryRepository;
import com.dnc.sariapi.repositories.ProductSubCategoryRepository;
import com.dnc.sariapi.services.ProductCategoryService;
import com.dnc.sariapi.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static com.dnc.sariapi.utils.Extension.getMerchantId;
import static com.dnc.sariapi.utils.Validation.isMissing;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public ProductCategoryDTO create(ProductCategoryDTO productCategoryDTO) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // set product merchant id
        productCategoryDTO.setMerchantId(merchantId);

        return productCategoryRepository.save(productCategoryDTO);
    }

    @Override
    public Page<ProductCategoryDTO> getAllProductCategories(Integer maxResult, Integer page) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // page
        Pageable pageable = PageRequest.of(page, maxResult);

        return productCategoryRepository.findAllByMerchantId(merchantId, pageable);
    }

    @Override
    public ProductCategoryDTO getCategoryById(String categoryId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // check if id is missing
        if (isMissing(categoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category id is missing");
        }

        return productCategoryRepository.findByCategoryIdAndMerchantId(categoryId, merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is not existing"));
    }

    @Override
    public ProductCategoryDTO update(String categoryId, ProductCategoryDTO body) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // check if id is missing
        if (isMissing(categoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category id is missing");
        }

        ProductCategoryDTO currentCategory = productCategoryRepository.findByCategoryIdAndMerchantId(categoryId, merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is not existing"));


        if (!isMissing(body.getLabel()) && !body.getLabel().equals(currentCategory.getLabel())) {
            currentCategory.setLabel(body.getLabel());
        }

        if (body.getSubCategories() != null && !body.getSubCategories().isEmpty()) {
            // list all subcategories for the merchant
            List<String> subCategories = productSubCategoryRepository.findAllByMerchantId(merchantId)
                    .stream().map(ProductSubCategoryDTO::getId).toList();

            // get subcategories not existing in the saved subcategory ids for the product
            List<String> newSubCategories = body.getSubCategories().stream().filter(e ->
                    !currentCategory.getSubCategories().contains(e)).toList();

            // check if there's new subcategories added
            if (!newSubCategories.isEmpty()) {
                // get added subcategories that are not existing
                List<String> unrecognizedSubCategories =
                        newSubCategories.stream().filter(e -> !subCategories.contains(e)).toList();

                // there should be no unrecognized subcategories or throw an error
                if (!unrecognizedSubCategories.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Non existent sub-category was provided");
                }

                // add all new categories to existing associated categories
                currentCategory.getSubCategories().addAll(newSubCategories);
            }
        }

        // set modified time
        currentCategory.setModifiedTime(LocalDateTime.now());

        return productCategoryRepository.save(currentCategory);
    }

    @Override
    public ProductCategoryDTO remove(String categoryId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        ProductCategoryDTO category = productCategoryRepository.findByCategoryIdAndMerchantId(categoryId, merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is non existent"));

        // delete product record
        category.setDeletedTime(LocalDateTime.now());
        category.setIsDeleted(true);
        category.setIsActivated(false);
        category.setModifiedTime(LocalDateTime.now());

        return productCategoryRepository.save(category);
    }

    @Override
    public ProductCategoryDTO activateCategory(String categoryId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        ProductCategoryDTO category = productCategoryRepository.findByCategoryIdAndMerchantId(categoryId, merchantId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is non existent"));

        if (Boolean.TRUE.equals(category.getIsActivated())) {
            throw new ResponseStatusException(HttpStatus.OK, "Category is already activated");
        }

        // activate product
        category.setIsActivated(true);

        // set modified time
        category.setModifiedTime(LocalDateTime.now());

        return productCategoryRepository.save(category);
    }
}
