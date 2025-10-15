package com.example.imageanalysisapi.dto.imagga;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImaggaTagDetails {
    @JsonProperty("es")
    private String es;

    public String getEs() {
        return es;
    }

    public void setEn(String es) {
        this.es = es;
    }
}
