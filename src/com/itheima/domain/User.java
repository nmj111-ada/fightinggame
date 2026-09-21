package com.itheima.domain;

import java.util.Random;

public class User {
    //id,用户名,密码,状态
    private String id; //heima+5位随机数字
    private String username;
    private String password;
    private Boolean status;
    private String phoneNumber;

    public User() {
        //调用creatID()方法，设置id
        id = creatId();
        //修改state的值
        status = true;
    }

    public User(String id, String username, String password, Boolean status, String phoneNumber) {
        id = creatId();
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        status = true;
    }

    public String creatId() {
        StringBuilder sb = new StringBuilder();

        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }
        return "heima" + sb.toString();
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
