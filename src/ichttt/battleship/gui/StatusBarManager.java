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
    private static StatusBar statusBarP1 = new StatusBar();
    private static StatusBar statusBarP2 = new StatusBar();
    public static List<HitTable> defeatedShipsP1 = new ArrayList<HitTable>();
    public static List<HitTable> defeatedShipsP2 = new ArrayList<HitTable>();

    static void flushStatusBars() {
        statusBarP1 = new StatusBar();
        statusBarP2 = new StatusBar();
    }

    private static String quietlyRemove(String toRemove, String alternativeMessage) {
        if(toRemove.length()>2)
            return toRemove.substring(0, toRemove.length()-2);
        else
            return alternativeMessage;
    }

    private static boolean searchHitTable(int toFind, List<HitTable> toSearch) {
        for(HitTable hit:toSearch) {
            if(hit.actualSize()==toFind)
                return true;
        }
        return false;
    }

    public static void updatePlacingBar(boolean isP1) {
        String ships = "";
        int shipList[] = ShipRegistry.getShipList();
        for(int i = 0; i<ShipRegistry.getShipListSize(); i++) {
            if(!ShipRegistry.isPlaced(i))
                ships += shipList[i] + ", ";
        }
        ships = quietlyRemove(ships, "Keine weiteren Schiffe vorhanden!");

        if(isP1) {
            statusBarP1.addOrChangeContent(new StatusBarContent("ships", "Verfügbare Schiffe: " + ships, 10));
            statusBarP1.addOrChangeContent(new StatusBarContent("currentShip", "Ausgewähltes Schiff: " + GuiBattleShip.desiredLength, 11));
            GuiBattleShip.bar.setText(statusBarP1.buildString());
        }
        else {
            statusBarP2.addOrChangeContent(new StatusBarContent("ships", "Verfügbare Schiffe: " + ships, 10));
            statusBarP2.addOrChangeContent(new StatusBarContent("currentShip", "Ausgewähltes Schiff: " + GuiBattleShip.desiredLength, 11));
            GuiBattleShip.bar.setText(statusBarP2.buildString());
        }
    }

    public static void updateBattleStatusBar(boolean isP1) {
        String ships = "";
        int[] shipList = ShipRegistry.getShipList();
        String nonShips = "";
        if(isP1) {
            for (HitTable hit : defeatedShipsP1) {
                ships += hit.actualSize() + ", ";
            }
            for(int ship:shipList) {
                if(!searchHitTable(ship, defeatedShipsP1))
                    nonShips +=ship+", ";
            }
        }
        else {
            for (HitTable hit : defeatedShipsP2) {
                ships += hit.actualSize() + ", ";
            }
            for(int ship:shipList) {
                if(!searchHitTable(ship, defeatedShipsP2))
                    nonShips +=ship+", ";
            }
        }
        ships = quietlyRemove(ships, "Noch kein Schiff versenkt!");
        nonShips = quietlyRemove(nonShips, "Alle Schiffe versenkt!");
        if(isP1) {
            statusBarP1.addOrChangeContent(new StatusBarContent("shipsDown", "Versenkte Schiffe: " + ships, 10));
            statusBarP1.addOrChangeContent(new StatusBarContent("shipsAvailable", "Noch nicht versenkte Schiffe: " + nonShips, 11));
            GuiBattleShip.bar.setText(statusBarP1.buildString());
        }
        else {
            statusBarP2.addOrChangeContent(new StatusBarContent("shipsDown", "Versenkte Schiffe: " + ships, 10));
            statusBarP2.addOrChangeContent(new StatusBarContent("shipsAvailable", "Noch nicht versenkte Schiffe: " + nonShips, 11));
            GuiBattleShip.bar.setText(statusBarP2.buildString());
        }
    }
}