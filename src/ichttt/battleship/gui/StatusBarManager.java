package ichttt.battleship.gui;

import ichttt.battleship.logic.HitTable;
import ichttt.battleship.logic.ShipRegistry;
import ichttt.battleship.logic.StatusBar.StatusBar;
import ichttt.battleship.logic.StatusBar.StatusBarContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 04.01.2017.
 */
public class StatusBarManager {
    public static List<HitTable> defeatedShipsP1 = new ArrayList<>();
    public static List<HitTable> defeatedShipsP2 = new ArrayList<>();
    private static StatusBar statusBarP1 = new StatusBar();
    private static StatusBar statusBarP2 = new StatusBar();

    static void flushStatusBars() {
        statusBarP1 = new StatusBar();
        statusBarP2 = new StatusBar();
    }

    private static String strip(String toRemove, String alternativeMessage) {
        if (toRemove.length() > 2)
            return toRemove.substring(0, toRemove.length() - 2);
        else
            return alternativeMessage;
    }

    private static boolean searchHitTable(int toFind, List<HitTable> toSearch) {
        for (HitTable hit : toSearch) {
            if (hit.actualSize() == toFind)
                return true;
        }
        return false;
    }

    public static void updatePlacingBar(boolean isP1) {
        int shipList[] = ShipRegistry.getShipList();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ShipRegistry.getShipListSize(); i++) {
            if (!ShipRegistry.isPlaced(i))
                builder.append(shipList[i]).append(", ");
        }
        String ships = strip(builder.toString(), "Keine weiteren Schiffe vorhanden!");

        if (isP1) {
            statusBarP1.addOrChangeContent(new StatusBarContent("ships", "Verfügbare Schiffe: " + ships, 10));
            statusBarP1.addOrChangeContent(new StatusBarContent("currentShip", "Ausgewähltes Schiff: " + GuiBattleShip.desiredLength, 11));
            GuiBattleShip.bar.setText(statusBarP1.buildString());
        } else {
            statusBarP2.addOrChangeContent(new StatusBarContent("ships", "Verfügbare Schiffe: " + ships, 10));
            statusBarP2.addOrChangeContent(new StatusBarContent("currentShip", "Ausgewähltes Schiff: " + GuiBattleShip.desiredLength, 11));
            GuiBattleShip.bar.setText(statusBarP2.buildString());
        }
    }

    public static void updateBattleStatusBar(boolean isP1) {
        StringBuilder shipsBuilder = new StringBuilder();
        int[] shipList = ShipRegistry.getShipList();
        StringBuilder nonShipsBuilder = new StringBuilder();
        if (isP1) {
            for (HitTable hit : defeatedShipsP1) {
                shipsBuilder.append(hit.actualSize()).append(", ");
            }
            for (int ship : shipList) {
                if (!searchHitTable(ship, defeatedShipsP1))
                    nonShipsBuilder.append(ship).append(", ");
            }
        } else {
            for (HitTable hit : defeatedShipsP2) {
                shipsBuilder.append(hit.actualSize()).append(", ");
            }
            for (int ship : shipList) {
                if (!searchHitTable(ship, defeatedShipsP2))
                    nonShipsBuilder.append(ship).append(", ");
            }
        }
        String ships = strip(shipsBuilder.toString(), "Noch kein Schiff versenkt!");
        String nonShips = strip(nonShipsBuilder.toString(), "Alle Schiffe versenkt!");
        if (isP1) {
            statusBarP1.addOrChangeContent(new StatusBarContent("shipsDown", "Versenkte Schiffe: " + ships, 10));
            statusBarP1.addOrChangeContent(new StatusBarContent("shipsAvailable", "Noch nicht versenkte Schiffe: " + nonShips, 11));
            GuiBattleShip.bar.setText(statusBarP1.buildString());
        } else {
            statusBarP2.addOrChangeContent(new StatusBarContent("shipsDown", "Versenkte Schiffe: " + ships, 10));
            statusBarP2.addOrChangeContent(new StatusBarContent("shipsAvailable", "Noch nicht versenkte Schiffe: " + nonShips, 11));
            GuiBattleShip.bar.setText(statusBarP2.buildString());
        }
    }
}