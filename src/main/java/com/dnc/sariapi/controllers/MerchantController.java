package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.dtos.MerchantDTO;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @PostMapping("/create")
    public ResponseEntity<SariBaseResponse> create(@RequestBody MerchantDTO merchantDTO) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantService.create(merchantDTO))
                        .build());
    }

    @RequestMapping("/list")
    public ResponseEntity<SariBaseResponse> getAllMerchant(@RequestParam Integer maxResult, @RequestParam Integer page) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantService.list(maxResult, page))
                        .build());
    }

    @GetMapping
    public ResponseEntity<SariBaseResponse> findById(@RequestParam String id) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(merchantService.findById(id))
                        .build());
    }
}
