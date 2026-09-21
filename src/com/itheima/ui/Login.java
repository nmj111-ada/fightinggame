package com.itheima.ui;

import com.itheima.domain.User;
import com.itheima.domain.VerificationCode;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    //这个方法表示的就是登陆注册的主页面，以控制台的形式进行展示

    ArrayList<User> list = new ArrayList<>();

    public void start() {
        System.out.println("游戏的登陆注册页面打开了！");

        while (true) {
            System.out.println("欢迎来到文字格斗游戏！");
            System.out.println("请选择操作： 1. 登陆 2. 注册 3. 忘记密码 4. 退出");
            //注册时需要手机号，登陆时不需要
            //忘记密码的规则：
            //选择3后，输入用户名，查询用户是否存在
            //不存在，提示当前用户名未注册
            //存在，提示输入手机号，判断手机号是否正确，输入新密码
            //修改密码

            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch (choose){
                case "1":
                    login(list);
                    break;
                case "2":
                    register(list);
                    break;
                case "3":
                    forgetPassword(list);
                    break;
                case "4":
                    System.out.println("你选择了退出！");
                    System.exit(0); // 退出程序
                    break;
                default:
                    System.out.println("你的输入有误，请重新输入！");
            }
        }
    }
    //登录的操作
    public void login(ArrayList<User> list) {
        if (list.isEmpty()) {
            System.out.println("没有用户，请先注册！");
            return;
        }
        System.out.println("你选择了登陆！");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        //判断用户名是否在list中
        int index = findIndex(list, username);
        if(index == -1) {
            System.out.println("用户名未注册！");
            return;
        }

        User user = list.get(index);
        if(user.getStatus() == false){
            System.out.println("用户名已禁用,无法登录");
            return;
        }

        //判断验证码是否正确
        VerificationCode vc = new VerificationCode();//生成验证码
        String code = vc.getCode();
        System.out.println("请输入验证码：" + code);
        String inputCode = sc.next();
        if(!code.equals(inputCode)) {
            System.out.println("验证码错误！");
            return;
        }

        // 输入密码，最多三次机会，第三次错误则禁用该用户
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = sc.next();
            if (user.getPassword().equals(password)) {
                System.out.println("登陆成功,游戏启动！");
                FightingGame fg = new FightingGame();
                fg.gameStart(username);
                return;
            }
            int remaining = 2 - i; // 剩余次数
            if (remaining == 0) {
                user.setStatus(false); // 关键：把状态改为禁用
                System.out.println("密码错误三次，该用户已被禁用！");
                return;
            }
            System.out.println("密码错误！你还有" + remaining + "次机会");
        }
    }

    //注册的操作
    public void register(ArrayList<User> list) {
        System.out.println("你选择了注册！");
        //1.创建User对象
        User u = new User();
        //2.键盘录入用户名
        //  检验用户名是否符合要求
        //  u.setUsername();
        Scanner sc = new Scanner(System.in);
        String username = null;
        while (true) {
            System.out.println("请输入用户名：");
            username = sc.next();

            if(!checkLen(3, 6, username)){
                System.out.println("用户名长度不符合要求！必须是3到6之间");
                continue;
            }

            if(!checkUsername(username)){
                System.out.println("用户名只能由字母和数字组成");
                continue;
            }

            //判断用户名已经注册
            boolean isDuplicate = false;
            for(User user : list) {
                if(user.getUsername().equals(username)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("用户名已存在，请重新输入！");
                continue; // 关键：跳出外层 while 循环的本次执行，回到开头让用户重新输入
            }

            u.setUsername(username);
            break;
        }

        while(true) {
            //3.键盘录入密码
            System.out.println("请输入密码：");
            String password = sc.next();
            System.out.println("请确认密码：");
            String confirmPassword = sc.next();

            if(!password.equals(confirmPassword)) {
                System.out.println("两次输入的密码不一致！");
                continue;
            }

            //  检验密码是否符合要求
            if(!checkLen(6, 12, password)){
                System.out.println("密码长度不符合要求！必须是6到12之间");
                continue;
            }

            if(!password.matches("[a-zA-Z0-9]{6,12}")) {
                System.out.println("密码只能由字母和数字组成");
                continue;
            }

            //4.将用户对象添加到集合中
            u.setPassword(password);
            break;
        }
        list.add(u);
        //5.提示成功
        System.out.println("用户" + u.getUsername() + "注册成功！");
    }

    //忘记密码的操作
    public void forgetPassword(ArrayList<User> list) {
        System.out.println("你选择了忘记密码！");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        //判断用户名是否在list中
        int index = findIndex(list, username);
        if(index == -1) {
            System.out.println("用户名未注册！");
            return;
        }
        //输入手机号，判断手机号是否正确，输入新密码
        System.out.println("请输入手机号：");
        String phone = sc.next();
        if(!list.get(index).getPhoneNumber().equals(phone)) {
            System.out.println("手机号错误！");
            return;
        }
        System.out.println("请输入新密码：");
        String newPassword = sc.next();
        list.get(index).setPassword(newPassword);
        System.out.println("密码修改成功！");
    }

    public boolean checkLen(int minLen,int maxLen,String str) {
        if(str.length() < minLen || str.length() > maxLen){
            System.out.println("长度不符合要求！");
            return false;
        }
        return true;
    }

    //用户名只能由字母和数字组成，不能全部是数字
    public boolean checkUsername(String str) {
        return str.matches("[a-zA-Z0-9]{3,16}");
    }

    //在集合中找username所在的索引
    public int findIndex(ArrayList<User> list, String username) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}
