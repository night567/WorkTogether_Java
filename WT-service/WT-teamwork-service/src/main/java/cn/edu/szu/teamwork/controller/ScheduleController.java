package cn.edu.szu.teamwork.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.teamwork.pojo.ScheduleDTO;
import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.service.ScheduleService;
import cn.hutool.jwt.JWTUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    private Result createSchedule(@RequestHeader("Authorization") String token, @RequestBody ScheduleDTO scheduleDTO) {
        Long userId = JwtUtil.getUserId(token);
        if (userId == null) {
            return new Result(Code.SAVE_ERR, false, "token解析异常");
        }
        scheduleDTO.setCreatorId(userId.toString());
        try {
            boolean isCreated = scheduleService.createSchedule(scheduleDTO);
            if (isCreated) {
                return new Result(Code.SAVE_OK, true, "创建成功");
            }
            return new Result(Code.SAVE_ERR, false, "创建失败");
        } catch (Exception e) {
            return new Result(Code.SAVE_ERR, false, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    private Result deleteSchedule(@PathVariable String id) {
        boolean isDeleted = scheduleService.removeById(id);
        if (isDeleted) {
            return new Result(Code.DELETE_OK, true, "删除成功");
        }
        return new Result(Code.DELETE_ERR, false, "删除失败");
    }

    @PutMapping
    private Result updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            boolean isUpdated = scheduleService.updateSchedule(scheduleDTO);
            if (isUpdated) {
                return new Result(Code.UPDATE_OK, true, "更新成功");
            }
            return new Result(Code.UPDATE_ERR, false, "更新失败");
        } catch (Exception e) {
            return new Result(Code.UPDATE_ERR, false, e.getMessage());
        }
    }
    //获取个人日程（时间区内）
    @GetMapping("/user")
    private Result selectUserSchedule(@RequestParam Long groupId, @RequestParam Long userId, @RequestParam String startTime, @RequestParam String endTime){
        List<Schedule> schedules = scheduleService.selectUserSchedule(groupId, userId, startTime, endTime,true);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }

    //获取团队日程（时间区内）
    @GetMapping("/group")
    private Result selectGroupSchedule(@RequestParam Long groupId, @RequestParam String startTime, @RequestParam String endTime){
        List<Schedule> schedules = scheduleService.selectScheduleByGroupId(groupId, startTime, endTime,true);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }
    //获取不同类型日程（时间区内）
    @GetMapping("/type")
    private Result selectScheduleByType(@RequestParam Long groupId, @RequestParam String startTime, @RequestParam String endTime,@RequestParam Long type){
        List<Schedule> schedules = scheduleService.selectScheduleByType(groupId, type,startTime, endTime,true);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }
    //获取个人全部日程
    @GetMapping("/user/all")
    private Result selectUserScheduleAll(@RequestParam Long groupId, @RequestParam Long userId){
        List<Schedule> schedules = scheduleService.selectUserSchedule(groupId, userId, null,null,false);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }

    //获取团队全部日程
    @GetMapping("/group/all")
    private Result selectGroupScheduleAll(@RequestParam Long groupId){
        List<Schedule> schedules = scheduleService.selectScheduleByGroupId(groupId, null,null,false);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }
    //获取不同类型全部日程
    @GetMapping("/type/all")
    private Result selectScheduleByTypeAll(@RequestParam Long groupId,@RequestParam Long type){
        List<Schedule> schedules = scheduleService.selectScheduleByType(groupId, type,null,null,false);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }

    @GetMapping("/{id}")
    public Result selectScheduleById(@PathVariable Long id){
        ScheduleDTO schedule = scheduleService.selectScheduleById(id);

        if (schedule != null){
            return new Result(Code.GET_OK,schedule,"查询成功！");
        }
        return  new Result(Code.GET_ERR,null,"查询失败！");
    }

    @PutMapping("/join/{id}/{force}")
    public Result judgeSchedule(@PathVariable Long id,@RequestHeader String Authorization,@PathVariable Integer force){
        Long userId = JwtUtil.getUserId(Authorization);
        boolean check = scheduleService.judgeSchedule(id,userId);
        if (force == 1){
            scheduleService.joinSchedule(id,userId);
            return new Result(Code.UPDATE_OK,null,"日程不冲突，已经加入日程");
        }
        if (check){
            scheduleService.joinSchedule(id,userId);
            return new Result(Code.UPDATE_OK,null,"日程不冲突，已经加入日程");
        }
        return new Result(Code.UPDATE_ERR,null,"日程冲突");
    }

}
