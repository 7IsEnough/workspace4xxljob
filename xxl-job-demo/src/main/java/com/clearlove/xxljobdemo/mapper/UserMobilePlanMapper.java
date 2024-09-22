package com.clearlove.xxljobdemo.mapper;

import com.clearlove.xxljobdemo.domain.UserMobilePlan;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author promise
 * @date 2024/9/22 - 16:27
 */
@Mapper
public interface UserMobilePlanMapper {

  @Select("select * from t_user_mobile_plan")
  List<UserMobilePlan> selectAll();

  @Select("select * from t_user_mobile_plan where mod(id, #{shardingTotal}) = #{shardingIndex}")
  List<UserMobilePlan> selectByMod(@Param("shardingIndex") Integer shardingIndex, @Param("shardingTotal") Integer shardingTotal);

}
