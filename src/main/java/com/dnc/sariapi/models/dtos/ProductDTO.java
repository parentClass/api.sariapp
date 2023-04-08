package com.dnc.sariapi.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @Id
    private String id;
    @JsonProperty("merchant_id")
    private String merchantId;
    private String label;
    @JsonProperty("stock_quantity")
    private Integer stockQuantity;
    @JsonProperty("base_price")
    private Double basePrice;
    @JsonProperty("categories")
    private List<String> categories;
    @JsonProperty("sub_categories")
    private List<String> subCategories;
    @JsonProperty("created_time")
    @CreatedDate
    private LocalDateTime createdTime;
    @JsonProperty("modified_time")
    @LastModifiedDate
    private LocalDateTime modifiedTime;
    @JsonProperty("deletedTime")
    private LocalDateTime deletedTime;
    @JsonProperty("is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;
    @JsonProperty("is_activated")
    @Builder.Default
    private Boolean isActivated = false;
}
