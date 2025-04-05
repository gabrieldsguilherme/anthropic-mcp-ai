package com.github.gabrieldsguilherme.mcp.service.aimodel;

import com.github.gabrieldsguilherme.mcp.model.MCPContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GPT4OService implements AIModel {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final WebClient webClient = WebClient.builder().build();

    @Override
    public AIModelName name() {
        return AIModelName.GPT_4O;
    }

    @Override
    public String generateTechnicalExplanation(MCPContext mcp) {
        String prompt = buildPrompt(mcp);

        String response = webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(buildOpenAiRequest(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractAnswerFromResponse(response);
    }

    private String buildPrompt(MCPContext mcp) {
        return String.format("""
            You are a technical assistant that summarizes request processing flow.

            Execution ID: %s
            Name: %s
            Start Time: %s
            End Time: %s
            Duration: %d ms

            Agify API:
            - Success: %s
            - Latency: %d ms
            - Error: %s

            Genderize API:
            - Success: %s
            - Latency: %d ms
            - Error: %s

            Errors captured:
            %s

            Based on this data, provide a concise technical explanation about what happened during the request. 
            Mention delays, exceptions, or suspicious behavior that might help developers understand what went wrong.
            """,
                mcp.getExecutionId(),
                mcp.getInputName(),
                mcp.getStartTime(),
                mcp.getEndTime(),
                mcp.getEndTime().toEpochMilli() - mcp.getStartTime().toEpochMilli(),
                mcp.getAgifyResult().isSuccess(),
                mcp.getAgifyResult().getLatencyMillis(),
                mcp.getAgifyResult().getErrorMessage(),
                mcp.getGenderizeResult().isSuccess(),
                mcp.getGenderizeResult().getLatencyMillis(),
                mcp.getGenderizeResult().getErrorMessage(),
                String.join("\n", mcp.getErrorMessages())
        );
    }

    private String buildOpenAiRequest(String prompt) {
        String escapedPrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n");
        return """
        {
          "model": "gpt-4o",
          "temperature": 0.2,
          "messages": [
            { "role": "system", "content": "You are a technical assistant." },
            { "role": "user", "content": "%s" }
          ]
        }
        """.formatted(escapedPrompt);
    }

    private String extractAnswerFromResponse(String response) {
        int start = response.indexOf("\"content\":\"") + 11;
        int end = response.indexOf("\"", start);
        if (start < 11 || end <= start) return "Unable to extract AI response.";
        return response.substring(start, end)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }
}