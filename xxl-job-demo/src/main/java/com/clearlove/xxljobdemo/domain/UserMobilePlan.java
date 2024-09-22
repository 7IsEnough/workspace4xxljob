package com.clearlove.xxljobdemo.domain;

import lombok.Data;

/**
 * @author promise
 * @date 2024/9/22 - 16:24
 */
@Data
public class UserMobilePlan {

  // 主键
  private Long id;

  // 用户名
  private String username;

  // 昵称
  private String nickName;

  // 手机号码
  private String phone;

  // 备注
  private String info;

}
