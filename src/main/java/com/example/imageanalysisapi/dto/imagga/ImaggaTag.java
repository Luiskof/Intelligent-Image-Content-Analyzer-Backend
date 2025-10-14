package com.example.imageanalysisapi.dto.imagga;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImaggaTag {
    @JsonProperty("confidence")
    private double confidence;

    @JsonProperty("tag")
    private ImaggaTagDetails tag;

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public ImaggaTagDetails getTag() {
        return tag;
    }

    public void setTag(ImaggaTagDetails tag) {
        this.tag = tag;
    }
}
