package com.diyiliu.support.config;

import com.diyiliu.support.cache.ICache;
import com.diyiliu.support.site.WebContainer;
import com.diyiliu.ui.MainUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description: SpringQuartz
 * Author: DIYILIU
 * Update: 2018-03-01 21:22
 */

@EnableScheduling
@Configuration
public class SpringQuartz {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ICache agentCacheProvider;

    @Resource
    private WebContainer webContainer;

    @Resource
    private MainUI mainUI;

    /**
     * 刷新开奖数据
     */
    @Scheduled(fixedDelay = 11 * 1000, initialDelay = 15 * 1000)
    public void refreshWebAgent() {
        logger.info("刷新开奖数据...");
        Map respMap = webContainer.queryWebAgent();

        if (respMap != null){
            agentCacheProvider.put("XYFT", respMap);
            mainUI.refresh(respMap);
        }
    }
}
