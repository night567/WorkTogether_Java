package cn.edu.szu.user.service.impl;

import cn.edu.szu.user.mapper.MemberMapper;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public List<User> selectUserByUserIds(List<Integer> user_ids) {
        return memberMapper.selectUserByUserIds(user_ids);
    }
}
