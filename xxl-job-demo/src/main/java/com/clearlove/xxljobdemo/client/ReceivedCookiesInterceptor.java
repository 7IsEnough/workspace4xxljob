package com.clearlove.xxljobdemo.client;



import com.clearlove.xxljobdemo.service.JobService;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author promise
 * @date 2024/9/22 - 23:51
 */
public class ReceivedCookiesInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Response originalResponse = chain.proceed(chain.request());
    StringBuilder cookieStr = new StringBuilder();
    Headers headers = originalResponse.headers();
    if (!originalResponse.headers("Set-Cookie").isEmpty()) {
      for (String cookie : originalResponse.headers("Set-Cookie")) {
        // 处理cookie，例如保存到SharedPreferences或者内存中
        cookieStr.append(cookie);
      }
      JobService.setCookieValue(cookieStr.toString());
    }
    return originalResponse;
  }
}
