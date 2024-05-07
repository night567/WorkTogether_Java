package cn.edu.szu.company.controller;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.pojo.GroupDTO;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.UserGroupRequest;
import cn.edu.szu.company.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/createByExcel")
    public Result createGroupByExcel(@RequestHeader("companyId") Long companyId, @RequestPart("groupFile") MultipartFile groupFile) {
        try {
            boolean flag = groupService.createByExcel(companyId,groupFile);
            if (flag) {
                return new Result(Code.SAVE_OK, true, "创建成功");
            }
            return new Result(Code.SAVE_ERR, false, "文件不存在");
        } catch (Exception e) {
            return new Result(Code.SAVE_ERR, false, e.getMessage());
        }
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

    /**
     * 获取团队内所有成员
     * 需要返回MemberDTO的所有内容
     * @param id
     * @return
     */
    @GetMapping("/member/{id}")
    public Result getGroupMemberById(@PathVariable Long id){
        List<MemberDTO> res = groupService.getGroupMember(id);
        if (res == null){
            return new Result(Code.GET_ERR, null, "查询失败");
        }else if(res.isEmpty()){
            return new Result(Code.GET_OK, res, "查询结果为空");
        }
        return new Result(Code.GET_OK, res, "查询成功");
    }

    /**
     * 向团队中添加成员
     * @param userGroup
     * @return
     */
    @PostMapping("/member/addByMail")
    public Result addMember(@RequestBody UserGroupRequest userGroup){
        List<String> list = userGroup.getList();
        Long groupId = userGroup.getGroupId();
        System.out.println(list);
        System.out.println(groupId);
        boolean k = groupService.addMemberToGroup(list,groupId);
        if (k){
            return new Result(Code.SAVE_OK, null, "添加成功");
        }
        return new Result(Code.SAVE_ERR, null, "添加失败");
    }

    @DeleteMapping("/member/{id}/{gid}")
    public Result delMember(@PathVariable Long id,@PathVariable Long gid){
        boolean k = groupService.delMemberFromGroup(id, gid);
        if (k){
            return new Result(Code.DELETE_OK, null, "删除成功");
        }

        return new Result(Code.DELETE_ERR, null, "删除失败");
    }

    @PutMapping("/member")
    public Result updateMember(@RequestBody UserGroupRequest request){
        boolean k = groupService.updateMember(request);
        if (k){
            return new Result(Code.UPDATE_OK, null, "更新成功");
        }
        return new Result(Code.UPDATE_ERR, null, "更新失败");
    }

    @GetMapping("/position")
    public Result getPosition(){
        List<String> res = groupService.getPosition();
        if (res == null || res.isEmpty()){
            return new Result(Code.GET_ERR,null,"查询失败");
        }
        return new Result(Code.GET_ERR,res,"查询成功");
    }


}
