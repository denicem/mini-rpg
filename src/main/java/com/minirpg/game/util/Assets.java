package com.minirpg.game.util;

public final class Assets {

    private Assets() {}

    private static final String resource_path = "/com/minirpg/game/";
    private static final String backgrounds_path = resource_path + "backgrounds/";
    private static final String characters_path = resource_path + "characters/";
    private static final String items_path = resource_path + "items/";

    public static final String BG_CASTLE_INFRONT =
            backgrounds_path + "castle_infront.png";

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
}
