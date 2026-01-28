package com.magicmond.global.numerologyreportservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magicmond.global.numerologyreportservice.entities.NumerologyReport;
import com.magicmond.global.numerologyreportservice.entities.OrderInfo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class NumerologyReportService {

    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;

    public NumerologyReportService(ChatClient chatClient){
        this.objectMapper = new ObjectMapper();
        this.chatClient = chatClient;
    }


    public static final String NUMEROLOGY_PROMPT =
            "You are a professional Numerology Expert. Generate a deeply personalized report.\n\n" +
                    "USER DATA:\n" +
                    "Name: %s\n" +
                    "Date of Birth: %s\n\n" +
                    "INSTRUCTIONS:\n" +
                    "1. Calculate the Life Path Number using the standard reduction method.\n" +
                    "2. Analyze the name for Expression and Soul Urge insights.\n" +
                    "3. Provide the response ONLY in valid JSON format. No markdown, no triple backticks, no conversational text.\n\n" +
                    "JSON STRUCTURE:\n" +
                    "{\n" +
                    "  \"report_metadata\": { \"user_name\": \"string\", \"calculation_date\": \"ISO-8601\" },\n" +
                    "  \"life_path\": { \"number\": integer, \"description\": \"string\" },\n" +
                    "  \"personality_traits\": [ { \"trait\": \"string\", \"explanation\": \"string\" } ],\n" +
                    "  \"career_insights\": { \"recommended_fields\": [\"string\"], \"strengths\": [\"string\"], \"advice\": \"string\" },\n" +
                    "  \"luck_factors\": { \"lucky_numbers\": [integer], \"lucky_colors\": [\"string\"], \"favorable_days\": [\"string\"] }\n" +
                    "}";

    public NumerologyReport generateReport(OrderInfo orderInfo) {


        String json = chatClient.prompt(
                String.format(NUMEROLOGY_PROMPT, orderInfo.getFullName(), orderInfo.getDob())).call().content();
        System.out.println(json);
        try {
            return objectMapper.readValue(json, NumerologyReport.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Gemini response", e);
        }
    }
}
