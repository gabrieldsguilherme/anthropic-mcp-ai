package com.github.gabrieldsguilherme.mcp.service.aimodel;

import com.github.gabrieldsguilherme.mcp.model.MCPContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GPT35TurboService implements AIModel {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final WebClient webClient = WebClient.builder().build();

    @Override
    public AIModelName name() {
        return AIModelName.GPT_3_5_TURBO;
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
            You are an assistant that generates technical summaries about request processing.

            Execution ID: %s
            Name: %s
            Start: %s
            End: %s
            Duration: %d ms

            Agify API call:
            - Success: %s
            - Latency: %d ms
            - Error: %s

            Genderize API call:
            - Success: %s
            - Latency: %d ms
            - Error: %s

            Errors captured:
            %s

            Please summarize what happened during this request, highlighting issues, slowness, or exceptions in plain technical language.
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
          "model": "gpt-3.5-turbo",
          "temperature": 0.2,
          "messages": [
            {"role": "system", "content": "You are a technical assistant."},
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(escapedPrompt);
    }

    private String extractAnswerFromResponse(String response) {
        int start = response.indexOf("\"content\":\"") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"");
    }
}