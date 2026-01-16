package com.minirpg.game.model;

import java.util.Random;

public class BattleSystem {
    private static final Random random = new Random();

    public static String performAttack(Character attacker, Character target) {
        Stats attackerStats = attacker.getStats();
        Stats targetStats = target.getStats();

        if (random.nextDouble() < 0.10) {
            return target.getName() + "ist geschickt ausgewichen! Kein Schaden.";
        }
        int dmg = Math.max(1, attackerStats.getAtk() - targetStats.getDef());

        boolean isCrit = false;
        if (random.nextDouble() < 0.20 && attacker instanceof Player) {
            dmg *= 2;
            isCrit = true;
        }

        target.setHp(target.getHp() - dmg);

        String msg = String.format("%s%s fügt %s %d Schaden zu.",
                (isCrit ? "KRITISCHER TREFFER! " : ""),
                attacker.getName(),
                target.getName(), dmg);

        if (!target.isAlive()) {
            msg += "\n" + target.getName() + " wurde besiegt.";
        }

        return msg;
    }
}
