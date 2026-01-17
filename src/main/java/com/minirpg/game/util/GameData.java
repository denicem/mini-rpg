package com.minirpg.game.util;

import com.minirpg.game.model.Player;

public class GameData {
   //Speichern hier Spielertyp (knight oder knight_girl)
   private static String selectedCharacterType;
   private static Player player;

   public static void setSelectedCharacterType(String type) {
       selectedCharacterType = type;
   }

   public static String getSelectedCharacterType() {
       return selectedCharacterType;
   }

   public static void setPlayer(Player p) {
       player = p;
   }

   public static Player getPlayer() {
       return player;
   }
}
