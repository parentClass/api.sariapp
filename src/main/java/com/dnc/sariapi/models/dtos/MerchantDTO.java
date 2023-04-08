package com.dnc.sariapi.models.dtos;

import com.dnc.sariapi.models.enums.MembershipType;
import com.dnc.sariapi.models.enums.MerchantType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "merchants")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO {
    @Id
    private String id;
    @NotNull(message = "Please provide an account id")
    @Indexed(unique = true)
    @JsonProperty("account_id")
    private String accountId;
    @NotNull(message = "Please provide an owner name")
    @JsonProperty("owner_name")
    private String ownerName;
    @NotNull(message = "Please provide an owner address")
    @JsonProperty("owner_address")
    private String ownerAddress;
    @NotNull(message = "Please provide an owner contact")
    @JsonProperty("owner_contact")
    private String ownerContact;
    @NotNull(message = "Please provide a store name")
    @JsonProperty("merchant_name")
    private String merchantName;
    @NotNull(message = "Please provide a store contact")
    @JsonProperty("merchant_contact")
    private String merchantContact;
    @NotNull(message = "Please provide a store address")
    @JsonProperty("merchant_address")
    private String merchantAddress;
    @NotNull(message = "Please provide a store region")
    @JsonProperty("merchant_region")
    private String merchantRegion;
    @Builder.Default
    @JsonProperty("merchant_type")
    private MerchantType merchantType = MerchantType.SARIOWNER;
    @Builder.Default
    @JsonProperty("total_income")
    private Double totalIncome = 0.0;
    @Builder.Default
    @JsonProperty("average_income")
    private Double averageIncome = 0.0;
    @Builder.Default
    @JsonProperty("customer_count")
    private Integer customerCount = 0;
    @Builder.Default
    @JsonProperty("average_traffic")
    private Integer averageTraffic = 0;
    @Builder.Default
    @JsonProperty("total_visits")
    private Integer totalVisits = 0;
    @JsonProperty("sku_id")
    private String skuId;
    @Builder.Default
    @JsonProperty("membership_type")
    private MembershipType membershipType = MembershipType.FREE;
    @JsonProperty("membership_start")
    private LocalDateTime membershipStart;
    @JsonProperty("membership_end")
    private LocalDateTime membershipEnd;
    @JsonProperty("last_sync")
    @Builder.Default
    private LocalDateTime lastSync = LocalDateTime.now();
    @CreatedDate
    @JsonProperty("created_time")
    private LocalDateTime createdTime;
    @LastModifiedDate
    @JsonProperty("modified_time")
    private LocalDateTime modifiedTime;
    @JsonProperty("deleted_time")
    private LocalDateTime deletedTime;
    @Builder.Default
    @JsonProperty("is_deleted")
    private Boolean isDeleted = false;
    @Builder.Default
    @JsonProperty("is_activated")
    private Boolean isActivated = false;
}
