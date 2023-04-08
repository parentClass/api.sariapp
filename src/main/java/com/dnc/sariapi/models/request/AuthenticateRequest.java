package com.dnc.sariapi.models.request;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequest implements Serializable {
    /**
     * serial version uid
     */
    private static final long serialVersionUID = 5926468583005150707L;

    @NonNull
    private String username;
    @NonNull
    private String password;
}