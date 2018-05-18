package com.centit.demo.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
public class TestLombok {
    @Getter @Setter private int numa;
    @Getter @Setter private int numb;

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
