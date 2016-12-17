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

    public static void checkWin(int posy, int posx, boolean isP1) {
        if(checkShipDown)
    	    checkShipDown(posy, posx, isP1);
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
    
    
	public static void checkShipDown(int curposy, int curposx, boolean isP1) {
        boolean isDestroyed = true;
        int possibleShip;
        if(!isP1) {
        	possibleShip = getPossibleShip(Battleship.shipRowsP1, curposy, curposx);
	        if(possibleShip !=-1) {
	            for(int i = 0; i< Battleship.shipRowsP1[possibleShip].size(); i++) {
	            	if(Battleship.shipRowsP1[possibleShip].posx[i]==-1&& Battleship.shipRowsP1[possibleShip].posy[i]==-1)
	            		break;
	            	if(Battleship.player2hit[Battleship.shipRowsP1[possibleShip].posy[i]][Battleship.shipRowsP1[possibleShip].posx[i]]==null||
	            		Battleship.player2hit[Battleship.shipRowsP1[possibleShip].posy[i]][Battleship.shipRowsP1[possibleShip].posx[i]].equals("O")) {
						isDestroyed = false;
						System.out.println("Ship candidate invalid because of " + Battleship.shipRowsP1[possibleShip].posx[i] +" Y "+ Battleship.shipRowsP1[possibleShip].posy[i]);
						break;
	            	}
	            }
	        }
	        else {
                isDestroyed = false;
            }
        }
        else {
        	possibleShip = getPossibleShip(Battleship.shipRowsP2, curposy, curposx);
	        if(possibleShip !=-1) {
	            for(int i = 0; i< Battleship.shipRowsP2[possibleShip].size(); i++) {
	            	if(Battleship.shipRowsP2[possibleShip].posx[i]==-1&& Battleship.shipRowsP2[possibleShip].posy[i]==-1)
	            		break;
	            	if(Battleship.player1hit[Battleship.shipRowsP2[possibleShip].posy[i]][Battleship.shipRowsP2[possibleShip].posx[i]]==null||
	            		Battleship.player1hit[Battleship.shipRowsP2[possibleShip].posy[i]][Battleship.shipRowsP2[possibleShip].posx[i]].equals("O")) {
						isDestroyed = false;
						System.out.println("Ship candiate invalid because of " + Battleship.shipRowsP2[possibleShip].posx[i] +" Y "+ Battleship.shipRowsP2[possibleShip].posy[i]);
						break;
	            	}
	            }
	        }
	        else {
                isDestroyed = false;
            }
        }
        if(isDestroyed) {
        	JOptionPane.showMessageDialog(null, "Ship down!");
        }
	}
}
