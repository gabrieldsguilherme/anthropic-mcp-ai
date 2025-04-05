package com.github.gabrieldsguilherme.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPContext {

    private String executionId;
    private String inputName;

    private Instant startTime;
    private Instant endTime;

    private ExternalCallResult agifyResult;
    private ExternalCallResult genderizeResult;

    private List<String> errorMessages = new ArrayList<>();

    private String technicalExplanation;

    public void addError(String error) {
        if (errorMessages == null) {
            errorMessages = new ArrayList<>();
        }
        errorMessages.add(error);
    }
}