package com.eca.visitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespDTO {
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("data")
    private List<DataItem> data;

}


