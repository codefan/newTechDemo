package com.centit.demo.kotlin;

public class TestLombok {

    private int numa;
    private int numb;

    public TestLombok(){
        numa = 1;
        numb = 2;
    }

    public int getNuma() {
        return numa;
    }

    public void setNuma(int numa) {
        this.numa = numa;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        this.numb = numb;
    }

    public int sum(){
        return numa+numb;
    }

    public static void main(String [] args){
        TestLombok testLombok = new TestLombok();
        testLombok.setNuma(12);
        testLombok.setNumb(15);
        System.out.println(testLombok.sum());

    }
}
