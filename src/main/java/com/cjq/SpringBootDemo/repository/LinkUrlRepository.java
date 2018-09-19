package com.cjq.SpringBootDemo.repository;

import com.cjq.SpringBootDemo.domain.LinkUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LinkUrlRepository extends JpaRepository<LinkUrl,Integer> ,JpaSpecificationExecutor<LinkUrl> {


}
