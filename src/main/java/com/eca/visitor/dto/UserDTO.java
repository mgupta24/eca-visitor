package com.eca.visitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userFirstName;
    private String userLastName;
    private String userEmailId;
    private int apartmentId;
    private String apartmentName;

}
