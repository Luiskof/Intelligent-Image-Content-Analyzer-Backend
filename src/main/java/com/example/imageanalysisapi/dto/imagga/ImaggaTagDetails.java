package com.example.imageanalysisapi.dto.imagga;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImaggaTagDetails {
    @JsonProperty("en")
    private String en;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
