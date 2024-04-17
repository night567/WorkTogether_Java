package cn.edu.szu.company.service.impl;

import cn.edu.szu.company.dao.CompanyUserDao;
import cn.edu.szu.company.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.edu.szu.common.utils.RedisConstants.INVITE_CODE_KEY;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {
    @Autowired
    private CompanyUserDao companyUserDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<Long> selectUserIdsByCompanyId(Long companyId) {
        return companyUserDao.selectUserIdsByCompanyId(companyId);
    }

    @Override
    public boolean deleteMember(Long memberId) {
        return companyUserDao.deleteMember(memberId);
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
