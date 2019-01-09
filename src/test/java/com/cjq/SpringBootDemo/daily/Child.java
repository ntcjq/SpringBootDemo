package com.cjq.SpringBootDemo.daily;

/**
 * @Author: cuijq
 */
public class Child extends Parent{


    public String cpub;
    String cdef;
    protected String cpro;
    private String cpri;

    public void cmethodPub(){

    }

    void cmethodD(){

    }

    protected void cmethodPro(){

    }

    private String cmethodPri(){
        System.out.println("child private method");
        return "cmethodPri";
    }

    public String getCpub() {
        return cpub;
    }

    public void setCpub(String cpub) {
        this.cpub = cpub;
    }

    public String getCdef() {
        return cdef;
    }

    public void setCdef(String cdef) {
        this.cdef = cdef;
    }

    public String getCpro() {
        return cpro;
    }

    public void setCpro(String cpro) {
        this.cpro = cpro;
    }

    public String getCpri() {
        return cpri;
    }

    public void setCpri(String cpri) {
        this.cpri = cpri;
    }
}
