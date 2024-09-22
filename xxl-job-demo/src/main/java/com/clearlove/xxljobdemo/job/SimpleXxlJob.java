package com.clearlove.xxljobdemo.job;

import com.clearlove.xxljobdemo.domain.UserMobilePlan;
import com.clearlove.xxljobdemo.mapper.UserMobilePlanMapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author promise
 * @date 2024/9/20 - 1:56
 */
@Component
public class SimpleXxlJob {

  @Autowired
  private UserMobilePlanMapper userMobilePlanMapper;

  @XxlJob("demoJobHandler")
  public void demoJobHandler() {
    System.out.println("执行定时任务，执行时间：" + new Date());
  }


  @XxlJob("sendMsgHandler")
  public void sendMsgHandler() {
    int shardIndex = XxlJobHelper.getShardIndex();
    int shardTotal = XxlJobHelper.getShardTotal();
    System.out.println("分片的总数：" + shardTotal + "，分片的索引：" + shardIndex);
    List<UserMobilePlan> userMobilePlans;
    if (shardTotal == 1) {
      // 如果没有分片就直接查询所有数据
      userMobilePlans = userMobilePlanMapper.selectAll();
    } else {
      userMobilePlans = userMobilePlanMapper.selectByMod(shardIndex, shardTotal);
    }
    System.out.println("任务开始时间：" + new Date() + "，处理任务数量：" + userMobilePlans.size());
    Long startTime = System.currentTimeMillis();
    userMobilePlans.forEach(item -> {
      try {
        // 模拟发送短信操作
        TimeUnit.MILLISECONDS.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    System.out.println("任务结束时间：" + new Date());
    System.out.println("任务耗时：" + (System.currentTimeMillis() - startTime) + " 毫秒");
  }
}
