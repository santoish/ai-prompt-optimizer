package com.example.prompt_optimiser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptRequest {
    @NotBlank
    @Length(min = 5, max = 500)
    private String roughPrompt;
}
