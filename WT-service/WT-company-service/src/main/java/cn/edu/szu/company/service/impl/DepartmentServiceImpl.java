package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.service.DepartmentService;
import cn.edu.szu.company.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zgr24
* @description 针对表【wt_department(部门)】的数据库操作Service实现
* @createDate 2024-04-28 16:39:41
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserClient userClient;


    //根据部门ID查询部门
    @Override
    public DeptDTO selectDeptByID(Long deptId) {
        Department department = departmentMapper.selectById(deptId);
        Long managerId = department.getManagerId();
        UserDTO user = userClient.getUserById(managerId);
        DeptDTO deptDTO=new DeptDTO(department);
        deptDTO.setManager(user);
        return  deptDTO;
    }

    //根据公司ID查询最高级部门的部门列表
    @Override
    public List<DeptDTO> selectHighestDeptByCompanyId(Long companyId){
        List<DeptDTO> deptDTOS = departmentMapper.selectHighestDeptByCompanyId(companyId);
        for(DeptDTO deptDTO :deptDTOS){
            deptDTO.setManagerName(userClient.getUserById(deptDTO.getManagerId()).getName());
        }
        return deptDTOS;
    }

    //根据上级部门ID查看子部门列表
    @Override
    public List<DeptDTO> selectDeptsByParentId(Long parentId) {
        List<DeptDTO> deptDTOS = departmentMapper.selectDeptsByParentId(parentId);
        for(DeptDTO deptDTO :deptDTOS){
            deptDTO.setManagerName(userClient.getUserById(deptDTO.getManagerId()).getName());
        }
        return deptDTOS;
    }
}




