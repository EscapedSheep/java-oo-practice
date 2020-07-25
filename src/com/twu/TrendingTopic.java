package com.twu;

public class TrendingTopic implements Comparable<TrendingTopic>{
    private int ranking;
    private String content;
    private int votes;
    private Boolean isSuperTrending;

    public TrendingTopic(int ranking, String content, Boolean isSuperTrending) {
        this.ranking = ranking;
        this.content = content;
        this.votes = 0;
        this.isSuperTrending = isSuperTrending;
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

    public Boolean getSuperTrending() {
        return isSuperTrending;
    }

    public void setSuperTrending(Boolean superTrending) {
        isSuperTrending = superTrending;
    }

    public int getVotes() {
        return votes;
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
        if (this.votes < t.getVotes()) return 1;
        if (this.votes > t.getVotes()) return -1;
        return 0;
    }
}
