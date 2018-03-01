package com.diyiliu.support.site;

import com.diyiliu.support.config.Constant;
import com.diyiliu.support.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    private String sessionId;

    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.TEXT_HTML);

        HttpEntity<String> requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(Constant.CZJ_HOME, HttpMethod.GET, requestEntity, String.class);

        int status = responseEntity.getStatusCodeValue();
        if (200 == status) {
            logger.info("访问网站首页...");

            HttpHeaders respHeaders = responseEntity.getHeaders();
            List<String> cookies = respHeaders.get(HttpHeaders.SET_COOKIE);
            cookie = cookies.get(0).split(";")[0];
        } else {
            logger.error("访问网站首页失败[{}]!", status);
        }
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public byte[] fetchCode() {
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
    public boolean loginIn(String username, String password, String checkCode) {
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
        if (StringUtils.isNotEmpty(result)) {
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                boolean flag = (boolean) rsMap.get("success");
                if (flag) {
                    String msg = JacksonUtil.toJson(rsMap.get("annLoginList"));
                    logger.info("登录成功{}...", msg);
                    return true;
                } else {
                    String message = (String) rsMap.get("message");
                    logger.error("登录失败[{}]!", message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    /**
     * 查询余额
     *
     * @return
     */
    public String getBalance() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map paramMap = new HashMap();
        paramMap.put("pNetwork", "MAIN_WALLET");

        HttpEntity<String> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.CZJ_QUERY_BALANCE, requestEntity, String.class);

        String result = responseEntity.getBody();
        if (StringUtils.isNotEmpty(result)) {
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                boolean flag = (boolean) rsMap.get("success");
                if (flag) {
                    String balance = (String) rsMap.get("balance");
                    return balance;
                } else {
                    String message = (String) rsMap.get("message");
                    logger.error("查询余额失败[{}]!", message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "?";
    }

    public void getPlayHoldem() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map paramMap = new HashMap();
        paramMap.put("product", "LOTTERY_IG");
        paramMap.put("type", "2");
        paramMap.put("line", "0");

        HttpEntity<String> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<HashMap> responseEntity = restTemplate.postForEntity(Constant.CZJ_PLAY_HOLDEM, requestEntity, HashMap.class);

        Map respMap = responseEntity.getBody();
        boolean flag = (boolean) respMap.get("success");
        if (flag) {
            String link = (String) respMap.get("link");
            int index = link.indexOf("code=");
            String code = link.substring(index);
            index = code.indexOf("&");
            sessionId = code.substring(5, index);

            logger.info("获取查询参数sessionId[{}]", sessionId);

            ResponseEntity<String> responseEntity2 = restTemplate.exchange(link, HttpMethod.GET, requestEntity, String.class);
            logger.info("进入幸运飞艇[{}]...", responseEntity2.getStatusCodeValue());
        } else {
            logger.error("获取查询参数失败[{}]!", flag);
        }
    }

    public Map queryWebAgent() {
        if (StringUtils.isEmpty(sessionId)) {

            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("command", "UPDATE");
        paramMap.add("sessionId", sessionId);
        paramMap.add("lotteryType", "XYFT");
        paramMap.add("hasPlayerInfo", "true");

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.CZJ_WEB_AGENT, requestEntity, String.class);

        String result = responseEntity.getBody();
        if (StringUtils.isNotEmpty(result)) {
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                int code = (int) rsMap.get("returnCode");
                if (code == 0) {
                    Map gameInfo = (Map) rsMap.get("gameInfo");
                    double balance = (double) rsMap.get("balance");

                    Map simplify = new HashMap();
                    simplify.put("balance", String.format("%.2f", balance));
                    simplify.put("XYFT", gameInfo.get("XYFT"));
                    simplify.put("time", System.currentTimeMillis());

                    return simplify;
                } else {
                    String message = (String) rsMap.get("message");
                    logger.error("查询更新数据失败[{}]!", message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
