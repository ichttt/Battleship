package ichttt.battleship.logic;

import ichttt.battleship.Battleship;
import ichttt.battleship.EnumHitResult;
import ichttt.battleship.gui.GuiBattleShip;
import ichttt.battleship.gui.StatusBarManager;
import ichttt.battleship.util.I18n;

import javax.swing.*;

/**
 * Created by Tobias on 16.11.2016.
 */
public class WinningCondition {
    public static int foundp1 = 0;
    public static int foundp2 = 0;
    public static int max;
    public static boolean checkShipDown = true;

    public static void checkWin(int posx, int posy, boolean isP1) {
        if (foundp1 == max && isP1) {
            JOptionPane.showMessageDialog(GuiBattleShip.window, I18n.translate("Player") + " 1 " + I18n.translate("win"));
            GuiBattleShip.loadFromBoolean(Battleship.player1);
        } else if (foundp2 == max && !isP1) {
            JOptionPane.showMessageDialog(GuiBattleShip.window, I18n.translate("Player") + " 2 " + I18n.translate("win"));
            GuiBattleShip.loadFromBoolean(Battleship.player2);
        } else if (checkShipDown) {
            if (isP1) {
                int possible = getPossibleShip(Battleship.shipRowsP2, posy, posx);
                if (checkShipDown(possible, Battleship.shipRowsP2, Battleship.player1hit)) {
                    JOptionPane.showMessageDialog(GuiBattleShip.window, I18n.translate("ShipDown"));
                    StatusBarManager.defeatedShipsP1.add(Battleship.shipRowsP2[possible]);
                    StatusBarManager.updateBattleStatusBar(true);
                }
            } else {
                int possible = getPossibleShip(Battleship.shipRowsP1, posy, posx);
                if (checkShipDown(possible, Battleship.shipRowsP1, Battleship.player2hit)) {
                    JOptionPane.showMessageDialog(GuiBattleShip.window, I18n.translate("ShipDown"));
                    StatusBarManager.defeatedShipsP2.add(Battleship.shipRowsP1[possible]);
                    StatusBarManager.updateBattleStatusBar(false);
                }
            }
        }
    }


    private static int getPossibleShip(HitTable shipRows[], int curposy, int curposx) {
        for (int i1 = 0; i1 < shipRows.length; i1++) {
            for (int i2 = 0; i2 < shipRows[0].size(); i2++) {
                if (shipRows[i1].posy[i2] == curposy && shipRows[i1].posx[i2] == curposx) {
                    return i1;
                }
            }
        }
        return -1;
    }

    private static boolean checkShipDown(int possibleShip, HitTable[] shipRows, EnumHitResult[][] playerhit) {
        if (possibleShip != -1) {
            for (int i = 0; i < shipRows[possibleShip].size(); i++) {
                if (shipRows[possibleShip].posx[i] == -1 && shipRows[possibleShip].posy[i] == -1) break;
                if (playerhit[shipRows[possibleShip].posy[i]][shipRows[possibleShip].posx[i]] == null || playerhit[shipRows[possibleShip].posy[i]][shipRows[possibleShip].posx[i]] == EnumHitResult.NO_HIT) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}