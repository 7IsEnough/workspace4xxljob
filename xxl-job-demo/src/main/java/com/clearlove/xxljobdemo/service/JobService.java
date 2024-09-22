package com.clearlove.xxljobdemo.service;

import com.clearlove.xxljobdemo.client.AddCookiesInterceptor;
import com.clearlove.xxljobdemo.client.ReceivedCookiesInterceptor;
import com.clearlove.xxljobdemo.client.XxlJobAdminClient;
import com.clearlove.xxljobdemo.config.XxlJobAdminProperties;
import com.clearlove.xxljobdemo.domain.XxlJobInfo;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author promise
 * @date 2024/9/22 - 23:10
 */
@Service
@RequiredArgsConstructor
public class JobService {

  public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";
  public static ThreadLocal<String> cookieValue = new ThreadLocal<>();
  public static ThreadLocal<String> xxlJobToken = new ThreadLocal<>();
  private final XxlJobAdminProperties xxlJobAdminProperties;
  private XxlJobAdminClient xxlJobAdminClient =
      new Retrofit.Builder()
          .baseUrl("http://localhost:8080/xxl-job-admin/")
          .client(
              new OkHttpClient.Builder()
                  .addInterceptor(new AddCookiesInterceptor())
                  .addInterceptor(new ReceivedCookiesInterceptor())
                  .build())
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(XxlJobAdminClient.class);

  public static void setCookieValue(String value) {
    cookieValue.set(value);
  }

  public String pageList(int jobGroup, int triggerStatus) {
    if (StringUtils.isEmpty(xxlJobToken.get())) {
      login(
          xxlJobAdminProperties.getUsername(),
          xxlJobAdminProperties.getPassword(),
          xxlJobAdminProperties.getIfRemember());
    }
    Call<Map<String, Object>> mapCall = xxlJobAdminClient.pageList(jobGroup, triggerStatus);
    try {
      return mapCall.execute().body().get("data").toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean login(String userName, String password, String ifRemember) {
    Call<ResponseBody> response = xxlJobAdminClient.login(userName, password, ifRemember);
    try {
      response.execute();
      String allCookieValue = cookieValue.get();
      String[] split = allCookieValue.split(";");
      for (String value : split) {
        if (value.contains(LOGIN_IDENTITY_KEY)) {
          xxlJobToken.set(value);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  public void addJob() throws IllegalAccessException, IOException {
    if (StringUtils.isEmpty(xxlJobToken.get())) {
      login(
          xxlJobAdminProperties.getUsername(),
          xxlJobAdminProperties.getPassword(),
          xxlJobAdminProperties.getIfRemember());
    }
    XxlJobInfo info = new XxlJobInfo();
    info.setJobGroup(1)
        .setJobDesc("代码创建任务")
        .setAuthor("Promise")
        .setScheduleType("CRON")
        .setScheduleConf("* 0/1 * * * ?")
        .setGlueType("BEAN")
        .setExecutorHandler("codeMissionHandler")
        .setExecutorParam("{param:promise}")
        .setExecutorRouteStrategy("FIRST")
        .setMisfireStrategy("DO_NOTHING")
        .setExecutorBlockStrategy("SERIAL_EXECUTION")
        .setExecutorTimeout(0)
        .setExecutorFailRetryCount(0)
        .setGlueRemark("GLUE代码初始化")
        .setAlarmEmail("")
        .setGlueSource("")
        .setGlueUpdatetime(new Date())
        .setAddTime(new Date())
        .setChildJobId("")
        .setTriggerStatus(1) // 直接开启任务
        .setUpdateTime(new Date());
    Map<String, String> queryMap = new HashMap<>();
    Class<?> clazz = info.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true); // 确保私有字段也可以访问
      queryMap.put(
          field.getName(), Objects.isNull(field.get(info)) ? null : field.get(info).toString());
    }
    Call<Object> add = xxlJobAdminClient.add(queryMap);
    add.execute();
  }
}
