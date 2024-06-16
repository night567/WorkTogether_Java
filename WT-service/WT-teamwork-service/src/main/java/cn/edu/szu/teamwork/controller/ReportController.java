package cn.edu.szu.teamwork.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping
    public Result submitReport(@RequestHeader("Authorization") String token, @RequestBody Report report) {
        Long userId = JwtUtil.getUserId(token);
        report.setUserId(userId);
        boolean isSuccess = reportService.submitReport(report);
        if (isSuccess) {
            return new Result(Code.SAVE_OK, true, "提交成功");
        }
        return new Result(Code.SAVE_ERR, false, "提交失败");
    }

    @PutMapping
    public Result updateReport(@RequestHeader("Authorization") String token, @RequestBody Report report) {
        Long userId = JwtUtil.getUserId(token);
        report.setUserId(userId);
        boolean isSuccess = reportService.updateReport(report);
        if (isSuccess) {
            return new Result(Code.UPDATE_OK, true, "修改成功");
        }
        return new Result(Code.UPDATE_ERR, false, "修改失败");
    }
}
