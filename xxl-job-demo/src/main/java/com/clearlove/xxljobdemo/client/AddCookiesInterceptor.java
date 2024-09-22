package com.clearlove.xxljobdemo.client;

import com.clearlove.xxljobdemo.service.JobService;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * @author promise
 * @date 2024/9/23 - 0:43
 */
public class AddCookiesInterceptor implements Interceptor {

  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {
    Builder builder = chain.request().newBuilder();
    if (!chain.request().url().uri().getPath().contains("/login")) {
      builder.addHeader("Cookie", JobService.xxlJobToken.get());
    }
    return chain.proceed(builder.build());
  }
}
