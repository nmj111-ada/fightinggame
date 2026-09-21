package com.itheima.domain;

import java.util.ArrayList;
import java.util.Random;

public class VerificationCode {
    //验证码要求：长度为5，由四位大小写字母和一位数字组成，同一个字母可重复
    //数字可以出现在任何地方
    public static String getCode() {
        ArrayList<java.lang.Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('A' + i));
            list.add((char) ('a' + i));
        }
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        sb.append(r.nextInt(10));

        char[] arr = sb.toString().toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);
            char temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        String code = new String(arr);
        return code;
    }
}
