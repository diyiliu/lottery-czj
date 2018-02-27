package com.diyiliu.support.site;

import com.diyiliu.support.config.Constant;
import com.diyiliu.support.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: WebContainer
 * Author: DIYILIU
 * Update: 2018-02-27 21:16
 */

public class WebContainer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String cookie;

    public void init(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.TEXT_HTML);

        HttpEntity<String> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(Constant.CZJ_HOME, HttpMethod.GET, requestEntity, String.class);

        int status = responseEntity.getStatusCodeValue();
        if (200 == status){
            logger.info("访问网站首页...");

            HttpHeaders respHeaders = responseEntity.getHeaders();
            List<String> cookies = respHeaders.get(HttpHeaders.SET_COOKIE);
            cookie = cookies.get(0).split(";")[0];
        }else {
            logger.error("访问网站首页失败[{}]!", status);
        }
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public byte[] fetchCode(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.IMAGE_JPEG);

        List<String> cookies = new ArrayList();
        cookies.add(cookie);
        headers.put(HttpHeaders.COOKIE, cookies);

        HttpEntity<String> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(Constant.CZJ_CHECK_CODE, HttpMethod.GET, requestEntity, byte[].class);

        return responseEntity.getBody();
    }


    /**
     * 登录账号
     *
     * @param username
     * @param password
     * @param checkCode
     * @return
     */
    public boolean loginIn(String username, String password, String checkCode){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map paramMap = new HashMap();
        paramMap.put("txtLoginUsername", username);
        paramMap.put("txtLoginPassword", password);
        paramMap.put("txtLoginCaptcha", checkCode);

        HttpEntity<String> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.CZJ_TO_LOGIN, requestEntity, String.class);

        String result = responseEntity.getBody();
        if (StringUtils.isNotEmpty(result)){
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                boolean flag = (boolean) rsMap.get("success");
                if (flag){
                    String msg = JacksonUtil.toJson(rsMap.get("annLoginList")) ;
                    logger.info("登录成功{}...", msg);
                    return true;
                }else {
                    String message = (String) rsMap.get("message");
                    logger.error("登录失败[{}]!", message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
