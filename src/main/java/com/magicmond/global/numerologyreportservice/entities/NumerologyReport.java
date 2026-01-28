package com.magicmond.global.numerologyreportservice.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NumerologyReport {
    private ReportMetadata reportMetadata;
    private LifePath lifePath;
    private List<PersonalityTrait> personalityTraits;
    private CareerInsights careerInsights;
    private LuckFactors luckFactors;
}

