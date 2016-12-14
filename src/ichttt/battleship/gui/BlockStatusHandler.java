package ichttt.battleship.gui;

import ichttt.battleship.Battleship;

import java.util.Vector;

/**
 * Created by Tobias on 18.11.2016.
 */
public class BlockStatusHandler {

    protected static Vector<Integer> blockingX = new Vector<Integer>();
    protected static Vector<Integer> blockingY = new Vector<Integer>();

    protected static boolean checkForExpansion(int check1, int check2) {
        return check1 != -1 && check1 != Battleship.horizontalLength && check2 != Battleship.verticalLength && check2 != -1;
    }

    protected static void blockStatus(boolean status, int exclude, boolean isX) {
        for(int i = 0; i< Battleship.verticalLength; i++) {
            if(isX&&i==exclude) {
            }
            else {
                for (int i2 = 0; i2 < Battleship.horizontalLength; i2++) {
                    if (!isX && i2 == exclude) {

                    } else
                        GuiBattleShip.fields[i][i2].setEnabled(!status);
                }
            }
        }
    }

    protected static void blockStatus(boolean status, boolean excludeUsed) {
        for(int i = 0; i< Battleship.verticalLength; i++) {
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                if(GuiBattleShip.fields[i][i2].getText().equals("X")&&excludeUsed) {

                }
                else
                    GuiBattleShip.fields[i][i2].setEnabled(!status);
            }
        }
    }

    protected static void blockFieldsOnDirection(int posX, int posY, boolean checkX) {
        if(checkX) {
            if(posX+1 != Battleship.horizontalLength&&!GuiBattleShip.fields[posX+1][posY].getText().equals("X"))
                GuiBattleShip.fields[posX+1][posY].setEnabled(true);
            if(posX-1 != -1 &&!GuiBattleShip.fields[posX-1][posY].getText().equals("X"))
                GuiBattleShip.fields[posX-1][posY].setEnabled(true);
        }
        else {
            if(posY+1 != Battleship.verticalLength &&!GuiBattleShip.fields[posX][posY+1].getText().equals("X"))
                GuiBattleShip.fields[posX][posY+1].setEnabled(true);
            if(posY-1 != -1 && !GuiBattleShip.fields[posX][posY-1].getText().equals("X"))
                GuiBattleShip.fields[posX][posY-1].setEnabled(true);
        }
    }

    protected static void blockFields(int posX, int posY) throws IllegalArgumentException {
        blockingX.add(posX);
        blockingY.add(posY);
        if(blockingX.size()!= blockingY.size()) {
            throw new IllegalArgumentException("X has to be as large as Y!");
        }
        blockStatus(true, true);
        for(int i = 0; i< blockingY.size(); i++){
            if(checkForExpansion(blockingX.get(i)+1, blockingY.get(i))) {
                if (!GuiBattleShip.fields[blockingX.get(i) + 1][blockingY.get(i)].getText().equals("X"))
                    GuiBattleShip.fields[blockingX.get(i) + 1][blockingY.get(i)].setEnabled(true);
            }
            if(checkForExpansion(blockingX.get(i)-1, blockingY.get(i))) {
                if (!GuiBattleShip.fields[blockingX.get(i) - 1][blockingY.get(i)].getText().equals("X"))
                    GuiBattleShip.fields[blockingX.get(i) - 1][blockingY.get(i)].setEnabled(true);
            }
            if(checkForExpansion(blockingX.get(i), blockingY.get(i)+1)) {
                if (!GuiBattleShip.fields[blockingX.get(i)][blockingY.get(i) + 1].getText().equals("X"))
                    GuiBattleShip.fields[blockingX.get(i)][blockingY.get(i) + 1].setEnabled(true);
            }
            if(checkForExpansion(blockingX.get(i), blockingY.get(i)-1)) {
                if (!GuiBattleShip.fields[blockingX.get(i)][blockingY.get(i) - 1].getText().equals("X"))
                    GuiBattleShip.fields[blockingX.get(i)][blockingY.get(i) - 1].setEnabled(true);
            }
        }
    }

}
