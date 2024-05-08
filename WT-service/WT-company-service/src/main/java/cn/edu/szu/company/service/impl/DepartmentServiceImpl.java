package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.mapper.CompanyUserMapper;
import cn.edu.szu.company.mapper.DepartmentMapper;
import cn.edu.szu.company.pojo.DeptDTO;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.Department;
import cn.edu.szu.company.pojo.domain.UserCompanyRequest;
import cn.edu.szu.company.service.DepartmentService;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_department(部门)】的数据库操作Service实现
 * @createDate 2024-04-28 16:39:41
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private CompanyUserMapper CompanyUserMapper;

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
    public List<DeptDTO> selectHighestDeptByCompanyId(Long companyId) {
        List<DeptDTO> deptDTOS = departmentMapper.selectHighestDeptByCompanyId(companyId);
        for (DeptDTO deptDTO : deptDTOS) {
            deptDTO.setManagerName(userClient.getUserById(deptDTO.getManagerId()).getName());
        }
        return deptDTOS;
    }

    //根据上级部门ID查看子部门列表
    @Override
    public List<DeptDTO> selectDeptsByParentId(Long parentId) {
        List<DeptDTO> deptDTOS = departmentMapper.selectDeptsByParentId(parentId);
        for (DeptDTO deptDTO : deptDTOS) {
            deptDTO.setManagerName(userClient.getUserById(deptDTO.getManagerId()).getName());
        }
        return deptDTOS;
    }

    @Override
    public boolean updateUserDept(Long uid, Long did) {
        try {
            int k = departmentMapper.updateUserDept(uid, did);
            System.out.println(uid + "     " + did);
            if (k <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public boolean createDepartment(Long companyId, DeptDTO deptDTO) {
        // 实现创建部门的方法
        try {
            Department department = new Department();
            department.setCompanyId(companyId);
            //department.setParentId(deptDTO.getParentId());

            LambdaQueryWrapper<Department> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Department::getName, deptDTO.getParentName());
            Department parent = departmentMapper.selectOne(lqw);
            if (parent == null) {
                return false;
            }
            department.setParentId(parent.getId());

            department.setName(deptDTO.getName());
            department.setManagerId(deptDTO.getManagerId());
            department.setIntroduction(deptDTO.getIntroduction());
            department.setNum(1L);
            department.setCreateTime(new Date());
            department.setJob(0); // 初始待分配任务数为0

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

    @Override
    @Transactional
    public boolean deleteDepartments(List<Long> deptIds) {
        try {
            return departmentMapper.deleteBatchIds(deptIds) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateDUPosition(UserCompanyRequest request) {
        Long pid = departmentMapper.selectPositionId(request.getPosition());
        if (pid == null) {
            return false;
        }
        int k = departmentMapper.updateUDPosition(request.getUid(), request.getDid(), pid);
        if (k == 0) {
            return false;
        }

        return true;
    }

    @Override
    public List<MemberDTO> getDeptMember(Long deptId, Long companyId) {
        List<MemberDTO> deptMembers;
        if (deptId == 0L) {
            deptMembers = departmentMapper.selectAllByCompanyId(companyId);
        } else {
            deptMembers = departmentMapper.getDeptMember(deptId, companyId);
        }
        for (MemberDTO deptMember : deptMembers) {
            String id = deptMember.getId();
            UserDTO user = userClient.getUserById(Long.valueOf(id));
            deptMember.setEmail(user.getEmail());
            deptMember.setName(user.getName());
        }

        return deptMembers;
    }

    @Override
    public boolean createOrUpdateByExcel(Long companyId, MultipartFile deptFile) {
        // 检查输入参数是否为null
        if (companyId == null || deptFile == null) {
            throw new RuntimeException("上传文件异常");
        }

        // 通过Excel文件逐个添加部门
        try {
            // 读取Excel文件
            ExcelReader reader = ExcelUtil.getReader(deptFile.getInputStream());
            List<List<Object>> rowList = reader.read(2); // 从第二行开始读取数据
            for (List<Object> row : rowList) {
                //定义部门数据
                Long id = null;
                Long parentId = null;
                String deptName = null;
                String email = null;
                //获取四列的数据
                Object row1 = row.get(1);
                Object row2 = row.get(2);
                Object row3 = row.get(3);

                DeptDTO deptDTO = new DeptDTO();
                if (row1 instanceof Long && row1 != null) {
                    parentId = (Long) row1;
                }
                if (row2 instanceof String) {
                    deptName = (String) row2;
                }
                if (row3 instanceof String) {
                    email = (String) row3;
                }

                //存入部门实体
                deptDTO.setParentId(parentId);
                deptDTO.setName(deptName);
                Long userId = userClient.getUserByMail(email);
                deptDTO.setManagerId(userId);
                // 创建/更新部门
                Object row0 = row.get(0); // 有时excel为空时row[0]会返回A（列名）
                if (row0 == null || StrUtil.isBlank(row0.toString()) || row0.toString().equals("A")) {
                    //创建部门
                    boolean department = createDepartment(companyId, deptDTO);
                } else {
                    if (row0 instanceof Long) {
                        id = (Long) row0;
                    }
                    deptDTO.setId(id);
                    departmentMapper.updateDept(deptDTO);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public List<Department> selectDeptByCompanyId(Long companyId) {
        return departmentMapper.selectDeptByCompanyId(companyId);
    }

    @Override
    public Boolean updateDept(DeptDTO deptDTO) {
        return departmentMapper.updateDept(deptDTO);
    }
}




