package com.cjq.SpringBootDemo.repository;


import com.cjq.SpringBootDemo.domain.LinkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LinkTypeRepository extends JpaRepository<LinkType,Integer>,JpaSpecificationExecutor<LinkType> {


}
