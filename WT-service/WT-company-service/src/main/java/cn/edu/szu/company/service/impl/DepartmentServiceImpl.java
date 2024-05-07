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

import java.util.Date;
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
        if (department == null) {
            return null;
        }

        Long managerId = department.getManagerId();
        UserDTO user = userClient.getUserById(managerId);
        DeptDTO deptDTO = new DeptDTO(department);
        deptDTO.setManager(user);
        return deptDTO;
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

    @Override
    public boolean updateUserDept(Long uid, Long did) {
        try {
            int k = departmentMapper.updateUserDept(uid,did);
            System.out.println(uid+ "     "+did);
            if (k <= 0){
                return false;
            }
        }catch (Exception e){
            return false;
        }

        return true;
    }
    @Override
    public boolean createDepartment(Long companyId, DeptDTO deptDTO) {
        // 实现创建部门的方法
        try {
            Department department = new Department();
            department.setCompanyId(companyId);
            department.setParentId(deptDTO.getParentId());
            department.setName(deptDTO.getName());
            department.setManagerId(deptDTO.getManagerId());
            department.setIntroduction(deptDTO.getIntroduction());
            department.setCreateTime(new Date());
            department.setJob(0); // 初始待分配任务数为0
            department.setNum(0L); // 初始部门人数为0

            return departmentMapper.insert(department) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteDepartment(Long deptId) {
        // 实现删除部门的方法
        try {
            return departmentMapper.deleteById(deptId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Long selectIdByName(String deptName) {
        // 实现根据部门名称查询部门ID的逻辑
        return departmentMapper.selectIdByName(deptName);
    }
}




