package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.dtos.ProductCategoryDTO;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@CrossOrigin
@RequestMapping("/product/category")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping("/create")
    public ResponseEntity<SariBaseResponse> create(@RequestBody ProductCategoryDTO productCategoryDTO) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productCategoryService.create(productCategoryDTO))
                        .build());
    }

    @PutMapping("/update")
    public ResponseEntity<SariBaseResponse> update(@RequestParam String id, @RequestBody ProductCategoryDTO categoryDTO)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productCategoryService.update(id, categoryDTO))
                        .build());
    }

    @GetMapping("/list")
    public ResponseEntity<SariBaseResponse> getAllProducts(@RequestParam Integer maxResult, @RequestParam Integer page)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productCategoryService.getAllProductCategories(maxResult, page))
                        .build());
    }

    @GetMapping
    public ResponseEntity<SariBaseResponse> getProductById(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productCategoryService.getCategoryById(id))
                        .build());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<SariBaseResponse> remove(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productCategoryService.remove(id))
                        .build());
    }

    @PostMapping("/activate")
    public ResponseEntity<SariBaseResponse> activateProduct(@RequestParam String id) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productCategoryService.activateCategory(id))
                        .build());
    }
}
