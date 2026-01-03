package com.strategicgame.core;

import com.strategicgame.player.Player;
import com.strategicgame.units.Unit;
import com.strategicgame.combat.CombatResolver;
import java.util.List;
import java.util.Random;

/**
 * Very simple AI controller plays as Player 2 (mode solo contre la machine).
 * The AI:
 *  - finds the human player,
 *  - if both have units, attacks a random human unit,
 *  - otherwise just ends its turn (can be developped later)
 */
public class AiController {

    private final Random random = new Random();

    public void playTurn(Player aiPlayer, GameManager gameManager) {
        System.out.println("\n=== AI TURN (" + aiPlayer.getName() + ") ===");

        // Find human player 
        Player human = gameManager.getPlayers()
                .stream()
                .filter(p -> p != aiPlayer)
                .findFirst()
                .orElse(null);

        if (human == null) {
            System.out.println("AI: no human player found.");
            aiPlayer.endTurn();
            return;
        }

        List<Unit> aiUnits = aiPlayer.getUnits();
        List<Unit> humanUnits = human.getUnits();

        // If no combat possible, just end turn
        if (aiUnits.isEmpty() || humanUnits.isEmpty()) {
            System.out.println("AI: no combat possible, ending turn.");
            aiPlayer.endTurn();
            return;
        }

        // Choose random attacker from AI units
        Unit attacker = aiUnits.get(random.nextInt(aiUnits.size()));

        // Choose random defender from human units
        Unit defender = humanUnits.get(random.nextInt(humanUnits.size()));

        CombatResolver resolver = new CombatResolver();
        boolean defenderKilled = resolver.resolveCombat(attacker, defender);

        System.out.println("AI: " + attacker.getName()
                + " attacked your unit " + defender.getName()
                + " (HP now: " + defender.getHealth() + ")");

        if (defenderKilled) {
            System.out.println("AI: your unit was defeated!");
            human.removeUnit(defender);
        }

        aiPlayer.endTurn();
    }
}
