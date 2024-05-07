package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.pojo.domain.UserDept;
import cn.edu.szu.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DeptController {
    @Autowired
    private DepartmentService departmentService;

    //根据部门ID查询部门
    @GetMapping("/selectByID")
    public Result selectDeptByID(@RequestParam Long deptId){
        DeptDTO deptDTO = departmentService.selectDeptByID(deptId);
        if (deptDTO!=null)
            return new Result(Code.GET_OK,deptDTO,"查询成功！");

        return new Result(Code.GET_ERR,null,"查询失败！");
    }

    //根据公司ID查询最高级部门的部门列表
    @GetMapping("/selectHighestDepts")
    public Result selectHighestDepts(@RequestParam Long companyId){
        List<DeptDTO> deptDTO = departmentService.selectHighestDeptByCompanyId(companyId);
        if (deptDTO!=null||deptDTO.isEmpty())
            return new Result(Code.GET_OK,deptDTO,"查询成功！");

        return new Result(Code.GET_ERR,null,"查询失败！");
    }

    //根据上级部门ID查看子部门列表
    @GetMapping("/selectDeptsByParentId")
    public Result selectDeptsByParentId(@RequestParam Long parentDeptId){
        List<DeptDTO> deptDTO = departmentService.selectDeptsByParentId(parentDeptId);
        if (deptDTO!=null||deptDTO.isEmpty())
            return new Result(Code.GET_OK,deptDTO,"查询成功！");

        return new Result(Code.GET_ERR,null,"查询失败！");
    }

    //编辑部门
    @PostMapping("/updateDeptInfo")
    public Result updateDeptInfo(@RequestParam Long id, @RequestParam String deptName, @RequestParam String FatherDeptName,@RequestParam Long managerId){
        DeptDTO deptDTO = departmentService.selectDeptByID(id);
        Long FatherDeptId = departmentService.selectIdByName(FatherDeptName);
        deptDTO.setName(deptName);
        deptDTO.setParentId(FatherDeptId);
        deptDTO.setManagerId(managerId);
        boolean b = departmentService.updateById(new Department(deptDTO));
        if (b)
            return new Result(Code.UPDATE_OK,null,"编辑成功！");
        return new Result(Code.UPDATE_ERR,null,"编辑失败！");
    }

    /**
     * 修改用户部门
     * @param userDept
     * @return
     */
    @PutMapping("/updateUserDept")
    public Result updateUserDept(@RequestBody UserDept userDept){
        System.out.println(userDept.toString());
        if (departmentService.updateUserDept(userDept.getUid(), userDept.getDid())){
            return new Result(Code.UPDATE_OK,null,"更新成功");
        }

        return new Result(Code.UPDATE_ERR,null,"更新失败");
    }

    @PostMapping("/createDept")
    public Result createDepartment(@RequestParam Long companyId, @RequestBody DeptDTO deptDTO) {
        boolean result = departmentService.createDepartment(companyId, deptDTO);
        if (result) {
            return new Result(Code.SAVE_OK, true, "部门创建成功");
        } else {
            return new Result(Code.SAVE_ERR, false, "部门创建失败");
        }
    }

    @DeleteMapping("/deleteDept")
    public Result deleteDepartment(@RequestParam Long deptId) {
        boolean result = departmentService.deleteDepartment(deptId);
        if (result) {
            return new Result(Code.DELETE_OK, true, "部门删除成功");
        } else {
            return new Result(Code.DELETE_ERR, false, "部门删除失败");
        }
    }


}
