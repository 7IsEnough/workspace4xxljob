package com.clearlove.xxljobdemo.client;


import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author promise
 * @date 2024/9/22 - 22:53
 */
public interface XxlJobAdminClient {
  
  @POST("login")
  Call<ResponseBody> login(@Query("userName") String userName, @Query("password") String password, @Query("ifRemember") String ifRemember);

  @GET("jobinfo/pageList")
  Call<Map<String, Object>> pageList(@Query("jobGroup") int jobGroup, @Query("triggerStatus") int triggerStatus);

  @POST("jobinfo/add")
  @FormUrlEncoded
  Call<Object> add(@FieldMap Map<String, String> queryMap);
  
}
