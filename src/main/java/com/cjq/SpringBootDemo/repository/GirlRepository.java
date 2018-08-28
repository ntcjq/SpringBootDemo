package com.cjq.SpringBootDemo.repository;

import com.cjq.SpringBootDemo.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GirlRepository extends JpaRepository<Girl,Integer> {

    //自定义通过年龄查询
    List<Girl> findByAge(Integer age);
}
