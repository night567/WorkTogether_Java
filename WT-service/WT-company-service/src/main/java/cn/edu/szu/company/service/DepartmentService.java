package cn.edu.szu.company.service;

import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.pojo.domain.UserCompanyRequest;
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

    boolean updateUserDept(Long uid, Long did);

    boolean deleteDepartment(Long deptId);

    boolean createDepartment(Long companyId, DeptDTO deptDTO);

    Long selectIdByName(String fatherDeptName);

    boolean deleteDepartments(List<Long> deptIds); // 批量删除部门

    boolean updateDUPosition(UserCompanyRequest request);

    List<MemberDTO> getDeptMember(Long deptId, Long companyId);
}