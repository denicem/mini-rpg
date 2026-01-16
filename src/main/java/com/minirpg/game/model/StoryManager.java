package com.minirpg.game.model;

public class StoryManager {
    public static final int ACT_1 = 1; //Intro + Schrei
    public static final int ACT_2 = 2; //Im Wald
    public static final int ACT_3 = 3; //Vor dem Schloss
    public static final int ACT_4 = 4; //Im Schloss
    public static final int ACT_5 = 5; //Finale/Ende

    public enum EnemyType {
        FOREST_MAGE,
        DRAMATIC_ELF
    }

    public enum ItemType {
        POTION_HP,
        ELIXIR_ATK,
        SHIELD_DEF,
        SWORD_ATK,
        POTION_COURAGE
    }

    public String[] getStoryChunks(int act) {
        switch (act) {
            case ACT_1:
                return new String[]{
                        "You are a young recruit of the Royal Knight Order.\n"
                                + "To become a full-fledged knight, you must soon complete your first official trial, "
                                + "a solo mission designed to test courage, judgment, and (ideally) zero dying.\n\n",

                        "You decided to train your swordsmanship and footwork in the nearby forest.\n\n",

                        "You walk through the woods, humming to yourself — when a distant scream cuts through the air. "
                                + "You freeze.\n\n"
                                + "\"That didn’t sound like a friendly squirrel.\"\n"
                                + "\"Aaaaaaaah\""
                };
            case ACT_2:
                return new String[]{
                        "You move toward the sound carefully.\n",
                        "The bushes to your right begin to rustle violently.\n",
                        "Before you can prepare a heroic pose, someone leaps out.\n\n"
                };
            case ACT_3:
                return new String[]{
                        "A grand castle towers above you.\n",
                        "A beautiful princess stands at the gate.\n\n"
                                + "\"Oh noooo! A scary monster is inside! I need a biiig strong knight to save me!!\"\n\n",
                        "She smiles a little too brightly.\n"
                                + "\"Absolutely! Totally traumatized! Let’s hurry before anything bad happens!\""
                };

            case ACT_4:
                return new String[]{
                        "You step into the castle.\n"
                                + "The doors slam shut behind you.\n"
                                + "Heat fills the hall.\n\n"
                                + "From the shadows, the princess steps out, her smile widening unnaturally.\n"
                                + "The air shimmers - and in a burst of flame, her form melts away,\n"
                                + "revealing a massive dragon.\n\n"
                                + "The final trial begins."
                };

            default:
                return new String[]{getStoryText(act)};
        }
    }

        public String getStoryText(int act){
            switch (act) {
                case ACT_1:
                    return "ACT 1";
                case ACT_2:
                    return "Act 2";
                case ACT_3:
                    return "Act 3";
                case ACT_4:
                    return "Act 4";
                case ACT_5:
                    return "Act 5";
                default:
                    return "";
            }
        }

    public String getButtonText(int act, int option) {
        switch (act) {

            case ACT_1:
                return (option == 1) ? "Investigate the Scream" : (option == 2) ? "Run for your life" : "";
            case ACT_2:
                return (option == 1) ? "Fight!" : (option == 2) ? "Try to talk" : (option == 3) ? "Run for your life" : "";
            case ACT_3:
                return (option == 1) ? "Follow the princess inside" : (option == 2) ? "Run for your life" : "";
            case ACT_4:
                return (option == 1) ? "Continue" : "";
            case ACT_5:
                return (option == 1) ? "Restart" : (option == 2) ? "Quit" : "";
            default:
                return "";
        }
    }

    public String getCowardText() {
        return "You turn around.\n"
                + "A warm bed, a hot stew, zero monsters... all sounds great.\n\n"
                + "But something deep inside you whispers:\n"
                + "\"Coward.\"";
    }

    public String getTalkFailsText() {
        return "I am beyond conversation. Even my therapist ghosted me.";
    }

    public String getEnemyIntroText(EnemyType type) {
        if (type == null) return "An enemy appears!";

        switch (type) {
            case FOREST_MAGE:
                return "A forest mage steps out, raising his staff.\n"
                        + "He looks kind... and dangerously unsure.\n\n"
                        + "\"Fear not, child! I shall protect thee with a spell of-\"\n"
                        + "... OH NO. That was the fireball one.\"";

            case DRAMATIC_ELF:
                return "An elf storms toward you, devastated.\n\n"
                        + "\"You trampled my sacred moss! MY LIFE IS RUINED!\"\n"
                        + "\"Are you... okay?\"\n"
                        + "\"NO. NOW SUFFER WITH ME!\"";

            default:
                return "An enemy appears!";
        }
    }

    public String getLootPickupText(ItemType itemType) {
        if (itemType == null) return "You find something... but it's unclear what it is.";

        String intro = "A small glimmer catches your eye - an item!\n\n";
        switch (itemType) {
            case POTION_HP:
                return intro + "You pick up: Potion of Reasonable Hope (+10 HP)\n"
                        + "\"Now with 12% more placebo!\"";

            case ELIXIR_ATK:
                return intro + "You pick up: Elixir of Excessive Confidence (+10 ATK)\n"
                        + "\"Side effects may include shouting dramatic attack names.\"";

            case SHIELD_DEF:
                return intro + "You pick up: Shield of Emotional Support (+10 DEF)\n"
                        + "\" It doesn’t really protect you, but it believes in you.\"";

            case SWORD_ATK:
                return intro + "You pick up: Silver Sword of Questionable Origins (+10 ATK)\\n"
                        + "\"Nobody knows where it came from. Nobody asked. Probably fine.\"";

            case POTION_COURAGE:
                return intro + "You pick up: Potion of Selective Courage (+10% Escape Chance)\n\n"
                        + "\"Helps you boldly run away from your problems.\"";


            default:
                return intro + "You pick up something mysterious.";
        }
    }

    public String getEscapeEndingText() {
        return "You sprint out of the castle as fast as your armor allows.\n\n"
                + "Somewhere far behind you, you hear a furious voice:\n"
                + "\"Seriously?!  This isn't how boss fights are supposed to work! WHO programmed you?\"\n\n"
                + "Title earned: \"Knight of Swift Retreat\"";
    }

    public String getHappyEndingText() {
        return "Against all common sense, you actually survived.\n"
                + "The dragon falls by your hands.\n\n"
                + "The Royal Knight Order welcomes you as a full-fledged knight.";
    }

    public String getBadEndingText() {
        return "You died.";
    }
}