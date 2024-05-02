package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.GroupDTO;
import cn.edu.szu.company.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public Result createGroup(@RequestHeader("companyId") Long companyId, @RequestBody GroupDTO groupDTO) {
        boolean flag = groupService.createGroup(companyId, groupDTO);
        if (flag) {
            return new Result(Code.SAVE_OK, true, "创建成功");
        }
        return new Result(Code.SAVE_ERR, false, "创建失败");
    }

    @DeleteMapping("/delete/{deptId}")
    public Result deleteGroup(@PathVariable Long deptId) {
        boolean flag = groupService.deleteGroup(deptId);
        if (flag) {
            return new Result(Code.DELETE_OK, true, "删除成功");
        }
        return new Result(Code.DELETE_ERR, false, "删除失败");
    }

    @PutMapping("/update")
    public Result updateGroup(@RequestBody GroupDTO groupDTO) {
        boolean flag = groupService.updateGroup(groupDTO);
        if (flag) {
            return new Result(Code.UPDATE_OK, true, "更新成功");
        }
        return new Result(Code.UPDATE_ERR, false, "更新失败");
    }

    @GetMapping("/all")
    public Result getAllGroup(@RequestHeader("companyId") Long companyId) {
        List<GroupDTO> groupDTOList = groupService.getAllGroup(companyId);
        return new Result(Code.GET_OK, groupDTOList, "查询成功");
    }

    @GetMapping("/{id}")
    public Result getGroupById(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.getGroupById(id);
        if (groupDTO != null) {
            return new Result(Code.GET_OK, groupDTO, "查询成功");
        }
        return new Result(Code.GET_ERR, null, "查询失败");
    }
}
