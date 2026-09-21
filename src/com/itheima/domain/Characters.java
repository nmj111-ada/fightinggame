package com.itheima.domain;

public class Characters {
    //名字，血量，最大血量，攻击，防御
    public String name;
    public int HP;  //当前血量
    public int maxHP;
    public int attack;
    public int defense;
    public int MP;  //新增蓝条（魔法值）
    //无参构造
    public Characters() {
    }
    //有参构造
    public Characters(String name, int HP, int attack, int defense, int MP) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
        this.MP = MP;
    }

    //判断是否存活
    public boolean isAlive() {
        return HP > 0;
    }

    //恢复血量
    public void heal(int amount) {
        HP += amount;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    //回合制
    public void takeDamage(int damage) {
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
    }

    //展示人物属性
    public void show() {
        System.out.println("名称：" + name + " 血量：" + HP + " 攻击：" + attack + " 防御：" + defense + " 蓝条：" + MP);
    }
}
