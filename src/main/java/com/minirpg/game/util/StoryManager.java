package com.minirpg.game.util;

import com.minirpg.game.model.Item;
import com.minirpg.game.model.Potion;
import com.minirpg.game.model.StrengthPotion;
import com.minirpg.game.model.IronCharm;

public class StoryManager {
    public static final int ACT_HOME = -1; //Flee - Go Home
    public static final int ACT_1 = 1; // Intro + Scream
    public static final int ACT_2 = 2; // Forest
    public static final int ACT_3 = 3; // Combat
    public static final int ACT_4 = 4; // In front of castle
    public static final int ACT_5 = 5; // Inside castle - Combat - Dragon

    public enum EnemyType {
        FOREST_MAGE,
        DRAMATIC_ELF
    }

    public enum ItemType {
        POTION_HP,
        STRENGTHPOTION_ATK,
        IRONCHARM_DEF,
        SWORD_ATK,
        POTION_COURAGE
    }

    public enum Ending {
        GOOD,
        BAD,
        COWARD,
        RETREAT
    }

    public String[] getStoryChunks(int act) {
        return switch (act) {
            case ACT_1 -> new String[]{
                    "You are a young recruit of the Royal Knight Order. To become a full-fledged knight, you must soon complete your first official trial, a solo mission designed to test courage, judgement, and (ideally) zero dying.",
                    "You decided to train your swordsmanship and footwork in the nearby forest.",
                    "You walk through the woods, humming to yourself — when a distant scream cuts through the air. You freeze. \"That didn’t sound like a friendly squirrel.\" \"Aaaaaaaah\""
            };
            case ACT_2 -> new String[]{
                    "You move toward the sound carefully.",
                    "The bushes to your right begin to rustle violently.",
                    "Before you can prepare a heroic pose, someone leaps out."
            };
            case ACT_3 -> new String[]{
                    "You take a moment to catch your breath. Your muscles ache, but you feel stronger with every encounter.\n\n" +
                    "The path deeper into the forest is still open, but the tall spires of the castle are visible in the distance.\n\n" +
                    "Do you want to continue training here, or are you ready to face the castle?"
            };
            case ACT_4 -> new String[]{
                    "A grand castle towers above you.",
                    "A beautiful princess stands at the gate. \"Oh noooo! A scary monster is inside! I need a biiig strong knight to save me!!\"",
                    "She smiles a little too brightly. \"Absolutely! Totally traumatized! Let’s hurry before anything bad happens!\""
            };
            case ACT_5 -> new String[]{
                    "You step into the castle. The doors slam shut behind you. Heat fills the hall.",
                    "From the shadows, the princess steps out, her smile widening unnaturally.",
                    "The air shimmers - and in a burst of flame, her form melts away, revealing a massive dragon. The final trial begins."
            };
            default -> new String[]{""};
        };
    }

    public String getButtonText(int act, int option) {
        switch (act) {
            case ACT_1:
                return (option == 1) ? "Investigate the Scream" : (option == 2) ? "Run for your life" : "";
            case ACT_2:
                return (option == 1) ? "Fight!" : (option == 2) ? "Run for your life" : (option == 3) ? "Try to talk" : "";
            case ACT_3:
                return (option == 1) ? "Continue to train" : (option == 2) ? "I'm ready" : "";
            case ACT_4:
                return (option == 1) ? "Go Inside" : (option == 2) ? "Run for your life" : "";
            case ACT_5:
                return (option == 1) ? "Fight the Dragon" : "";
            default:
                return "Continue";
        }
    }

    public String getTalkFailsText() {
        return "I am beyond conversation. Even my therapist ghosted me.";
    }

    public String getEnemyIntroText(EnemyType type) {
        if (type == null) return "Another encounter, another chance to prove that your courage is slightly greater than your common sense.";

        switch (type) {
            case FOREST_MAGE:
                return """
                        A forest mage steps out, raising his staff.
                        He looks kind... and dangerously unsure.
                        
                        "Fear not, child! I shall protect thee with a spell of-"
                        ... OH NO. That was the fireball one.\"""";
            case DRAMATIC_ELF:
                return """
                        An elf storms toward you, devastated.
                        
                        "You trampled my sacred moss! MY LIFE IS RUINED!"
                        "Are you... okay?"
                        "NO. NOW SUFFER WITH ME!\"""";
            default:
                return "An enemy appears!";
        }
    }

    public String getLootPickupText(ItemType itemType) {
        if (itemType == null) return "You find something... but it's unclear what it is.";

        String intro = "You defeated the enemy.\nA small glimmer catches your eye - an item!\n\n";
        switch (itemType) {
            case POTION_HP -> intro += """
                    You pick up:
                    Potion of Reasonable Hope (+20 HP)
                    "Now with 12% more placebo!\"""";
            case STRENGTHPOTION_ATK -> intro += """
                    You pick up:
                    Elixir of Excessive Confidence (+5 ATK)
                    "Side effects may include shouting dramatic attack names.\"""";
            case IRONCHARM_DEF -> intro += """
                    You pick up:
                    Iron Charm of Emotional Support (+3 DEF)
                    "It doesn’t really protect you, but it believes in you.\"""";
            case SWORD_ATK -> intro += """
                    You pick up:
                    Silver Sword of Questionable Origins (+10 ATK)
                    "Nobody knows where it came from. Nobody asked. Probably fine.\"""";
            case POTION_COURAGE -> intro += """
                    You pick up:
                    Potion of Selective Courage (+10% Escape Chance)
                    "Helps you boldly run away from your problems.\"""";

            default -> intro += "You pick up something mysterious.";
        }
        return intro;
    }

    public String getEndingText(Ending type) {
        return switch (type) {
            case GOOD -> "Against all common sense, you actually survived.\nThe dragon falls by your hands.\n\nThe Royal Knight Order welcomes you as a full-fledged knight.";
            case BAD -> "You Died.";
            case COWARD -> "You turn around.\nA warm bed, a hot stew, zero monsters... all sounds great.\n\nBut something deep inside you whispers:\n\"Coward.\"";
            case RETREAT -> "You sprint out of the castle as fast as your armor allows.\n\nSomewhere far behind you, you hear a furious voice:\n\"Seriously?! This isn't how boss fights are supposed to work! WHO programmed you?\"\n\nTitle earned: \"Knight of Swift Retreat\"";
        };
    }

    public String getBackgroundForEnding(Ending type) {
        return switch (type) {
            case COWARD -> Assets.BG_AT_HOME;
            case GOOD -> Assets.BG_ENDING;
            default -> Assets.BG_START_SCREEN;
        };
    }

    public String getBackgroundForAct(int act) {
        return switch (act) {
            case ACT_1 -> Assets.BG_CASTLE_INFRONT;
            case ACT_2 -> Assets.BG_FOREST;
            case ACT_3 -> Assets.BG_DARK_FOREST;
            case ACT_4 -> Assets.BG_CASTLE_INFRONT;
            case ACT_5 -> Assets.BG_CASTLE_INSIDE;
            default -> Assets.BG_FOREST;
        };
    }
}
