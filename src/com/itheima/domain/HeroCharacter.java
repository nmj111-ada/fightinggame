package com.itheima.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//我方游戏人物的角色
public class HeroCharacter extends Characters {
    public ArrayList<String> skillList;
    //道具背包：key是道具名字，value是拥有数量，例如 "桃子" -> 3
    public Map<String, Integer> packageMap;

    public HeroCharacter() {
        super();
        skillList = new ArrayList<String>();
        packageMap = new HashMap<String, Integer>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense, int MP) {
        super(name, HP, attack, defense, MP);
        packageMap = new HashMap<String, Integer>();
        skillList = new ArrayList<String>();
    }

    public String showSkill() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(skillList.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    //往背包里添加道具，count是本次增加的数量（已存在则累加）
    public void addItem(String name, int count) {
        packageMap.put(name, packageMap.getOrDefault(name, 0) + count);
    }

    //展示背包内容，格式：道具名 X 数量
    public String showPackage() {
        if (packageMap.isEmpty()) {
            return "背包是空的";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : packageMap.entrySet()) {
            sb.append(entry.getKey()).append(" X ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    //使用一个道具：数量减1，减到0就从背包移除；
    //成功则返回被使用的道具（可从中取回血数值），没有该道具则返回null
    public Consumable useItem(String name) {
        Integer count = packageMap.get(name);
        if (count == null || count <= 0) {
            return null;
        }
        if (count == 1) {
            packageMap.remove(name);
        } else {
            packageMap.put(name, count - 1);
        }
        return Consumable.findByName(name);
    }
}
