package com.itheima.domain;

public class EnemyCharacters extends Characters {

    public String skill;
    public boolean defending;

    public EnemyCharacters() {
        super();
    }

    public EnemyCharacters(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    //重写受伤的方法
    public void takeDamage(int damage) {
        if (defending) {
            damage = damage /2 > 1 ? damage /2 : 1;
            defending = false;
        }
        //调用父类的方法扣除血量
        super.takeDamage(damage);
    }
}
