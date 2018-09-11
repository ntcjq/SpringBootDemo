package com.cjq.SpringBootDemo.repository;

import com.cjq.SpringBootDemo.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface GirlRepository extends JpaRepository<Girl,Integer> {

    //自定义通过年龄查询
    List<Girl> findByAge(Integer age);

    @Query("select count(id)   from Girl")
    Integer getGirlAmount();

    /**
     * 返回的是List里装的Object[]
     * @return
     */
    @Query("select cupSize , count(cupSize)   from Girl group by cupSize")
    List getGroupBy();
}
