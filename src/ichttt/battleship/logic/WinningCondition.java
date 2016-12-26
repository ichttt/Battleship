package ichttt.battleship.logic;

import ichttt.battleship.Battleship;
import ichttt.battleship.gui.GuiBattleShip;

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
        if(checkShipDown) {
            if(isP1) {
                if(checkShipDown(getPossibleShip(Battleship.shipRowsP2, posy, posx), Battleship.shipRowsP2, Battleship.player1hit))
                    JOptionPane.showMessageDialog(null, "Ship down!");
            }
            else {
                if(checkShipDown(getPossibleShip(Battleship.shipRowsP1, posy, posx), Battleship.shipRowsP1, Battleship.player2hit))
                    JOptionPane.showMessageDialog(null, "Ship down!");
            }
        }
        if (foundp1 == max&&isP1) {
            JOptionPane.showMessageDialog(null, "Player 1 wins!");
            GuiBattleShip.loadFromBoolean(Battleship.player1);
        }
        else if(foundp2==max&&!isP1) {
            JOptionPane.showMessageDialog(null, "Player 2 wins!");
            GuiBattleShip.loadFromBoolean(Battleship.player2);
        }
    }


    private static int getPossibleShip(HitTable shipRows[], int curposy, int curposx) {
        for(int i1 = 0;i1<shipRows.length;i1++) {
            for(int i2 = 0;i2<shipRows[0].size();i2++) {
                if(shipRows[i1].posy[i2]==curposy&&shipRows[i1].posx[i2]==curposx) {
                    System.out.println("Found possible Ship at " + i1);
                    return i1;
                }
            }
        }
        return -1;
    }

    private static boolean checkShipDown(int possibleShip, HitTable[] shipRows, String[][] playerhit) {
        if(possibleShip !=-1) {
            for(int i = 0; i< shipRows[possibleShip].size(); i++) {
                if(shipRows[possibleShip].posx[i]==-1&& shipRows[possibleShip].posy[i]==-1)
                    break;
                if(playerhit[shipRows[possibleShip].posy[i]][shipRows[possibleShip].posx[i]]==null||
                        playerhit[shipRows[possibleShip].posy[i]][shipRows[possibleShip].posx[i]].equals("O")) {
                    System.out.println("Ship candidate invalid because of " + shipRows[possibleShip].posx[i] +" Y "+ shipRows[possibleShip].posy[i]);
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }
}