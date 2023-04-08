package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.ProductCategoryDTO;
import com.dnc.sariapi.models.dtos.ProductDTO;
import com.dnc.sariapi.models.dtos.ProductSubCategoryDTO;
import com.dnc.sariapi.repositories.MerchantRepository;
import com.dnc.sariapi.repositories.ProductCategoryRepository;
import com.dnc.sariapi.repositories.ProductRepository;
import com.dnc.sariapi.repositories.ProductSubCategoryRepository;
import com.dnc.sariapi.services.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public ProductDTO create(ProductDTO body) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        if (body.getStockQuantity() == null) {
            body.setStockQuantity(0);
        }

        // set store id of product
        body.setMerchantId(merchantId);

        // retrieve all category under the store id
        List<String> categories = productCategoryRepository.findAllByMerchantId(merchantId)
                .stream().map(ProductCategoryDTO::getId).toList();

        // check category id is existing to the store id
        body.getCategories().forEach(categoryId -> {
            if (categories.stream().noneMatch(i -> i.equals(categoryId))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category provided is non existent");
            }
        });

        if (!body.getSubCategories().isEmpty()) {
            // check sub category id
            body.getSubCategories().forEach(subCategoryId -> {
                if (categories.stream().anyMatch(i -> i.equals(subCategoryId))
                        && (!productSubCategoryRepository.existsById(subCategoryId))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory provided is non existent");
                }
            });
        }

        return productRepository.save(body);
    }

    @Override
    public ProductDTO update(String productId, ProductDTO body) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // check if id is missing
        if (isMissing(productId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id is missing");
        }

        ProductDTO currentProductRecord = productRepository.findByProductIdAndMerchantId(productId, merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is not existing"));

        /**
         * allowable fields for modification
         * label
         * stockQuantity
         * basePrice
         * categories
         * subCategories
         */

        if (!isMissing(body.getLabel()) && !body.getLabel().equals(currentProductRecord.getLabel())) {
            currentProductRecord.setLabel(body.getLabel());
        }

        if (body.getStockQuantity() != null && !body.getStockQuantity().equals(currentProductRecord.getStockQuantity())) {
            currentProductRecord.setStockQuantity(body.getStockQuantity());
        }

        if (body.getBasePrice() != null && !body.getBasePrice().equals(currentProductRecord.getBasePrice())) {
            currentProductRecord.setBasePrice(body.getBasePrice());
        }

        if (body.getCategories() != null && !body.getCategories().isEmpty()) {
            // list all categories for the merchant
            List<String> categories = productCategoryRepository.findAllByMerchantId(merchantId)
                    .stream().map(ProductCategoryDTO::getId).toList();

            // get categories not existing in the saved category ids for the product
            List<String> newCategories = body.getCategories().stream().filter(e ->
                    !currentProductRecord.getCategories().contains(e)).toList();

            // check if there's new categories added
            if (!newCategories.isEmpty()) {
                // get added categories that are not existing
                List<String> unrecognizedCategories =
                        newCategories.stream().filter(e -> !categories.contains(e)).toList();

                // there should be no unrecognized categories or throw an error
                if (!unrecognizedCategories.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Non existent category was provided");
                }

                // add all new categories to existing associated categories
                currentProductRecord.getCategories().addAll(newCategories);
            }
        }

        if (body.getSubCategories() != null && !body.getSubCategories().isEmpty()) {
            // list all subcategories for the merchant
            List<String> subCategories = productSubCategoryRepository.findAllByMerchantId(merchantId)
                    .stream().map(ProductSubCategoryDTO::getId).toList();

            // get subcategories not existing in the saved subcategory ids for the product
            List<String> newSubCategories = body.getSubCategories().stream().filter(e ->
                    !currentProductRecord.getSubCategories().contains(e)).toList();

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
                currentProductRecord.getSubCategories().addAll(newSubCategories);
            }
        }

        // set modified time
        currentProductRecord.setModifiedTime(LocalDateTime.now());

        return productRepository.save(currentProductRecord);
    }

    @Override
    public Page<ProductDTO> getAllProducts(Integer maxResult, Integer page) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        // pageable
        Pageable pageable = PageRequest.of(page, maxResult);

        return productRepository.findAllByMerchantId(merchantId, pageable);
    }

    @Override
    public ProductDTO getProductById(String productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not existing"));
    }

    @Override
    public ProductDTO remove(String productId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        ProductDTO product = productRepository.findByProductIdAndMerchantId(productId, merchantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is not existing"));

        // delete product record
        product.setDeletedTime(LocalDateTime.now());
        product.setIsDeleted(true);
        product.setIsActivated(false);
        product.setModifiedTime(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public ProductDTO activateProduct(String productId) {
        // get account id from token
        String accountId = jwtToken.getAccountId();
        String merchantId = getMerchantId(merchantRepository, accountId);

        ProductDTO product = productRepository.findByProductIdAndMerchantId(productId, merchantId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is non existent"));

        if (Boolean.TRUE.equals(product.getIsActivated())) {
            throw new ResponseStatusException(HttpStatus.OK, "Product is already activated");
        }

        // activate product
        product.setIsActivated(true);

        // set modified time
        product.setModifiedTime(LocalDateTime.now());

        return productRepository.save(product);
    }
}
