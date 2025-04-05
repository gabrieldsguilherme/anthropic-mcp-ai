package com.github.gabrieldsguilherme.mcp.service;

import com.github.gabrieldsguilherme.mcp.configuration.AIProperties;
import com.github.gabrieldsguilherme.mcp.service.aimodel.AIModel;
import com.github.gabrieldsguilherme.mcp.service.aimodel.AIModelCapability;
import com.github.gabrieldsguilherme.mcp.service.aimodel.AIModelName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final AIProperties aiProperties;

    private final List<AIModel> aiModels;

    public AIModel getInstance(AIModelCapability capability) {
        String aiModelName = aiProperties.getCapabilities().get(capability.getPropertyName());
        AIModelName modelName = AIModelName.fromName(aiModelName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AI model name: " + aiModelName));

        return aiModels.stream()
                .filter(model -> model.name().equals(modelName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("AI model not found: " + modelName));
    }

}