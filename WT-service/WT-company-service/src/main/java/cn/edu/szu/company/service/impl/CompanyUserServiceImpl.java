package cn.edu.szu.company.service.impl;

import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.company.dao.CompanyUserDao;
import cn.edu.szu.company.pojo.CompanyUser;
import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.service.CompanyUserService;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.feign.pojo.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.Date;
import java.util.List;

import static cn.edu.szu.common.utils.RedisConstants.INVITE_CODE_KEY;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {
    @Autowired
    private CompanyUserDao companyUserDao;
    @Autowired
    private UserClient userClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<Long> selectUserIdsByCompanyId(Long companyId) {
        return companyUserDao.selectUserIdsByCompanyId(companyId);
    }


    @Override
    public List<MemberDTO> getAllMember(Long companyId) {
        // 获取企业成员数据
        List<MemberDTO> companyUsers = companyUserDao.selectAllByCompanyId(companyId);

        for (MemberDTO member : companyUsers) {
            UserDTO user = userClient.getUserById(member.getId());
            System.out.println(user);
            if (user != null) {
                member.setName(user.getName());
                member.setEmail(user.getEmail());
                member.setPosition("普通职员");
            } else {
                companyUsers.remove(member);
            }
        }

        return companyUsers;
    }

    @Override
    public boolean setMemberAsDeleted(Long memberId,Long companyId) {
        return companyUserDao.setMemberAsDeleted(memberId,companyId);
    }

    @Override
    public boolean joinCompany(String token, String code) {
        // 验证验证码有效期
        String key = INVITE_CODE_KEY + code;
        String cacheEmail = (String) stringRedisTemplate.opsForHash().get(key, "email");
        Long companyId = getCompanyIdByInviteCode(code);
        System.out.println(cacheEmail);
        if (cacheEmail == null || cacheEmail.isEmpty()) {
            System.out.println("验证码已过期");
            return false;
        }
        stringRedisTemplate.delete(key);

        //TODO：校验用户id
        Long id = JwtUtil.getUserId(token);
        if (id == null) {
            System.out.println("用户id错误");
            return false;
        }
        UserDTO user = userClient.getUserById(id);
        System.out.println(user);
        if (!cacheEmail.equals(user.getEmail())) {
            System.out.println("邮箱不匹配");
            return false;
        }

        //写入数据库
        CompanyUser companyUser = new CompanyUser();
        companyUser.setUserId(id);
        companyUser.setCompanyId(companyId);
        companyUser.setDeptId(1L); // TODO:加入部门，暂时加入1L
        companyUser.setStatus(true);
        companyUser.setJoinTime(new Date());
        companyUser.setIsDeleted(false);
        System.out.println(companyUser);
        companyUserDao.insert(companyUser);

        return true;
    }

    @Override
    public Long getCompanyIdByInviteCode(String code) {
        // 检查验证码是否存在
        Boolean hasCode = stringRedisTemplate.hasKey(INVITE_CODE_KEY + code);
        if (Boolean.TRUE.equals(hasCode)) {
            // 获取与验证码关联的公司ID
            Object companyIdStr = stringRedisTemplate.opsForHash().get(INVITE_CODE_KEY + code, "companyId");
            if (companyIdStr != null) {
                if (companyIdStr instanceof String) {
                    try {
                        return Long.parseLong((String) companyIdStr);
                    } catch (NumberFormatException e) {
                        return null; // 或者处理错误
                    }
                }
            }
        }
        // 如果验证码不存在或没有关联的公司ID，则返回null
        return null;
    }
}
