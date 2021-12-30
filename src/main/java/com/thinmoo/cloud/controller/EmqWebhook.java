package com.thinmoo.cloud.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.thinmoo.cloud.constant.TopicUtils;
import com.thinmoo.cloud.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 在当前项目中实现webhook，从而来监听设备上线、下线
 * <p>
 * 注意： 参考：https://docs.emqx.cn/broker/v4.3/advanced/proxy-subscriptions.html#%E5%BC%80%E5%90%AF%E4%BB%A3%E7%90%86%E8%AE%A2%E9%98%85%E5%8A%9F%E8%83%BD
 * 1. emqx\etc\plugins-webhook配置web转发路径
 * 2. 重启emqx
 * 3. 重启网页插件中的webhook
 */
@Slf4j
@RestController
@RequestMapping("/emq/webhook")
public class EmqWebhook {

    @Autowired
    TopicService topicService;

    @PostMapping("/receive")
    public void receiveWebHookData(@RequestBody Map<String, Object> param) {
        log.info("=========监听到客户端活动信息=========");
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            log.info(key + ":" + value);
        }
        log.info("=========以上是接收的所有信息=========\n");

        String action = (String) param.get("action");
        String clientId = (String) param.get("clientid");

        if (action.equals(TopicUtils.CLIENT_CONNECTED)) {
            if (!clientId.startsWith("server")) {
                log.info("=========默认为设备订阅主题=========\n");
                // 默认为设备订阅主题
                ThreadUtil.execute(() -> {
                    try {
                        Thread.sleep(300);
                        topicService.batchSubTopic(clientId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            log.info("=========设备已下线=========\n");
        }
    }

}
