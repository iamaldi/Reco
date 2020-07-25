package com.reco.service.model;

public class RecommendedUserModel extends UserModel {
    private int similarityMatch;

    public RecommendedUserModel(String name, String username, int mSimilarityMatch) {
        super(name, username);
        this.similarityMatch = mSimilarityMatch;
    }

    public int getSimilarityMatch() {
        return similarityMatch;
    }

    public void setSimilarityMatch(int similarityMatch) {
        this.similarityMatch = similarityMatch;
    }
}
