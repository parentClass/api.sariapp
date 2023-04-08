package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.dtos.ProductSubCategoryDTO;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.ProductSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@CrossOrigin
@RequestMapping("/product/subcategory")
public class ProductSubCategoryController {
    @Autowired
    private ProductSubCategoryService productSubCategoryService;

    @PostMapping("/create")
    public ResponseEntity<SariBaseResponse> create(@RequestBody ProductSubCategoryDTO productSubCategoryDTO) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productSubCategoryService.create(productSubCategoryDTO))
                        .build());
    }

    @PutMapping("/update")
    public ResponseEntity<SariBaseResponse> update(@RequestParam String id, @RequestBody ProductSubCategoryDTO productSubCategoryDTO)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productSubCategoryService.update(id, productSubCategoryDTO))
                        .build());
    }

    @GetMapping("/list")
    public ResponseEntity<SariBaseResponse> getAllProducts(@RequestParam Integer maxResult, @RequestParam Integer page)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productSubCategoryService.getAllProductSubCategories(maxResult, page))
                        .build());
    }

    @GetMapping
    public ResponseEntity<SariBaseResponse> getProductById(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productSubCategoryService.getSubCategoryById(id))
                        .build());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<SariBaseResponse> remove(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productSubCategoryService.remove(id))
                        .build());
    }

    @PostMapping("/activate")
    public ResponseEntity<SariBaseResponse> activateProduct(@RequestParam String id) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productSubCategoryService.activateSubCategory(id))
                        .build());
    }
}
