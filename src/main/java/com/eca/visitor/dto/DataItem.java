package com.eca.visitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataItem {
    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("owner")
    private OwnerDTO owner;

    @JsonProperty("vendor")
    private VendorDTO vendor;

    @JsonProperty("tenant")
    private TenantDTO tenant;

    @JsonProperty("userPhoneNumber")
    private long userPhoneNumber;

    @JsonProperty("accountNonExpired")
    private boolean accountNonExpired;

    @JsonProperty("accountNonLocked")
    private boolean accountNonLocked;

    @JsonProperty("credentialsNonExpired")
    private boolean credentialsNonExpired;

    @JsonProperty("enabled")
    private boolean enabled;

}
