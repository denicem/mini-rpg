package com.minirpg.game.util;

import com.minirpg.game.model.character.Character;
import com.minirpg.game.model.character.Player;
import com.minirpg.game.model.Stats;

import java.util.Random;

/**
 * Handles combat logic between characters in the MiniRPG game.
 * This class provides static methods to perform attacks and calculate damage.
 */
public class BattleSystem {
    private static final Random random = new Random();

    /**
     * Performs an attack from one character to another.
     * The attack may miss, deal normal damage, or deal a critical hit if the attacker is a player.
     */
    public static String performAttack(Character attacker, Character target) {
        Stats attackerStats = attacker.getStats();
        Stats targetStats = target.getStats();

        if (random.nextDouble() < 0.10) {
            return target.getName() + " skillfully dodged! No damage.";
        }
        int dmg = Math.max(1, attackerStats.getAtk() - targetStats.getDef());

        boolean isCrit = false;
        if (random.nextDouble() < 0.20 && attacker instanceof Player) {
            dmg *= 2;
            isCrit = true;
        }

        int hpAfterDmg = target.getHp() - dmg;
        if (hpAfterDmg < 0)
            hpAfterDmg = 0;
        target.setHp(hpAfterDmg);

        String msg = String.format("%s%s deals %d damage on %s.",
                (isCrit ? ">> CRITICAL HIT <<\n" : ""),
                attacker.getName(),
                dmg,
                target.getName());

        if (!target.isAlive())
            msg += String.format("%s%s was defeated.", "\n\n", target.getName());

        return msg;
    }
}
