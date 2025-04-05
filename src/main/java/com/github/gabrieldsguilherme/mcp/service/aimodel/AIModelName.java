package com.github.gabrieldsguilherme.mcp.service.aimodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum AIModelName {

    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_4O("gpt-4o");

    private final String name;

    public static Optional<AIModelName> fromName(String name) {
        return Stream.of(AIModelName.values())
                .filter(modelName -> modelName.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
