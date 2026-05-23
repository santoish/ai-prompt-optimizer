package com.example.prompt_optimiser.controller;

import com.example.prompt_optimiser.dto.PromptRequest;
import com.example.prompt_optimiser.dto.PromptResponse;
import com.example.prompt_optimiser.service.OpenAIService;
import com.example.prompt_optimiser.service.PromptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prompts")
@CrossOrigin(origins = "http://localhost:3000")
public class PromptController {

    @Autowired
    private PromptService promptService;

    @PostMapping("/optimize")
    public ResponseEntity<PromptResponse> optimize(
            @RequestBody @Valid PromptRequest request
            ){
        PromptResponse response = promptService.optimize(request);
        return ResponseEntity.ok(response);
    }
}
