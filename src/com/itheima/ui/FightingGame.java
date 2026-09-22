package com.itheima.ui;

import com.itheima.domain.Consumable;
import com.itheima.domain.EnemyCharacters;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void gameStart(String username) {
        //1.显示游戏的标题
        System.out.println(username + "欢迎来到文字格斗游戏！");

        //2.创建玩家角色（名字+属性分配）
        HeroCharacter player = createPlayerCharacter(username);

        //3.显示创建角色信息和展示信息
        System.out.println("玩家角色创建成功！");
        System.out.println("初始属性为：");
        player.show();
        System.out.println("拥有技能为：" + "\n" + player.showSkill());

        //4.创建多个敌人列表
        //name:初级战士 hp:80 atk:15 def:10 MP:50 skill:猛击（150%伤害）消耗5MP
        //敏捷刺客 60 20 5 50 快速攻击（2次50%伤害）消耗5MP
        //重装坦克 120 10 20 50 防御姿态（下回合伤害减半，defending=true）消耗10MP
        //神秘法师 70 25 8 100 火球术（180%伤害）（180%伤害）消耗20MP
        //蓝量为0无法释放技能
        ArrayList<EnemyCharacters> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacters("初级战士", 80, 15, 10, "猛击", 50));
        enemyList.add(new EnemyCharacters("敏捷刺客", 60, 20, 5, "快速攻击", 50));
        enemyList.add(new EnemyCharacters("重装坦克", 120, 10, 20, "防御姿态", 50));
        enemyList.add(new EnemyCharacters("神秘法师", 70, 25, 8, "火球术", 100));

        //准备战斗
        int count = 1;  //记录当前是跟第几个敌人进行战斗
        int wins = 0;   //记录胜场

        while (player.isAlive()) {
            System.out.println("------------第" + count + "轮战斗------------");
            //5.1 第二场开始重置敌人的属性，敌人属性每场hp+10,atk+3,def+2
            if(wins != 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacters c = new EnemyCharacters();
                    c.maxHP = c.maxHP + wins * 10;
                    c.HP = c.maxHP;
                    c.attack = c.attack + wins * 3;
                    c.defense = c.defense + wins * 2;
                    c.defending = false;
                }
            }

            //5.2随机选择敌人
            int randomIndex = (int) (Math.random() * enemyList.size());
            EnemyCharacters enemy = enemyList.get(randomIndex);
            enemy.show();

            //5.3战斗开始
            System.out.println("========================================");
            System.out.println("第"+ count + "场战斗开始！对手是：" + enemy.name);

            //跟当前的敌人是第几回合
            int round = 1;
            while(player.isAlive()) {
                System.out.println("-----------------------------------------------");
                System.out.println("第"+ round + "个回合开始！");
                System.out.println(getHealthBar(player.name, player.HP, player.maxHP));
                System.out.println(getHealthBar(enemy.name, enemy.HP, enemy.maxHP));

                //5.4玩家回合：选择行动
                //1.普通攻击2.强力一击（消耗10点hp）3.生命回复技能
                System.out.println("请选择行动：1.普通攻击 2.强力一击 3.生命回复技能 4.回复魔力 5.使用消耗品");
                playerTurn(player, enemy);
                //5.5判断敌人是否死亡
                if(!enemy.isAlive()) {
                    System.out.println("恭喜！你击败了" + enemy.name);
                    //击败对手后，对手有概率掉落消耗品
                    Consumable drop = enemy.dropConsumable();
                    if (drop != null) {
                        player.addItem(drop.getName(), 1);
                        System.out.println(enemy.name + "掉落了：" + drop.getName());
                    } else {
                        System.out.println(enemy.name + "没有掉落任何东西。");
                    }
                    wins++;
                    break;
                }

                //5.6敌人回合,50%使用普通攻击，50%使用技能
                enemyTurn(enemy, player);

                //5.7判断玩家是否死亡
                if(!player.isAlive()) {
                    System.out.println("很遗憾，你被" + enemy.name + "击败了！");
                    //敌人嘲讽，随机输出一句话
                    //敌人嘲讽，随机输出一句话
                    System.out.println(enemy.name + "嘲讽道：" + enemy.getRandomTaunt());
                    break;
                }
                round++;
            }
            System.out.println("=======第" + count + "场战斗结束！=======");
            System.out.println("=======下面开始战斗结算=======");

            //5.8跟一个敌人的战斗结束之后，玩家胜利则回血回复20-40hp。玩家失败则退出游戏。
            //每3胜获得属性提升，询问y/n继续游戏
            if(player.isAlive()) {
                Random r = new Random();
                int healAmount = r.nextInt(21) + 20;
                player.heal(healAmount);
                System.out.println("战斗结束了！你获得了" + healAmount + "点hp！");
            }

            //5.9每3胜获得属性提升
            if(player.isAlive() && wins > 0 && wins % 3 == 0) {
                System.out.println("恭喜！你赢得了第" + wins + "场战斗！");
                player.maxHP += 30;
                player.attack += 5;
                player.defense += 3;
                System.out.println("你的最大生命值提升了30，攻击提升了5，防御提升了3！");
                System.out.println("当前你的属性为：");
                player.show();
            }

            //5.10询问玩家是否继续游玩
            if(player.isAlive()) {
                System.out.println("是否继续游玩？(y/n)");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if(choice.equals("y")) {
                    System.out.println("游戏继续！");
                    count++;
                } else if (choice.equals("n")) {
                    break;
                }else {
                    System.out.println("无效输入！默认继续游戏！");
                }
            }
        }

        //5.11最终结算
        System.out.println("游戏结束！你共赢得了" + wins + "场战斗！");
        System.out.println("感谢游玩文字版格斗游戏！");
        System.exit(0); // Exit the game
    }

    //获取血量条
    public String getHealthBar(String name,int HP,int maxHP) {
        //满血状态下打印20个方块
        StringBuilder healthBar = new StringBuilder();
        healthBar.append(name).append(": [");
        int length = (int) (HP * 20.0 / maxHP);
        for (int i = 0; i < length; i++) {
            healthBar.append("█");
        }
        for (int i = length; i < 20; i++) {
            healthBar.append("░");
        }
        healthBar.append("] ").append(HP).append("/").append(maxHP);
        return healthBar.toString();
    }

    //创建玩家角色，参数是玩家用户名
    //返回值是创建的玩家角色
    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("正在创建玩家角色...");
        System.out.println("您的角色名为：" + username);

        //属性分配
        int points = 30;

        System.out.println("您有" + points + "点属性点，可以分配给角色的属性为：HP、攻击、防御、蓝量");
        System.out.println("请输入您要分配的属性点数（HP、攻击、防御、蓝量）：");
        System.out.println("生命值每点加10hp");
        Scanner sc = new Scanner(System.in);
        int hpPoints = sc.nextInt();
        if(hpPoints < 0) {
            System.out.println("无效输入！默认分配0点");
            hpPoints = 0;
        }
        if(hpPoints > points) {
            System.out.println("属性点数不能超过30！默认分配30点");
            hpPoints = points;
        }

        //分配属性点
        points = points - hpPoints;

        //攻击力和防御力分配同上
        System.out.println("攻击力每点加2攻击");
        int attackPoints = sc.nextInt();
        if(attackPoints < 0) {
            System.out.println("无效输入！默认分配0点");
            attackPoints = 0;
        }
        if(attackPoints > points) {
            System.out.println("属性点数不足！");
            attackPoints = points;
        }
        points = points - attackPoints;

        System.out.println("防御力每点加1防御");
        int defensePoints = sc.nextInt();
        if(defensePoints < 0) {
            System.out.println("无效输入！默认分配0点");
            defensePoints = 0;
        }
        if(defensePoints > points) {
            System.out.println("属性点数不足！");
            defensePoints = points;
        }

        System.out.println("蓝量每点加5MP");
        int mpPoints = sc.nextInt();
        if(mpPoints < 0) {
            System.out.println("无效输入！默认分配0点");
            mpPoints = 0;
        }
        if(mpPoints > points) {
            System.out.println("属性点数不足！");
            mpPoints = points;
        }
        System.out.println("您分配的属性为：HP：" + hpPoints + "攻击：" + attackPoints + "防御：" + defensePoints + "蓝量：" + mpPoints);

        HeroCharacter player = new HeroCharacter(username, 100+hpPoints * 10, 10+attackPoints * 2, defensePoints , 50+mpPoints * 10);

        //添加玩家技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");
        player.skillList.add("回复魔力");
        
        //给玩家一些初始消耗品（道具名，数量）
        player.addItem("桃子", 1);
        player.addItem("花酿鸡", 1);
        
        return player;
    }

    //玩家回合：1.普通攻击2.强力一击（消耗10点hp）3.生命回复技能，消耗10hp，回复0-20hp 4.回复魔力，消耗10HP恢复10MP
    //玩家技能消耗5MP
    public void playerTurn(HeroCharacter player, EnemyCharacters enemy) {
        System.out.println("========你的回合========");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击（消耗10点hp）");
        System.out.println("3.生命回复技能");
        System.out.println("4.回复魔力，消耗10HP恢复10MP");
        System.out.println("5.使用消耗品");
        System.out.println("选择行动（1-5）：");
        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();
        if(choose < 1 || choose > 5) {
            System.out.println("无效输入！默认选择普通攻击");
            choose = 1;
        }

        switch (choose) {
            case 5:
                //使用消耗品：先展示背包（每个道具后面带数量，例如：桃子 X 3），再按名字选择
                System.out.println("你的背包有：");
                System.out.println(player.showPackage());
                System.out.println("请选择消耗品（输入消耗品名称）：");
                String item = sc.next();
                Consumable used = player.useItem(item);
                if (used != null) {
                    System.out.println(player.name + "使用了" + used.getName());

                    player.heal(used.getNum());
                    System.out.println(player.name + "恢复了" + used.getNum() + "点hp");

                } else {
                    System.out.println("你没有这个消耗品！");
                }
                break;
            case 4:
                //回复魔力
                if(player.HP < 10) {
                    System.out.println("你的hp不足10点，无法使用回复魔力");
                    break;
                }
                player.HP -= 10;
                player.MP += 10;
                System.out.println(player.name + "使用了回复魔力！恢复了10点mp");
                break;
            case 1:
                //普通攻击
                int damage1 = calculateDamage(player.attack , enemy.defense);
                enemy.takeDamage(damage1);
                System.out.println(player.name + "对" + enemy.name + "使用了普通攻击！造成了" + damage1 + "点伤害");
                break;
            case 2:
                //强力一击
                if(player.HP < 10) {
                    System.out.println("你的hp不足10点，无法使用强力一击");
                    break;
                }
                if(player.MP < 5) {
                    System.out.println("你的mp不足5点，无法使用强力一击");
                    break;
                }
                player.MP -= 5;
                int Damage2 = calculateDamage((int)(player.attack * 1.8), enemy.defense);
                enemy.takeDamage(Damage2);
                player.takeDamage(10);
                System.out.println(player.name + "对" + enemy.name + "使用了强力一击！造成了" + Damage2 + "点伤害");
                break;
            case 3:
                //生命回复
                if(player.HP < 10) {
                    System.out.println("你的hp不足10点，无法使用生命回复");
                    break;
                }else if (player.MP < 5) {
                    System.out.println("你的mp不足5点，无法使用生命回复");
                    break;
                } else {
                    int healAmount = (int) (Math.random() * 21);
                    player.heal(healAmount);
                    player.MP -= 5;
                    System.out.println(player.name + "使用了生命回复！回复了" + healAmount + "点hp");
                }
            default:
                System.out.println("无效输入！默认选择普通攻击");
                break;
        }
    }

    //计算伤害
    public int calculateDamage(int attack, int defense) {
        int damage = attack - defense;
        if(damage < 1) {
            damage = 1;
        }
        return damage;
    }

    //对手回合
    public void enemyTurn(EnemyCharacters enemy, HeroCharacter player) {
        System.out.println("========敌人回合========");
        int enemyDamage = calculateDamage(enemy.attack, player.defense);
        String action;
        Random r = new Random();
        int num = r.nextInt(10);
        if (num < 5) {
            action = "普通攻击";
        } else {
            action = "技能" + enemy.skill;
        }

        //name:初级战士 hp:80 atk:15 def:10 skill:猛击（150%伤害）
        //敏捷刺客 60 20 5 快速攻击（2次50%伤害）
        //重装坦克 120 10 20 防御姿态（下回合伤害减半，defending=true）
        //神秘法师 70 25 8 火球术（180%伤害）（180%伤害）
        switch (action) {
            case "普通攻击":
                player.takeDamage(enemyDamage);
                System.out.println(enemy.name + "对" + player.name + "使用了普通攻击！造成了" + enemyDamage + "点伤害");
                break;
            case "猛击":
                if(enemy.MP < 5) {
                    //蓝不够使用普通攻击
                    player.takeDamage(enemyDamage);
                    System.out.println(enemy.name + "对" + player.name + "使用了普通攻击！造成了" + enemyDamage + "点伤害");
                    break;
                }
                enemy.MP -= 5;
                int enemyDamage2 = calculateDamage((int)(enemy.attack * 1.5), player.defense);
                player.takeDamage(enemyDamage2);
                System.out.println(enemy.name + "对" + player.name + "使用了猛击！造成了" + enemyDamage2 + "点伤害");
                break;
            case "快速攻击":
                if(enemy.MP < 5) {
                    //蓝不够使用普通攻击
                    player.takeDamage(enemyDamage);
                    System.out.println(enemy.name + "对" + player.name + "使用了普通攻击！造成了" + enemyDamage + "点伤害");
                    break;
                }
                enemy.MP -= 5;
                int enemyDamage3 = calculateDamage((int)(enemy.attack * 0.5), player.defense) * 2;
                player.takeDamage(enemyDamage3);
                System.out.println(enemy.name + "对" + player.name + "使用了快速攻击！造成了" + enemyDamage3 + "点伤害");
                break;
            case "防御姿态":
                if(enemy.MP < 5) {
                    //蓝不够使用普通攻击
                    player.takeDamage(enemyDamage);
                    System.out.println(enemy.name + "对" + player.name + "使用了普通攻击！造成了" + enemyDamage + "点伤害");
                    break;
                }
                enemy.MP -= 5;
                System.out.println(enemy.name + "对" + player.name + "使用了防御姿态！下回合伤害减半");
                break;
            case "火球术":
                if(enemy.MP < 5) {
                    //蓝不够使用普通攻击
                    player.takeDamage(enemyDamage);
                    System.out.println(enemy.name + "对" + player.name + "使用了普通攻击！造成了" + enemyDamage + "点伤害");
                    break;
                }
                enemy.MP -= 5;
                int enemyDamage4 = calculateDamage((int)(enemy.attack * 1.8), player.defense);
                player.takeDamage(enemyDamage4);
                System.out.println(enemy.name + "对" + player.name + "使用了火球术！造成了" + enemyDamage4 + "点伤害");
                break;
        }


        //
    }
}

