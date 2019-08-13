package com.cjq.SpringBootDemo.mapper;

import com.cjq.SpringBootDemo.domain.User;

import java.util.List;

public interface UserMapperByXml {

    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
