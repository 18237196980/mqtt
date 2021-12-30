package com.thinmoo.cloud.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * mqtt配置类，获取mqtt连接
 */
@Component
@Configuration
@ConfigurationProperties(MqttConfiguration.PREFIX)
@Slf4j
public class MqttConfiguration {

    @Autowired
    private MqttPushClient mqttPushClient;
    //指定配置文件application-local.properties中的属性名前缀
    public static final String PREFIX = "my.mqtt";
    private String host;
    private String clientId;
    private String userName;
    private String password;
    private String topic;
    private int timeout;
    private int keepAlive;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getKeepalive() {
        return keepAlive;
    }

    public void setKeepalive(int keepalive) {
        this.keepAlive = keepalive;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 连接至mqtt服务器，获取mqtt连接
     * @return
     */
    @Bean
    public MqttPushClient getMqttPushClient() {
        //连接至mqtt服务器，获取mqtt连接
        mqttPushClient.connect(host, clientId, userName, password, timeout, keepAlive);
        log.info("连接至mqtt服务器，获取mqtt连接");
        //一连接mqtt,就订阅默认需要订阅的主题
        new MqttSubClient(mqttPushClient);
        return mqttPushClient;
    }
}