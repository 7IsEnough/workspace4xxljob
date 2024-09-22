package com.clearlove.xxljobdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author promise
 * @date 2024/9/23 - 0:34
 */
@Data
@ConfigurationProperties(prefix = "xxl.login")
@Configuration
public class XxlJobAdminProperties {

  private String username;
  private String password;
  private String ifRemember;
}
