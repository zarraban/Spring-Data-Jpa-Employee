package org.example.app.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PositionDtoRequest(
        Long id,

        @JsonProperty("position_name")
        String positionName
) {
}
