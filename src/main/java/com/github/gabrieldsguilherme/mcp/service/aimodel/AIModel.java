package com.github.gabrieldsguilherme.mcp.service.aimodel;

import com.github.gabrieldsguilherme.mcp.model.MCPContext;

import java.util.List;

public interface AIModel {

    AIModelName name();

    default String generateTechnicalExplanation(MCPContext mcp) {
        throw new UnsupportedOperationException("This AI model does not support technical explanation generation.");
    }

}
