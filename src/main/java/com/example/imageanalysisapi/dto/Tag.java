package com.example.imageanalysisapi.dto;

public class Tag {
    private String label;
    private double confidence;

    public Tag() {
    }

    public Tag(String label, double confidence) {
        this.label = label;
        this.confidence = confidence;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
