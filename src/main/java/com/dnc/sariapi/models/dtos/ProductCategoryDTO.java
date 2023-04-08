package com.dnc.sariapi.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "product_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryDTO {
    @Id
    private String id;
    @NotNull
    @Indexed(unique = true)
    private String label;
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("sub_categories")
    private List<String> subCategories;
    @JsonProperty("created_time")
    @CreatedDate
    private LocalDateTime createdTime;
    @JsonProperty("modified_time")
    @LastModifiedDate
    private LocalDateTime modifiedTime;
    @JsonProperty("deleted_time")
    private LocalDateTime deletedTime;
    @JsonProperty("is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;
    @JsonProperty("is_activated")
    @Builder.Default
    private Boolean isActivated = false;
}