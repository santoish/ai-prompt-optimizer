package com.example.prompt_optimiser.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptResponse {

    private String optimizedPrompt;
    private String detectedRole;
    private String explanation;
    private Long processingTime;

}
