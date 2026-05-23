package com.example.prompt_optimiser.service;

import com.example.prompt_optimiser.client.OpenAIclient;
import com.example.prompt_optimiser.dto.PromptRequest;
import com.example.prompt_optimiser.dto.PromptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

    @Autowired
    private OpenAIclient openAIclient;

    private String roleAnalysisSystemPrompt = "You are an Top 1% expert prompt role classifier.\n" +
            "\n" +
            "Your ONLY job is to read a user's prompt and return the single most relevant professional role required to fulfill it.\n" +
            "\n" +
            "CLASSIFICATION RULES:\n" +
            "- Analyze the core intent, not surface-level keywords\n" +
            "- Return the most SPECIFIC role possible (e.g. \"Data Engineer\" not just \"Developer\")\n" +
            "- If multiple roles apply, return the DOMINANT one\n" +
            "- Never explain, never ask questions, never add punctuation\n" +
            "\n" +
            "ROLE EXAMPLES:\n" +
            "- \"build a REST API in Java\"           → Backend Developer\n" +
            "- \"write a haiku about loneliness\"     → Poet\n" +
            "- \"create a pitch deck for investors\"  → Business Strategist\n" +
            "- \"fix this SQL query\"                 → Database Administrator\n" +
            "- \"explain how black holes form\"       → Physics Educator\n" +
            "- \"design a mobile onboarding screen\"  → UI/UX Designer\n" +
            "- \"write a cold outreach email\"        → Copywriter\n" +
            "- \"analyze this financial statement\"   → Financial Analyst\n" +
            "- \"plan a 7-day diet for weight loss\"  → Nutritionist\n" +
            "- \"debug this React component\"         → Frontend Developer\n" +
            "\n" +
            "User prompt: [USER_PROMPT_HERE]\n" +
            "\n" +
            "Respond with ONLY the role name. One line. No explanation. No punctuation.";

    private String promptOptimizationSystemPrompt = "You are a world-class prompt engineer with deep expertise in the {ROLE} domain.\n" +
            "\n" +
            "You have spent years studying what separates average prompts from elite ones:\n" +
            "specificity, context, constraints, and role-aligned vocabulary.\n" +
            "\n" +
            "Your task is to transform a vague, generic prompt into a precision-engineered\n" +
            "prompt that extracts top 1% outputs from any AI model — outputs that are\n" +
            "specific, structured, and immediately actionable for a {ROLE}.\n" +
            "\n" +
            "OPTIMIZATION RULES (apply ALL of these):\n" +
            "\n" +
            "1. ROLE INJECTION     — Open with \"You are an expert {ROLE}...\" to prime the model\n" +
            "2. DOMAIN VOCABULARY  — Replace vague words with {ROLE}-specific terminology\n" +
            "3. OUTPUT FORMAT      — Specify exactly what the output must look like\n" +
            "                        (e.g. numbered list, JSON, step-by-step, table)\n" +
            "4. CONSTRAINTS        — Add scope, length, tone, and boundary conditions\n" +
            "5. CONTEXT ANCHORING  — Add assumed context a {ROLE} would expect\n" +
            "6. SUCCESS CRITERIA   — Define what a perfect response looks like\n" +
            "7. ANTI-VAGUENESS     — Remove filler words: \"good\", \"nice\", \"helpful\", \"explain\"\n" +
            "                        Replace them with measurable or specific equivalents\n" +
            "\n" +
            "Original prompt : {USER_PROMPT}\n" +
            "Target role     : {ROLE}\n" +
            "\n" +
            "Return EXACTLY this structure. No deviation. No extra commentary.\n" +
            "\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "OPTIMIZED PROMPT:\n" +
            "[Your fully rewritten, elite-level prompt here]\n" +
            "\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "WHAT CHANGED:\n" +
            "- [Change 1 — what was vague, what replaced it and why]\n" +
            "- [Change 2 — what terminology was added and why]\n" +
            "- [Change 3 — what constraint or format was injected]\n" +
            "- [Change 4 — what context was assumed and added]\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "\n" +
            "Keep \"WHAT CHANGED\" to exactly 4 bullet points.\n" +
            "Each bullet: one specific change + one-line reason.\n" +
            "No generic praise. No filler. No summary sentence at the end.";


    public PromptResponse optimize(PromptRequest request){
        long startTime = System.currentTimeMillis();
        String userPrompt = request.getRoughPrompt();
        String role = analyzePrompt(userPrompt);
        String optimizedPrompt = optimizeWithRole(userPrompt,role);
        long processingTime = System.currentTimeMillis() - startTime;
        PromptResponse response = buildResponse(
                optimizedPrompt,
                role,
                processingTime
        );
        return response;
    }

    private String analyzePrompt(String prompt){
        String finalSystemPrompt = roleAnalysisSystemPrompt.replace("[USER_PROMPT_HERE]",prompt);
        String role = openAIclient.detectRole(finalSystemPrompt);
        return role;
    }

    private String optimizeWithRole(String prompt,String role){
        String finalSystemPrompt = promptOptimizationSystemPrompt.replace("{USER_PROMPT}",prompt)
                .replace("{ROLE}",role);

        String optimized = openAIclient.optimizePrompt(finalSystemPrompt);
        return optimized;
    }

    private PromptResponse buildResponse(String optimizedPrompt, String role, long processingTime){
        PromptResponse response = new PromptResponse();
        response.setOptimizedPrompt(optimizedPrompt);
        response.setDetectedRole(role);
        response.setExplanation("Optimized for " + role +
                " with domain-specific terminology and clear structure");
        response.setProcessingTime(processingTime);
        return response;
    }
}
