package ichttt.battleship.gui;

import ichttt.battleship.Battleship;

import java.util.List;
import java.util.Vector;

/**
 * Created by Tobias on 18.11.2016.
 */
public class BlockStatusHandler {

    private static List<Integer> tempBlockingX = new Vector<Integer>();
    private static List<Integer> tempBlockingY = new Vector<Integer>();
    private static List<Integer> blockingX = new Vector<Integer>();
    private static List<Integer> blockingY = new Vector<Integer>();
    static boolean shipByShip = true;

    public static void block(int posx, int posy) {
        blockingX.add(posx);
        blockingY.add(posy);
    }

    public static void clearEntireBlockList() {
        blockingX.clear();
        blockingY.clear();
        clearTempBlockList();
    }

    public static void clearTempBlockList() {
        tempBlockingX.clear();
        tempBlockingY.clear();
    }

    public static int getSize() {
        if(tempBlockingX.size()!= tempBlockingY.size()) {
            throw new IllegalArgumentException("X has to be as large as Y!");
        }
        return tempBlockingY.size();
    }

    private static boolean checkForExpansion(int posX, int posY) {
        assert blockingX.size() == blockingY.size();
        boolean isBlocked = false;
        for(int i = 0;i<blockingX.size();i++) {
            if(blockingX.get(i).equals(posX)&&blockingY.get(i).equals(posY))
                isBlocked = true;
        }
        return posX != -1 && posX != Battleship.horizontalLength && posY != Battleship.verticalLength && posY != -1 &&!isBlocked;
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
            if(checkForExpansion(posX+1, posY))
                GuiBattleShip.fields[posY][posX+1].setEnabled(true);
            if(checkForExpansion(posX-1, posY))
                GuiBattleShip.fields[posY][posX-1].setEnabled(true);
        }
        else {
            if(checkForExpansion(posX, posY+1))
                GuiBattleShip.fields[posY+1][posX].setEnabled(true);
            if(checkForExpansion(posX, posY-1))
                GuiBattleShip.fields[posY-1][posX].setEnabled(true);
        }
    }

    protected static void unblockFieldsAround(int posX, int posY) throws IllegalArgumentException {
        tempBlockingX.add(posX);
        tempBlockingY.add(posY);
        if(tempBlockingX.size()!= tempBlockingY.size()) {
            throw new IllegalArgumentException("X has to be as large as Y!");
        }
        changeBlockStatus(true, true);
        for(int i = 0; i< tempBlockingY.size(); i++){
            if(checkForExpansion(tempBlockingX.get(i), tempBlockingY.get(i)+1)) {
                    GuiBattleShip.fields[tempBlockingY.get(i) + 1][tempBlockingX.get(i)].setEnabled(true);
            }
            if(checkForExpansion(tempBlockingX.get(i), tempBlockingY.get(i)-1)) {
                    GuiBattleShip.fields[tempBlockingY.get(i) - 1][tempBlockingX.get(i)].setEnabled(true);
            }
            if(checkForExpansion(tempBlockingX.get(i)+1, tempBlockingY.get(i))) {
                    GuiBattleShip.fields[tempBlockingY.get(i)][tempBlockingX.get(i) + 1].setEnabled(true);
            }
            if(checkForExpansion(tempBlockingX.get(i)-1, tempBlockingY.get(i))) {
                    GuiBattleShip.fields[tempBlockingY.get(i)][tempBlockingX.get(i) - 1].setEnabled(true);
            }
        }
    }
}