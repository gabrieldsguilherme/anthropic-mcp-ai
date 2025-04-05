package com.github.gabrieldsguilherme.mcp.client;

import com.github.gabrieldsguilherme.mcp.exception.ExternalCallException;
import com.github.gabrieldsguilherme.mcp.model.ExternalCallResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AgifyClient {

    private final WebClient webClient = WebClient.builder().build();

    public ExternalCallResult call(String name) throws ExternalCallException {
        String url = "https://api.agify.io/?name=" + name;
        long start = System.currentTimeMillis();

        try {
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            long latency = System.currentTimeMillis() - start;
            return new ExternalCallResult(url, response, latency, true, null);
        } catch (Exception e) {
            long latency = System.currentTimeMillis() - start;
            ExternalCallResult result = new ExternalCallResult(url, null, latency, false, e.getMessage());
            throw new ExternalCallException("Failed to call Agify API", result);
        }
    }

}
