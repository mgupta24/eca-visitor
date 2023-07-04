package com.eca.visitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitorMessageDTO extends BaseRequest{

    private String userFirstName;

    private String userLastName;

    @NotEmpty
    private String userEmailId;

    private String purposeOfVisiting;

    @NotEmpty
    private String visitorRequestId;

}
