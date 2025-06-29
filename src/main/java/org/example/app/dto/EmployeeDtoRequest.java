package org.example.app.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeDtoRequest(
        Long id,
        String firstName,
        String lastName,
        Long positionId,
        String phone
) {
}
