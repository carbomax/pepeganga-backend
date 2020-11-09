package uy.com.pepeganga.business.common.utils.enums;

public enum  NotificationTopic {

    ORDERS_V2("orders_v2");

    private String topicName;

    NotificationTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
