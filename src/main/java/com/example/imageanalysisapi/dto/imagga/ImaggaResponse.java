package com.example.imageanalysisapi.dto.imagga;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImaggaResponse {
    @JsonProperty("result")
    private ImaggaResult result;

    public ImaggaResult getResult() {
        return result;
    }

    public void setResult(ImaggaResult result) {
        this.result = result;
    }
}
