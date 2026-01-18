package com.minirpg.game.util;

public final class Assets {

    private Assets() {}

    private static final String resource_path = "/com/minirpg/game/";
    private static final String backgrounds_path = resource_path + "backgrounds/";
    private static final String characters_path = resource_path + "characters/";
    private static final String items_path = resource_path + "items/";
    private static final String ui_path = resource_path + "ui/";

    public static final String BG_CASTLE_INFRONT =
            backgrounds_path + "front_castle.png";

    public static final String BG_CASTLE_INSIDE =
            backgrounds_path + "castle_inside.png";

    public static final String BG_FOREST =
            backgrounds_path + "forest_no_combat.png";

    public static final String BG_DARK_FOREST =
            backgrounds_path + "dark_forest.png";

    public static final String BG_AT_HOME =
            backgrounds_path + "home.png";

    public static final String BG_START_SCREEN =
            backgrounds_path + "start_screen.png";

    public static final String BG_ENDING =
            backgrounds_path + "ending.jpg";

    public static final String UI_START_BUTTON =
            ui_path + "start_button.png";

    public static final String UI_EXIT_BUTTON =
            ui_path + "exit_button.png";

    public static final String CH_KNIGHT_GIRL =
            characters_path + "knight_girl.png";

    public static final String CH_KNIGHT =
            characters_path + "knight.png";

    public static final String CH_KNIGHT_WITH_SWORD =
            characters_path + "knight_sword.png";

    public static final String CH_KNIGHT_WITH_SHIELD =
            characters_path + "knight_shield.png";

    public static final String CH_KNIGHT_WITH_SWORD_AND_SHIELD =
            characters_path + "knight_sword_shield.png";

    public static final String CH_KNIGHT_SLAIN =
            characters_path + "knight_slain.png";

    public static final String CH_PRINCESS =
            characters_path + "princess.png";

    public static final String CH_DRAGON =
            characters_path + "dragon.png";

    public static final String CH_MAGE =
            characters_path + "mage.png";

    public static final String CH_ELF =
            characters_path + "elf.png";
}
