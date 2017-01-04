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
    static StatusBar statusBar;
    public static List<HitTable> defeatedShipsP1 = new ArrayList<HitTable>();
    public static List<HitTable> defeatedShipsP2 = new ArrayList<HitTable>();

    private static String quietlyRemove(String toRemove, int size, String alternativeMessage) {
        if(toRemove.length()>size)
            return toRemove.substring(0, toRemove.length()-size);
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

    private static void updateShipList() {
        String ships = "";
        int shipList[] = ShipRegistry.getShipList();
        for(int i = 0; i<ShipRegistry.getShipListSize(); i++) {
            if(!ShipRegistry.isPlaced(i))
                ships += shipList[i] + ", ";
        }
        ships = quietlyRemove(ships, 2, "Keine weiteren Schiffe vorhanden!");
        statusBar.addContent(new StatusBarContent("ships", "Verfügbare Schiffe: " + ships, 10));
    }

    static void updatePlacingBar() {
        updateShipList();
        statusBar.addContent(new StatusBarContent("currentShip", "Ausgewähltes Schiff: " + GuiBattleShip.desiredLength, 11));
        GuiBattleShip.bar.setText(statusBar.buildString());
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
        ships = quietlyRemove(ships, 2, "Noch kein Schiff versenkt!");
        nonShips = quietlyRemove(nonShips, 2, "Alle Schiffe versenkt!");
        statusBar.addContent(new StatusBarContent("shipsDown", "Versenkte Schiffe: " + ships, 10));
        statusBar.addContent(new StatusBarContent("shipsAvailable", "Noch nicht versenkte Schiffe: " + nonShips, 11));
        GuiBattleShip.bar.setText(statusBar.buildString());
    }
}