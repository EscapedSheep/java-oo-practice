package com.twu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TrendingTopicRankingSystem {
    private List<TrendingTopic> topics;

    public TrendingTopicRankingSystem() {
        topics = new ArrayList<>();
    }

    public void addTopic(String content) {
        checkTopicValid(content);
        topics.add(new TrendingTopic(topics.size() + 1, content, false));
        rankTopic();
    }

    public void addSuperTopic(String content) {
        checkTopicValid(content);
        topics.add(new TrendingTopic(topics.size() + 1, content, true));
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

    private void checkTopicValid(String content) {
        if (content.length() == 0) Utilities.sendError("热搜内容不能为空！");
        for (TrendingTopic t : topics) {
            if (t.getContent().equals(content)) Utilities.sendError("热搜已经存在了哦。");
        }
    }

    private void rankTopic() {
        Collections.sort(topics);
        AtomicInteger i = new AtomicInteger(1);
        topics.forEach(t -> t.setRanking(i.getAndIncrement()));
    }
}
