package com.twu;

public class TrendingTopic implements Comparable<TrendingTopic>{
    private int ranking;
    private String content;
    private int votes;
    private Boolean isSuperTrending;
    private int paid;

    public TrendingTopic(String content, Boolean isSuperTrending) {
        this.content = content;
        this.votes = 0;
        this.isSuperTrending = isSuperTrending;
        this.paid = 0;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getContent() {
        return content;
    }

    public int getVotes() {
        return votes;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public void addVote(int votes) {
        if (isSuperTrending) {
            this.votes += 2 * votes;
        } else {
            this.votes += votes;
        }
    }

    @Override
    public int compareTo(TrendingTopic t) {
        if (this.paid > 0 || t.getPaid() > 0) return 0;
        if (this.votes < t.getVotes()) return 1;
        if (this.votes > t.getVotes()) return -1;
        return 0;
    }
}
