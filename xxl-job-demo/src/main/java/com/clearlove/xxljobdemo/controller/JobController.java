package com.clearlove.xxljobdemo.controller;

import com.clearlove.xxljobdemo.service.JobService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author promise
 * @date 2024/9/22 - 23:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {

  private final JobService jobService;


  @GetMapping("/login")
  public Boolean login(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("ifRemember") String ifRemember) {
    return jobService.login(userName, password, ifRemember);
  }

  @GetMapping("/pageList")
  public String pageList(@RequestParam("jobGroup") Integer jobGroup, @RequestParam("triggerStatus") Integer triggerStatus) {
    return jobService.pageList(jobGroup, triggerStatus);
  }

  @GetMapping("/addJob")
  public void addJob() throws IllegalAccessException, IOException {
    jobService.addJob();
  }

}
