package com.magicmond.global.numerologyreportservice.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CareerInsights {
    private List<String> recommendedFields;
    private List<String> strengths;
    private String advice;
}
