package com.github.gabrieldsguilherme.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCallResult {

    private String url;
    private String responseBody;
    private long latencyMillis;
    private boolean success;
    private String errorMessage;

}