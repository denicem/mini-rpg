package com.minirpg.game.model;

public class StoryManager {
    public String getStory(int act) {
        switch (act) {
            case 1:
                return "A giant bat swoops down! What do you do?";
            case 2:
                return "You bravely fight the bat and win! You find a small chest.";
            default: return "";
        }
    }
}
