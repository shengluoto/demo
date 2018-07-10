package com.example.demo.test;

import org.junit.Test;

public class TestEnum {

    @Test
    public void test() {
        // System.out.println(TypeEnum.battery.name());
        // try {
        // Enum.valueOf(TypeEnum.class, "aaa");
        // } catch (IllegalArgumentException e) {
        // System.out.println(e);
        // }
        String judgeType = "cal&trsd";
        boolean flag = false;
        long startTime = System.nanoTime();
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if(typeEnum.getId().equals(judgeType)){
                flag=true;
                break;
            }
        }
//        TypeEnum[] typeEnum=TypeEnum.values();
//        int length = typeEnum.length;
//        System.out.println(length);
//        for (int i=0;i<length;i++) {
//            if(typeEnum[i].getId().equals(judgeType)){
//                flag=true;
//                System.out.println(i);
//                break;
//            }
//        }
        long endTime = System.nanoTime();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
        System.out.println(flag);
        int a=32;
        int b=9;
        System.out.println(a+b+"");
    }

}
