package com.minirpg.game.model.Enemy;



public class Enemy  {
    protected String name;


    public Enemy(String name){
        this.name = name;
    }



    public String attack(Character target){
        return "Enemy Attack";
    }
}
