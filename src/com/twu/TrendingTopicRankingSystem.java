package com.twu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton system to deal with issues regarding trending topic.
 * No display function (Do not interact with user directly).
 */
public class TrendingTopicRankingSystem {
    private final static TrendingTopicRankingSystem trendingTopicRankingSystem = new TrendingTopicRankingSystem();

    private List<TrendingTopic> topics;

    private TrendingTopicRankingSystem() {
        topics = new ArrayList<>();
    }

    public static TrendingTopicRankingSystem getInstance() {
        return trendingTopicRankingSystem;
    }

    public void addTopic(String content) {
        checkTopicValid(content);
        topics.add(new TrendingTopic(content, false));
        rankTopic();
    }

    public void addSuperTopic(String content) {
        checkTopicValid(content);
        topics.add(new TrendingTopic(content, true));
        rankTopic();
    }

    public TrendingTopic searchTopic(String content) {
        TrendingTopic topic = null;
        for(TrendingTopic t : topics) {
            if (t.getContent().equals(content)) topic = t;
        }

        if (topic == null) Utilities.sendError("没有这个热搜哦。");

        return topic;
    }

    public void voteTopic(TrendingTopic topic, int votes) {
        topic.addVote(votes);
        rankTopic();
    }

    public List<TrendingTopic> getTopics() {
        rankTopic();
        return topics;
    }

    public int getTopicsNumb() {
        return topics.size();
    }

    /**
     * Pay for a topic.
     * <p> Range check has been done in UserOperatingSystem payTopic().
     * Check if the topic has been paid in the input ranking.
     * Check if the input ranking has been paid and how much has been paid.
     * If the input ranking has been paid, then the topic in this ranking should be remove.
     * if the input ranking is the last ranking, another range check should be done for index error.
     * Then set the topic in the input ranking.
     * </p>
     * @param topic The topic user want to pay.
     * @param ranking The ranking user want to pay for the topic.
     * @param money Money to pay the ranking for the topic
     */
    public void payTopic(TrendingTopic topic, int ranking, int money) {
        //if (ranking > topics.size()) Utilities.sendError("一共也没有" + ranking +"条热搜！");
        if (topics.get(ranking-1) == topic && topic.getPaid() > 0) Utilities.sendError(topic.getContent() + " 已经被买在这个排名啦，不能重复购买。");
        int currentPaid = getRankingPaid(ranking);
        if (currentPaid >= money) Utilities.sendError("这个位置已经被付了" + currentPaid +"元， 你的钱不够哦。");
        if (currentPaid > 0) topics.remove(ranking-1);

        if (ranking - 1 >= getTopicsNumb()) ranking = getTopicsNumb();

        topic.setPaid(money);
        topic.setRanking(ranking);
        removeTopic(topic);
        topics.add( ranking - 1, topic);

        rankTopic();
    }

    public int getRankingPaid(int ranking) {
        return topics.get(ranking-1).getPaid();
    }

    private void removeTopic(TrendingTopic topic) {
        Iterator<TrendingTopic> iterator = topics.iterator();
        while(iterator.hasNext()) {
            TrendingTopic t = iterator.next();
            if (t.getContent().equals(topic.getContent())) iterator.remove();
        }
    }

    private void checkTopicValid(String content) {
        if (content.length() == 0) Utilities.sendError("热搜内容不能为空！");
        for (TrendingTopic t : topics) {
            if (t.getContent().equals(content)) Utilities.sendError("热搜已经存在了哦。");
        }
    }

    /**
     * Rank topic in topics.
     * <p> Remove the paid topic from topics,
     * and store the paid topic in another list.
     * After topics sorted, insert paid topic to topics according to the paid ranking.
     * Finally, refresh the ranking in topics.
     * </p>
     */
    private void rankTopic() {
        List<TrendingTopic> paidTopic = new ArrayList<>();
        Iterator<TrendingTopic> iterator = topics.iterator();
        while(iterator.hasNext()) {
            TrendingTopic t = iterator.next();
            if (t.getPaid() > 0) {
                paidTopic.add(t);
                iterator.remove();
            }
        }

        Collections.sort(topics);

        for(TrendingTopic t : paidTopic) {
            topics.add(t.getRanking() - 1, t);
        }

        AtomicInteger i = new AtomicInteger(1);
        topics.forEach(t -> t.setRanking(i.getAndIncrement()));
    }
}
