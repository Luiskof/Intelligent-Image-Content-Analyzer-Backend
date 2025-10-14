package com.example.imageanalysisapi.dto.imagga;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ImaggaResult {
    @JsonProperty("tags")
    private List<ImaggaTag> tags;

    public List<ImaggaTag> getTags() {
        return tags;
    }

    public void setTags(List<ImaggaTag> tags) {
        this.tags = tags;
    }
}
