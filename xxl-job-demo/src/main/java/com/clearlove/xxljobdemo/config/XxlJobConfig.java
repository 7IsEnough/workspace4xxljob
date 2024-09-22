package com.clearlove.xxljobdemo.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author promise
 * @date 2024/9/20 - 1:41
 */
@Configuration
public class XxlJobConfig {

  @Value("${xxl.job.admin.addresses}")
  private String adminAddress;

  @Value("${xxl.job.accessToken}")
  private String accessToken;

  @Value("${xxl.job.executor.appname}")
  private String appname;

  @Value("${xxl.job.executor.address}")
  private String address;

  @Value("${xxl.job.executor.ip}")
  private String ip;

  @Value("${xxl.job.executor.port}")
  private int port;


  @Bean
  public XxlJobSpringExecutor xxlJobSpringExecutor() {
    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
    xxlJobSpringExecutor.setAdminAddresses(adminAddress);
    xxlJobSpringExecutor.setAppname(appname);
    xxlJobSpringExecutor.setAddress(address);
    xxlJobSpringExecutor.setIp(ip);
    xxlJobSpringExecutor.setPort(port);
    xxlJobSpringExecutor.setAccessToken(accessToken);

    return xxlJobSpringExecutor;
  }

}
