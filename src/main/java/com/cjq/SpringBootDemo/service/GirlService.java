package com.cjq.SpringBootDemo.service;


import com.cjq.SpringBootDemo.domain.Girl;
import com.cjq.SpringBootDemo.exception.MyException;
import com.cjq.SpringBootDemo.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
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

    public Integer getGirlAmount(){
        return girlRepository.getGirlAmount();
    }
    public List getGroupBy(){
        return girlRepository.getGroupBy();
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


    /**
     * 动态sql查询
     * @param girl
     * @return
     */
    public List<Girl> complexQuery(Girl girl){

        return girlRepository.findAll(new Specification<Girl>() {
            @Override
            public Predicate toPredicate(Root<Girl> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate namePredicate = null;
                if(null != girl && !StringUtils.isEmpty(girl.getName())) {
                    // 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
                    namePredicate = builder.like(root.<String> get("name"), "%" + girl.getName() + "%");
                }
                Predicate cupSizePredicate = null;
                if(null != girl && !StringUtils.isEmpty(girl.getCupSize())) {
                    cupSizePredicate = builder.equal(root.<String> get("cupSize"), girl.getCupSize());
                }
                Predicate agePredicate = null;
                if(null != girl && girl.getAge() != null) {
                    agePredicate = builder.equal(root.<Integer> get("age"), girl.getAge());
                }

                //in的用法比较特殊  如下：
//                List<Integer> ids = new ArrayList<>();
//                ids.add(1);
//                ids.add(2);
//                Predicate idIn = root.<Integer>get("id").in(ids);

                //between的使用
//                Predicate datePredicate = null;
//                if(date!=null&&!date.equals("")){
//                    //处理时间
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    Date startDate;
//                    Date endDate;
//                    try {
//                        startDate = format.parse(date);
//                    } catch (ParseException e) {
//                        startDate = new Date(946656000000L);//2000 01 01
//                    }
//                    endDate = startDate;
//                    Calendar calendar = Calendar.getInstance() ;
//                    calendar.setTime(endDate);
//                    calendar.add(Calendar.DATE, 1);
//                    endDate = calendar.getTime();
//                    calendar = null;
//                    datePredicate = builder.between(root.<Date>get("insDate"),startDate,endDate);
//                }


                //where name like '%%' and age =  and cupSize = ;
                return query.where(namePredicate,agePredicate,cupSizePredicate).getRestriction();
                //builder.or()的作用就是把放在里面的Predicate用or连接
                //where age =  and (name like '%%' or cupSize = );
//              return query.where(agePredicate,builder.or(cupSizePredicate,namePredicate)).getRestriction();
            }
        });
    }

    /**
     * 动态sql查询（优化）
     * @param girl
     * @return
     */
    public List<Girl> complexQueryBetter(Girl girl){
        return girlRepository.findAll(where(girl));
    }

    /**
     * 动态sql查询（分页）
     * @param girl
     * @return
     */
    public Page<Girl> complexQueryBetter(Girl girl, Pageable pageable){
        return girlRepository.findAll(where(girl),pageable);
    }

    /**
     * 条件查询时动态组装条件
     */
    private Specification<Girl> where(Girl girl){
        return new Specification<Girl>() {
            @Override
            public Predicate toPredicate(Root<Girl> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if(null != girl && !StringUtils.isEmpty(girl.getName())) {
                    predicates.add(builder.like(root.<String> get("name"), "%" + girl.getName() + "%"));
                }
                if(null != girl && !StringUtils.isEmpty(girl.getCupSize())) {
                    predicates.add(builder.equal(root.<String> get("cupSize"), girl.getCupSize()));
                }
                if(null != girl && girl.getAge() != null) {
                    predicates.add(builder.equal(root.<Integer> get("age"), girl.getAge()));
                }
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }


}
