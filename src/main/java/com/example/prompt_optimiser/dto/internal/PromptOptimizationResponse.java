package com.example.prompt_optimiser.dto.internal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptOptimizationResponse {

    private String optimizedPrompt;
    private String explanation;

}
