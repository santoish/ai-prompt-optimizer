package com.example.prompt_optimiser.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromptOptimizationRequest {
    private String prompt;
    private String role;

}
