package ichttt.battleship.gui;

import ichttt.battleship.Battleship;

import java.util.List;
import java.util.Vector;

/**
 * Created by Tobias on 18.11.2016.
 */
public class BlockStatusHandler {

    private static List<Integer> blockingX = new Vector<Integer>();
    private static List<Integer> blockingY = new Vector<Integer>();
    protected static boolean shipByShip = true;

    public static void clearBlockList() {
        blockingX.clear();
        blockingY.clear();
    }

    public static int getSize() {
        if(blockingX.size()!= blockingY.size()) {
            throw new IllegalArgumentException("X has to be as large as Y!");
        }
        return blockingY.size();
    }

    private static boolean checkForExpansion(int posX, int posY) {
        return posX != -1 && posX != Battleship.horizontalLength && posY != Battleship.verticalLength && posY != -1;
    }

    protected static void changeBlockStatus(boolean status, int exclude, boolean isX) {
        for(int i = 0; i< Battleship.verticalLength; i++) {
            if(isX||i!=exclude) {
                for (int i2 = 0; i2 < Battleship.horizontalLength; i2++) {
                    if (!isX || i2 != exclude)
                        GuiBattleShip.fields[i][i2].setEnabled(!status);
                }
            }
        }
    }

    protected static void changeBlockStatus(boolean status, boolean excludeUsed) {
        for(int i = 0; i< Battleship.verticalLength; i++) {
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                if(!GuiBattleShip.fields[i][i2].getText().equals("X")||!excludeUsed)
                    GuiBattleShip.fields[i][i2].setEnabled(!status);
            }
        }
    }

    protected static void blockFieldsOnDirection(int posX, int posY, boolean checkX) {
        if(checkX) {
            if(posX+1 != Battleship.horizontalLength&&!GuiBattleShip.fields[posY][posX+1].getText().equals("X"))
                GuiBattleShip.fields[posY][posX+1].setEnabled(true);
            if(posX-1 != -1 &&!GuiBattleShip.fields[posY][posX-1].getText().equals("X"))
                GuiBattleShip.fields[posY][posX-1].setEnabled(true);
        }
        else {
            if(posY+1 != Battleship.verticalLength &&!GuiBattleShip.fields[posY+1][posX].getText().equals("X"))
                GuiBattleShip.fields[posY+1][posX].setEnabled(true);
            if(posY-1 != -1 && !GuiBattleShip.fields[posY-1][posX].getText().equals("X"))
                GuiBattleShip.fields[posY-1][posX].setEnabled(true);
        }
    }

    protected static void unblockFieldsAround(int posX, int posY) throws IllegalArgumentException {
        blockingX.add(posX);
        blockingY.add(posY);
        if(blockingX.size()!= blockingY.size()) {
            throw new IllegalArgumentException("X has to be as large as Y!");
        }
        changeBlockStatus(true, true);
        for(int i = 0; i< blockingY.size(); i++){
            if(checkForExpansion(blockingX.get(i), blockingY.get(i)+1)) {
                if (!GuiBattleShip.fields[blockingY.get(i) + 1][blockingX.get(i)].getText().equals("X"))
                    GuiBattleShip.fields[blockingY.get(i) + 1][blockingX.get(i)].setEnabled(true);
            }
            if(checkForExpansion(blockingX.get(i), blockingY.get(i)-1)) {
                if (!GuiBattleShip.fields[blockingY.get(i) - 1][blockingX.get(i)].getText().equals("X"))
                    GuiBattleShip.fields[blockingY.get(i) - 1][blockingX.get(i)].setEnabled(true);
            }
            if(checkForExpansion(blockingX.get(i)+1, blockingY.get(i))) {
                if (!GuiBattleShip.fields[blockingY.get(i)][blockingX.get(i) + 1].getText().equals("X"))
                    GuiBattleShip.fields[blockingY.get(i)][blockingX.get(i) + 1].setEnabled(true);
            }
            if(checkForExpansion(blockingX.get(i)-1, blockingY.get(i))) {
                if (!GuiBattleShip.fields[blockingY.get(i)][blockingX.get(i) - 1].getText().equals("X"))
                    GuiBattleShip.fields[blockingY.get(i)][blockingX.get(i) - 1].setEnabled(true);
            }
        }
    }
}