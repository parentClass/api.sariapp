package com.dnc.sariapi.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Builder
@AllArgsConstructor
public class AuthenticateResponse implements Serializable {
    /**
     * serial version uid
     */
    private static final long serialVersionUID = -8091879091924046844L;

    @NonNull
    private final String token;
    @NonNull
    @JsonProperty("issued_at")
    private final Date issuedAt;
    @NonNull
    @JsonProperty("valid_until")
    private final Date validUntil;
}