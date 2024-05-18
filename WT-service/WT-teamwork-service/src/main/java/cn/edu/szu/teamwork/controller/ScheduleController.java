package cn.edu.szu.teamwork.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.teamwork.pojo.ScheduleDTO;
import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.service.ScheduleService;
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
    //获取个人日程
    @GetMapping("/user")
    private Result selectUserSchedule(@RequestParam Long groupId, @RequestParam Long userId, @RequestParam String startTime, @RequestParam String endTime){
        List<Schedule> schedules = scheduleService.selectUserSchedule(groupId, userId, startTime, endTime);
        if(schedules==null||schedules.isEmpty())
            return  new Result(Code.GET_ERR,schedules,"查询失败！");
        return  new Result(Code.GET_OK,schedules,"查询成功！");
    }
}
