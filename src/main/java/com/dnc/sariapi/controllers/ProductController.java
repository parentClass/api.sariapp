package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.dtos.ProductDTO;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@CrossOrigin
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<SariBaseResponse> create(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productService.create(product))
                        .build());
    }

    @PutMapping("/update")
    public ResponseEntity<SariBaseResponse> update(@RequestParam String id, @RequestBody ProductDTO product)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productService.update(id, product))
                        .build());
    }

    @GetMapping("/list")
    public ResponseEntity<SariBaseResponse> getAllProducts(@RequestParam Integer maxResult, @RequestParam Integer page)
            throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productService.getAllProducts(maxResult, page))
                        .build());
    }

    @GetMapping
    public ResponseEntity<SariBaseResponse> getProductById(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productService.getProductById(id))
                        .build());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<SariBaseResponse> remove(@RequestParam String id) throws ResponseStatusException {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productService.remove(id))
                        .build());
    }

    @PostMapping("/activate")
    public ResponseEntity<SariBaseResponse> activateProduct(@RequestParam String id) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(productService.activateProduct(id))
                        .build());
    }
}
