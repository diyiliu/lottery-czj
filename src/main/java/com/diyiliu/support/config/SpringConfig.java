package com.diyiliu.support.config;

import com.diyiliu.support.cache.ICache;
import com.diyiliu.support.cache.ram.RamCacheProvider;
import com.diyiliu.support.site.WebContainer;
import com.diyiliu.ui.MainUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: SpringConfig
 * Author: DIYILIU
 * Update: 2018-03-01 21:20
 */

@Configuration
public class SpringConfig {


    /**
     * 爬虫工具
     *
     * @return
     */
    @Bean
    public WebContainer webContainer(){

        return new WebContainer();
    }

    /**
     * 主面板
     *
     * @return
     */
    @Bean
    public MainUI mainUI(WebContainer webContainer){

        return new MainUI(webContainer);
    }

    /**
     * 开奖数据
     *
     * @return
     */
    @Bean
    public ICache agentCacheProvider() {

        return new RamCacheProvider();
    }


    /**
     * 下注数据
     *
     * @return
     */
    @Bean
    public ICache betCacheProvider() {

        return new RamCacheProvider();
    }
}
