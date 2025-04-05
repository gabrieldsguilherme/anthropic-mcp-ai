package com.github.gabrieldsguilherme.mcp.service.aimodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AIModelCapability {

    TECHNICAL_EXPLANATION("technical-explanation");

    private final String propertyName;

}
