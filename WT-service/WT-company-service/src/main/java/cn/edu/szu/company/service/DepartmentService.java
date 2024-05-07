package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.domain.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zgr24
* @description 针对表【wt_department(部门)】的数据库操作Service
* @createDate 2024-04-28 16:39:41
*/
public interface DepartmentService extends IService<Department> {
    DeptDTO selectDeptByID(Long deptId);
    List<DeptDTO> selectHighestDeptByCompanyId(Long companyId);
    List<DeptDTO> selectDeptsByParentId(Long parentId);

    boolean updateUserDept(Long uid,Long did);

    boolean deleteDepartment(Long deptId);

    boolean createDepartment(Long companyId, DeptDTO deptDTO);
}