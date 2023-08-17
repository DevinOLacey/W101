package com.wiz101.calculator.dto;

/****

 Based on previous Calculator I have created an
 Object that store the Initial starting Calculated Values

 Values:
 ======================
 -Spell DMG:    111
 -DMG:          111
 -Crit Mod:     111
 -Pierce:       111
 -Enemy Resist: 111
 ======================

********/
public class WizStats {
    private int spelldmg;
    private int damage;
    private int crit;
    private int peirce;
    private int enemyres;

    public WizStats() {
    }

//    BuffStorm
    public WizStats(int spelldmg , int damage, int crit, int peirce, int enemyres){
      super();

      this.spelldmg = spelldmg;
      this.damage = damage;
      this.crit = crit;
      this.peirce = peirce;
      this.enemyres = enemyres;
    };

    public int getSpelldmg() {
        return spelldmg;
    }


    public void setSpelldmg(int spelldmg) {
        this.spelldmg = spelldmg;
    }



    public int getCrit() {
        return crit;
    }

    public void setCrit(int crit) {
        this.crit = crit;
    }

    public int getPeirce() {
        return peirce;
    }

    public void setPeirce(int peirce) {
        this.peirce = peirce;
    }

    public int getEnemyres() {
        return enemyres;
    }

    public void setEnemyres(int enemyres) {
        this.enemyres = enemyres;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}