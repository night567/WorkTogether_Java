package cn.edu.szu.teamwork.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.teamwork.pojo.ReportCondition;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getReportById")
    public Result getReportById(@RequestParam String id){
        Report report = reportService.getReportById(id);
        if (report==null||report.equals(""))
            return new Result(Code.GET_ERR,null,"获取失败");
        return new Result(Code.GET_OK,report,"获取成功");
    }

    @GetMapping("/getMyReports")
    public Result getMyReports(@RequestHeader("Authorization") String token){
        Long userId = JwtUtil.getUserId(token);
        List<Report> myReports = reportService.getMyReports(userId.toString());
        if (myReports ==null||myReports .equals(""))
            return new Result(Code.GET_ERR,null,"获取失败");
        return new Result(Code.GET_OK,myReports ,"获取成功");
    }

    @GetMapping("/getReportByCondition")
    public Result getReportByCondition(@RequestHeader("companyId") Long cid, @RequestBody ReportCondition condition){
        List<Report> res = reportService.getReportByCondition(condition, cid);
        if (res == null ){
            return new Result(Code.GET_ERR,null ,"获取失败");
        }
        return new Result(Code.GET_OK,res ,"获取成功");
    }


}
