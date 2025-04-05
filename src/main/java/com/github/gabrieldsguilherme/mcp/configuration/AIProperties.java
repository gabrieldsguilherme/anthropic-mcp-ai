package com.github.gabrieldsguilherme.mcp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ai")
public class AIProperties {

    private Map<String, String> capabilities;

}