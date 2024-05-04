package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DeptController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/selectByID")
    public Result selectDeptByID(@RequestParam Long deptId){
        DeptDTO deptDTO = departmentService.selectDeptByID(deptId);
        if (deptDTO!=null)
            return new Result(Code.GET_OK,deptDTO,"查询成功！");

        return new Result(Code.GET_ERR,null,"查询失败！");
    }

    @GetMapping("/selectHighestDepts")
    public Result selectHighestDepts(@RequestParam Long companyId){
        List<DeptDTO> deptDTO = departmentService.selectHighestDeptByCompanyId(companyId);
        if (deptDTO!=null||deptDTO.isEmpty())
            return new Result(Code.GET_OK,deptDTO,"查询成功！");

        return new Result(Code.GET_ERR,null,"查询失败！");
    }

    @GetMapping("/selectDeptsByParentId")
    public Result selectDeptsByParentId(@RequestParam Long parentDeptId){
        List<DeptDTO> deptDTO = departmentService.selectDeptsByParentId(parentDeptId);
        if (deptDTO!=null||deptDTO.isEmpty())
            return new Result(Code.GET_OK,deptDTO,"查询成功！");

        return new Result(Code.GET_ERR,null,"查询失败！");
    }

}
