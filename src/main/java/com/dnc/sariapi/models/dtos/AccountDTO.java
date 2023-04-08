package com.dnc.sariapi.models.dtos;

import com.dnc.sariapi.models.enums.AccountType;
import com.dnc.sariapi.models.enums.MembershipType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "accounts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @Id
    private String id;
    private String username;
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty("membership_type")
    @Builder.Default
    private MembershipType membershipType = MembershipType.FREE;
    @JsonProperty("account_type")
    @Builder.Default
    private AccountType accountType = AccountType.CUSTOMER;
    @JsonProperty("is_activated")
    @Builder.Default
    private boolean isActivated = false;
    @JsonProperty("is_deleted")
    @Builder.Default
    private boolean isDeleted = false;
    @CreatedDate
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;
    @JsonProperty("deleted_at")
    private LocalDateTime deletedAt;
}
