package com.cjq.SpringBootDemo.daily;

import com.cjq.SpringBootDemo.daily.anno.ClassAnno;
import com.cjq.SpringBootDemo.daily.anno.FieldAnno;
import com.cjq.SpringBootDemo.daily.anno.MethodAnno;

/**
 * @Author: cuijq
 */
@ClassAnno
public class Parent {


    @FieldAnno
    public String ppub;
    String pdef;
    protected String ppro;
    private String ppri;


    @MethodAnno
    public void pmethodPub(){

    }

    void pmethodD(){

    }

    protected void pmethodPro(){

    }

    private void pmethodPri(){

    }

    public String getPpub() {
        return ppub;
    }

    public void setPpub(String ppub) {
        this.ppub = ppub;
    }

    public String getPdef() {
        return pdef;
    }

    public void setPdef(String pdef) {
        this.pdef = pdef;
    }

    public String getPpro() {
        return ppro;
    }

    public void setPpro(String ppro) {
        this.ppro = ppro;
    }

    public String getPpri() {
        return ppri;
    }

    public void setPpri(String ppri) {
        this.ppri = ppri;
    }
}
