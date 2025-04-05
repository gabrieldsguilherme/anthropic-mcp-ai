package com.github.gabrieldsguilherme.mcp.service;

import com.github.gabrieldsguilherme.mcp.client.AgifyClient;
import com.github.gabrieldsguilherme.mcp.client.GenderizeClient;
import com.github.gabrieldsguilherme.mcp.exception.ExternalCallException;
import com.github.gabrieldsguilherme.mcp.model.AnalyzeResponse;
import com.github.gabrieldsguilherme.mcp.model.ExternalCallResult;
import com.github.gabrieldsguilherme.mcp.model.MCPContext;
import com.github.gabrieldsguilherme.mcp.service.aimodel.AIModel;
import com.github.gabrieldsguilherme.mcp.service.aimodel.AIModelCapability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyzerService {

    private final AgifyClient agifyClient;
    private final GenderizeClient genderizeClient;
    private final AIService AIService;

    public AnalyzeResponse analyze(String name) {
        MCPContext mcp = new MCPContext();
        mcp.setExecutionId(UUID.randomUUID().toString());
        mcp.setInputName(name);
        mcp.setStartTime(Instant.now());

        try {
            ExternalCallResult agify = agifyClient.call(name);
            mcp.setAgifyResult(agify);
        } catch (ExternalCallException e) {
            mcp.setAgifyResult(e.getResult());
            mcp.addError("Agify API failed: " + e.getMessage());
        }

        try {
            ExternalCallResult genderize = genderizeClient.call(name);
            mcp.setGenderizeResult(genderize);
        } catch (ExternalCallException e) {
            mcp.setAgifyResult(e.getResult());
            mcp.addError("Genderize API failed: " + e.getMessage());
        }

        AIModel aiModel = AIService.getInstance(AIModelCapability.TECHNICAL_EXPLANATION);

        mcp.setEndTime(Instant.now());
        mcp.setTechnicalExplanation(aiModel.generateTechnicalExplanation(mcp));

        return AnalyzeResponse.builder()
                .name(name)
                .estimatedAge(null)
                .estimatedGender(null)
                .technicalExplanation(mcp.getTechnicalExplanation())
                .build();
    }

}
