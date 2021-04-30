package com.java.datageneratingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.datageneratingsystem.entity.User;
import com.java.datageneratingsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserTest() {
        List<User> result = userMapper.selectList(new QueryWrapper<>());
        return result;
    }
}
