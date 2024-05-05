package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DeptController {
    @Autowired
    private DepartmentService departmentService;

    //
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

    @PostMapping("/updateDeptInfo")
    public Result updateDeptInfo(@RequestParam Long id, @RequestParam String deptName, @RequestParam Long FatherDeptId,@RequestParam Long managerId){
        DeptDTO deptDTO = departmentService.selectDeptByID(id);
        deptDTO.setName(deptName);
        deptDTO.setParentId(FatherDeptId);
        deptDTO.setManagerId(managerId);
        boolean b = departmentService.updateById(new Department(deptDTO));
        if (b)
            return new Result(Code.UPDATE_OK,null,"编辑成功！");
        return new Result(Code.UPDATE_ERR,null,"编辑失败！");
    }

}
