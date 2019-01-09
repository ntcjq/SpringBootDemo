package com.cjq.SpringBootDemo.daily;

/**
 * @Author: cuijq
 */
public class Parent {

    public String ppub;
    String pdef;
    protected String ppro;
    private String ppri;



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
