package com.thinmoo.cloud.constant;

public class TopicUtils {

    /**
     * 设备上线后，未设备批量订阅主题
     */
    public static final String SUB_BATCH_URL = "http://192.168.0.9:8081/api/v4/mqtt/subscribe_batch";

    public static final String CLIENT_CONNECTED = "client_connected";

    public static final String CLIENT_DISCONNECTED = "client_disconnected";

}
