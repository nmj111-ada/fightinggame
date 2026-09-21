package com.itheima.domain;

import java.util.ArrayList;

//我方游戏人物的角色
public class HeroCharacter extends Characters {
    public ArrayList<String> skillList;
    public HeroCharacter() {
        super();
        skillList = new ArrayList<String>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense, int MP) {
        super(name, HP, attack, defense, MP);
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
}
