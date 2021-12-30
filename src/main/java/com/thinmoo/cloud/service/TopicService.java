package com.thinmoo.cloud.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.thinmoo.cloud.constant.TopicUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TopicService {

    /**
     * 设备上线后，为设备订阅默认的topic
     *
     * @param clientid
     */
    public void batchSubTopic(String clientid) {
        List<Map<String, Object>> subList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Map<String, Object> item = new HashMap<>();
            String topic = "/practice/mqtt" + i;
            item.put("topic", topic);
            item.put("qos", 0);
            item.put("clientid", clientid);
            log.info("设备clientid：{}已上线，为设备订阅的topic:{}", clientid, topic);
            subList.add(item);
        }
        log.info("批量订阅subList={}", subList);
        // 批量订阅
        String url = TopicUtils.SUB_BATCH_URL;
        HttpRequest req = HttpUtil.createRequest(Method.POST, url);
        req.contentType("application/json");
        String body = JSON.toJSONString(subList);
        req.body(body);
        req.basicAuth("admin", "public");
        log.info("doRequest url:{},body:{}", url, body);
        HttpResponse httpResponse = req.executeAsync();

        String responseBody = httpResponse.body();
        log.info("responseBody:{}", responseBody);
    }

}
