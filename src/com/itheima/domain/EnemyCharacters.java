package com.itheima.domain;

public class EnemyCharacters extends Characters {

    public String skill;
    public boolean defending;
    public int MP;

    public EnemyCharacters() {
        super();
    }

    public EnemyCharacters(String name, int HP, int attack, int defense, String skill, int MP) {
        super(name, HP, attack, defense, MP);
        this.skill = skill;
        this.MP = MP;
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

    //新增专门存嘲讽话术的数组
    public String[] tauntPhrases = {"你来吧，我等的就是你！",
                                    "你的攻击就像一缕轻风，毫无威胁！",
                                    "你的防御就像一堵墙，但对我来说，只是纸糊的！",
                                    "你的魔法就像一道闪电，但对我来说，只是微不足道的！",
                                    "你的存在就像是一团黑暗，但对我来说，只是微不足道的！"
    };

    public String getRandomTaunt() {
        int index = (int) (Math.random() * tauntPhrases.length);
        return tauntPhrases[index];
    }
}
