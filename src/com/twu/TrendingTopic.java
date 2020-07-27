package com.twu;

public class TrendingTopic implements Comparable<TrendingTopic>{
    private int ranking;
    private String content;
    private int votes;
    private Boolean isSuperTrending;
    // Indicate if the topic has been paid for staying in the ranking.
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

    /**
     * Add vote for a topic. Votes for super topic should be double.
     * @param votes the number of vote add to topic.
     */
    public void addVote(int votes) {
        if (isSuperTrending) {
            this.votes += 2 * votes;
        } else {
            this.votes += votes;
        }
    }

    /**
     * To determine which topic should be in the above of another.
     * If any topic has been paid, then the ranking should not change.
     * If not topic has been paid, then compare their votes.
     * @param t Another topic to compare with.
     * @return 1 means another topic should be in the above of this topic.
     */

    @Override
    public int compareTo(TrendingTopic t) {
        if (this.paid > 0 || t.getPaid() > 0) return 0;
        if (this.votes < t.getVotes()) return 1;
        if (this.votes > t.getVotes()) return -1;
        return 0;
    }
}
