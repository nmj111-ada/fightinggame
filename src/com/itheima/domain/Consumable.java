package com.itheima.domain;

public class Consumable {
    //name是恢复道具的名字
    //num是恢复的血量

    private final String name;
    private final int num;

    public static final Consumable[] consumables = {
            new Consumable("桃子", 10),
            new Consumable("煎蛋", 20),
            new Consumable("花酿鸡", 30),
            new Consumable("黑背鲈鱼",40),
            new Consumable("白玉汤",50)
    };

    public Consumable(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

}
