package com.github.gabrieldsguilherme.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeResponse {

    private String name;
    private Integer estimatedAge;
    private String estimatedGender;
    private String technicalExplanation;

}
