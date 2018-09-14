package com.cjq.SpringBootDemo.controller;


import com.cjq.SpringBootDemo.domain.Girl;
import com.cjq.SpringBootDemo.domain.Result;
import com.cjq.SpringBootDemo.service.GirlService;
import com.cjq.SpringBootDemo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful风格接口
 */
@RestController
@RequestMapping(value="girl")
public class GirlController {

    @Autowired
    private GirlService girlService;

    @GetMapping("/getGirls")
    public List<Girl> getGirls(){
        List<Girl> list = girlService.findAll();
        return list;
    }

    @GetMapping(value="getGirls/{id}")
    public Girl getGirls(@PathVariable("id") Integer id){
        return girlService.findOne(id);
    }

    @GetMapping("getGirls/age")
    public List<Girl> getGirlsByAge(@RequestParam("age") Integer age){
        List<Girl> list = girlService.findByAge(age);
        return list;
    }

    @PostMapping("addGirl")
    public Result<Girl> addGirl(@Valid Girl g , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResultUtil.faild(1,bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultUtil.success(girlService.save(g));
    }

    @PostMapping("addGirls")
    public Result addGirls(@Valid @RequestBody List<Girl> girls , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.faild(1,bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        girlService.batchSave(girls);
        return ResultUtil.success();
    }


    @PutMapping("updateGirl")
    public Result<Girl> updateGirl(Girl g){
        return ResultUtil.success(girlService.save(g));
    }

    @DeleteMapping("deleteGirl/{id}")
    public Result deleteGirl(@PathVariable("id") Integer id){
        girlService.delete(id);
        return ResultUtil.success();
    }
    @PostMapping("batchDelete")
    public Result batchDelete(@RequestBody List<Girl> girls){
        girlService.batchDelete(girls);
        return ResultUtil.success();
    }
    @RequestMapping("getGirlAmount")
    public Result getGirlAmount(){
        return ResultUtil.success(girlService.getGirlAmount());
    }

    @RequestMapping("getGroupBy")
    public Result getGroupBy(){
        List list = girlService.getGroupBy();
        return ResultUtil.success(list);
    }

    /**
     * 事务
     * @param g
     * @param bindingResult
     * @return
     */
    @PostMapping("tx")
    public Result<Girl> tx(@Valid Girl g , BindingResult bindingResult){//throws Exception{

        if(bindingResult.hasErrors()){
            return ResultUtil.faild(1,bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return ResultUtil.success(girlService.tx(g));
    }

    /**
     * 动态sql查询
     * @param girl
     * @return
     */
    @PostMapping("complexQuery")
    public Result complexQuery( Girl girl){
        return ResultUtil.success(girlService.complexQueryBetter(girl));
    }
}
