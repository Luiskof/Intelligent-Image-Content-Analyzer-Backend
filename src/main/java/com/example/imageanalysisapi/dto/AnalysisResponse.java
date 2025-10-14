package com.example.imageanalysisapi.dto;

import java.util.List;

public class AnalysisResponse {
    private List<Tag> tags;

    public AnalysisResponse() {
    }

    public AnalysisResponse(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
