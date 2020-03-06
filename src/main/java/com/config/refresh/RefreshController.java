package com.config.refresh;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/***
 * 仍未解决config + bus 的不能自动刷新配置的问题
 *  请求actuator/bus-refresh 时 405错误 升级到2.1.8后 可能请求到 但是client端会报json接收消息错误
 *  *怀疑为版本问题 升级版本后再看是否还会有此问题
 */
@RestController
public class RefreshController {

    @RequestMapping("/refresh")
    public void refresh(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        //设置content_type为json要不然会报415的错误
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null,httpHeaders);
        // 以post方法访问真正的刷新链接
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8090/actuator/bus-refresh",
                request, String.class);
    }
}
