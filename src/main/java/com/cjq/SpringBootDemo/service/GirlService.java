package com.cjq.SpringBootDemo.service;


import com.cjq.SpringBootDemo.domain.Girl;
import com.cjq.SpringBootDemo.exception.MyException;
import com.cjq.SpringBootDemo.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GirlService {


    @Autowired
    private GirlRepository girlRepository;

    public List<Girl> findAll(){
        return girlRepository.findAll();
    }

    public Girl findOne(Integer id){
        return girlRepository.findOne(id);
    }

    public List<Girl> findByAge(Integer id){
        return girlRepository.findByAge(id);
    }


    /**
     * jpa的新增和更新都是save，根据有没有id或者id是否存在来判断。有id且数据库里存在则更新，否则新增
     * @param girl
     * @return
     */
    @Transactional
    public Girl save(Girl girl){//throws Exception{
        return girlRepository.save(girl);
    }

    /**
     * 批量新增或更新
     * @param girls
     */
    @Transactional
    public void batchSave(List<Girl> girls){
        List<Girl> tempGirls = new ArrayList<>();
        for(Girl girl : girls){
            tempGirls.add(girl);
            if (tempGirls.size() == 2){
                girlRepository.save(tempGirls);
                tempGirls.clear();
            }
        }
        if(!tempGirls.isEmpty()){
            girlRepository.save(tempGirls);
        }
    }



    public void delete(Integer id){
        girlRepository.delete(id);
    }


    /**
     * 批量删除
     * @param girls
     */
    public void batchDelete(List<Girl> girls){
        girlRepository.deleteInBatch(girls);
    }




    /**
     * 事务测试
     * @param girl
     * @return
     */
    @Transactional
    public Girl tx(Girl girl){//throws Exception{
        girl = girlRepository.save(girl);
//        if(girl.getAge() > 100){
//            throw new Exception("事务失效,非运行时异常，spring事务不回滚");
//        }
        if(girl.getAge() > 100){
            throw new MyException("事务不失效，运行时异常，spring事务回滚");
//           throw new MyException(ResultEnum.DATABASE_EXCEPTION);
        }
        return girl;
    }
}
