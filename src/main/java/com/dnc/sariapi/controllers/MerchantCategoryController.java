package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.dtos.MerchantCategoryDTO;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.MerchantCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@CrossOrigin
@RequestMapping("/merchant/category")
public class MerchantCategoryController {
    @Autowired
    private MerchantCategoryService merchantCategoryService;

    @PostMapping("/create")
    public ResponseEntity<SariBaseResponse> create(@RequestBody MerchantCategoryDTO merchantCategoryDTO) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantCategoryService.create(merchantCategoryDTO))
                        .build());
    }

    @PutMapping("/update")
    public ResponseEntity<SariBaseResponse> update(@RequestParam String id, @RequestBody MerchantCategoryDTO categoryDTO)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantCategoryService.update(id, categoryDTO))
                        .build());
    }

    @GetMapping("/list")
    public ResponseEntity<SariBaseResponse> getAllMerchantCategories(@RequestParam Integer maxResult, @RequestParam Integer page)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantCategoryService.getAllMerchantCategories(maxResult, page))
                        .build());
    }

    @GetMapping
    public ResponseEntity<SariBaseResponse> getCategoryById(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantCategoryService.getCategoryById(id))
                        .build());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<SariBaseResponse> remove(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantCategoryService.remove(id))
                        .build());
    }

    @PostMapping("/activate")
    public ResponseEntity<SariBaseResponse> activateProduct(@RequestParam String id) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantCategoryService.activateCategory(id))
                        .build());
    }
}
