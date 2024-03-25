package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.user.dao.UserDao;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
        // 检验邮箱正确性
        if (!RegexUtils.isEmail(email)) {
            return null;
        }

        // 查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, email);
        return userDao.selectOne(lqw);
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            return null;
        }

        return userDao.selectById(id);
    }
}
