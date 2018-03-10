package com.diyiliu.support.site;

import com.diyiliu.support.config.Constant;
import com.diyiliu.support.model.BetDetail;
import com.diyiliu.support.model.BetReturn;
import com.diyiliu.support.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description: WebContainer
 * Author: DIYILIU
 * Update: 2018-02-27 21:16
 */

public class WebContainer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConcurrentMap<String, Object> containerMap = new ConcurrentHashMap();

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
    public boolean login(String username, String password, String checkCode) {
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
     * 退出账号
     *
     * @return
     */
    public boolean logout() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity(new HashMap(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.CZJ_TO_LOGOUT, requestEntity, String.class);

        String result = responseEntity.getBody();
        if (StringUtils.isNotEmpty(result)) {
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                boolean flag = (boolean) rsMap.get("success");
                if (flag) {
                    logger.info("注销成功...");
                    sessionId = null;
                    return true;
                } else {
                    logger.error("注销失败[{}]!", JacksonUtil.toJson(rsMap));
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
        logger.info("查询余额...");

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

    /**
     * 进入下注页面
     */
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

    /**
     * 定时刷新数据
     *
     * @return
     */
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
                    double balance = Double.valueOf(String.valueOf(rsMap.get("balance")));

                    Map simplify = new HashMap();
                    simplify.put("balance", String.format("%.2f", balance));
                    simplify.put("XYFT", gameInfo.get("XYFT"));
                    simplify.put("time", System.currentTimeMillis());
                    simplify.put("odds", rsMap.get("odds"));

                    return simplify;
                } else {
                    logger.error("查询更新数据失败[{}]!", result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 下注
     *
     * @param betList
     * @return
     */
    public BetReturn submitBet(List betList) {
        BetReturn betReturn = new BetReturn();
        if (StringUtils.isEmpty(sessionId)) {

            return betReturn;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("command", "BET");
        paramMap.add("sessionId", sessionId);
        paramMap.add("oddsAdapt", "true");
        paramMap.add("bets", JacksonUtil.toJson(betList));
        paramMap.add("gameType", "XYFT");
        paramMap.add("timestamps", String.valueOf(System.currentTimeMillis()));
        paramMap.add("hasPlayerInfo", "true");

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.CZJ_WEB_AGENT, requestEntity, String.class);

        String result = responseEntity.getBody();
        if (StringUtils.isNotEmpty(result)) {
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                int code = (int) rsMap.get("returnCode");
                betReturn.setReturnCode(code);
                if (code == 0) {
                    //logger.info("下注成功...");

                    Map betMap = (Map) rsMap.get("bet");
                    String gameNo = (String) betMap.get("gameNo");
                    betReturn.setPeriod(gameNo);
                } else {
                    logger.error("下注失败[{}]!", result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return betReturn;
    }


    /**
     * 今日输赢
     * 服务器有查询时间间隔限制
     *
     * @return
     */
    public List<BetDetail> queryReportDetail() {
        if (StringUtils.isEmpty(sessionId)) {

            return null;
        }
        long now = System.currentTimeMillis();
        if (containerMap.containsKey("queryReportDetail")) {

            long last = (long) containerMap.get("queryReportDetail");
            // 小于30秒间隔
            if (now - last < 30 * 1000) {

                return null;
            }
        }
        logger.info("查询今日输赢情况...");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList();
        cookies.add(cookie);

        headers.put(HttpHeaders.COOKIE, cookies);
        headers.add(HttpHeaders.USER_AGENT, Constant.USER_AGENT);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("command", "REPORT_DETAILS");
        paramMap.add("sessionId", sessionId);
        paramMap.add("date", DateFormatUtils.format(calendar.getTimeInMillis(), "yyyy-MM-dd"));
        paramMap.add("hasPlayerInfo", "true");

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.CZJ_WEB_AGENT, requestEntity, String.class);

        String result = responseEntity.getBody();
        if (StringUtils.isNotEmpty(result)) {
            try {
                Map rsMap = JacksonUtil.toObject(result, HashMap.class);
                if (!rsMap.containsKey("returnCode")) {

                    return null;
                }

                int code = (int) rsMap.get("returnCode");
                if (code == 0) {
                    List gameInfo = (List) rsMap.get("reportDetails");

                    List<BetDetail> details = new ArrayList();
                    gameInfo.forEach(e -> {
                        Map m = (Map) e;

                        BetDetail d = new BetDetail();
                        d.setGameNo((String) m.get("gameNo"));
                        d.setBetType((String) m.get("betType"));
                        d.setBetOn((String) m.get("betOn"));
                        d.setBetMoney((Integer) m.get("betAmount"));
                        d.setWinLoss(new BigDecimal(String.valueOf(m.get("winloss"))));
                        details.add(d);
                    });
                    containerMap.put("queryReportDetail", now);

                    return details;
                } else {
                    logger.error("查询今日输赢失败[{}]!", result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
