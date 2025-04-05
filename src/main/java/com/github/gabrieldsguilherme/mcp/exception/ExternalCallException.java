package com.github.gabrieldsguilherme.mcp.exception;

import com.github.gabrieldsguilherme.mcp.model.ExternalCallResult;
import lombok.Getter;

@Getter
public class ExternalCallException extends Throwable {

    private final ExternalCallResult result;

    public ExternalCallException(String message, ExternalCallResult result) {
        super(message);
        this.result = result;
    }

}
