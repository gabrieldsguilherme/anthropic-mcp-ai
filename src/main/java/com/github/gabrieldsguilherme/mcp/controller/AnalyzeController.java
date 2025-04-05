package com.github.gabrieldsguilherme.mcp.controller;

import com.github.gabrieldsguilherme.mcp.model.AnalyzeRequest;
import com.github.gabrieldsguilherme.mcp.model.AnalyzeResponse;
import com.github.gabrieldsguilherme.mcp.service.AnalyzerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analyze")
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzerService analyzer;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AnalyzeResponse analyze(@RequestBody AnalyzeRequest request) {
        return analyzer.analyze(request.getName());
    }

}
