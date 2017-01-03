package ichttt.battleship.gui;

import ichttt.battleship.Battleship;
import ichttt.battleship.util.LogManager;
import ichttt.battleship.util.i18n;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by Tobias on 18.11.2016.
 */
public class BlockStatusHandler {

    private static List<Integer> tempBlockingX = new Vector<Integer>();
    private static List<Integer> tempBlockingY = new Vector<Integer>();
    private static List<Integer> blockingX = new Vector<Integer>();
    private static List<Integer> blockingY = new Vector<Integer>();
    static boolean shipByShip = true;
    private static Logger logger = LogManager.getLogger(BlockStatusHandler.class.getName());

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

    public static int getTempSize() {
        if(tempBlockingX.size()!= tempBlockingY.size()) {
            throw new IllegalArgumentException("X has to be as large as Y!");
        }
        return tempBlockingY.size();
    }

    private static boolean checkIfBlocked(int posX, int posY) {
        assert blockingX.size() == blockingY.size();
        for(int i = 0;i<blockingX.size();i++) {
            if(blockingX.get(i).equals(posX)&&blockingY.get(i).equals(posY))
                return false;
        }
        return true;
    }

    private static boolean checkForExpansion(int posX, int posY) {
        return posX != -1 && posX != Battleship.horizontalLength && posY != Battleship.verticalLength && posY != -1;
    }

    private static boolean checkBoth(int posX, int posY) {
        return checkForExpansion(posX, posY)&&checkIfBlocked(posX, posY);
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
                if(checkIfBlocked(i2, i)||!excludeUsed)
                    GuiBattleShip.fields[i][i2].setEnabled(!status);
            }
        }
    }

    protected static void blockFieldsOnDirection(int posX, int posY, boolean checkX) {
        if(checkX) {
            if(checkBoth(posX+1, posY))
                GuiBattleShip.fields[posY][posX+1].setEnabled(true);
            if(checkBoth(posX-1, posY))
                GuiBattleShip.fields[posY][posX-1].setEnabled(true);
        }
        else {
            if(checkBoth(posX, posY+1))
                GuiBattleShip.fields[posY+1][posX].setEnabled(true);
            if(checkBoth(posX, posY-1))
                GuiBattleShip.fields[posY-1][posX].setEnabled(true);
        }
    }

    protected static void blockShipByShip() {
        assert !shipByShip;
        int pos = blockingX.size();
        for(int i = 0;i<=pos;i++) {
            if(GuiBattleShip.fields[blockingY.get(i)][blockingX.get(i)].getText().equals("X")) {
                if (checkBoth(blockingX.get(i), blockingY.get(i) + 1)) {
                    blockingX.add(blockingX.get(i));
                    blockingY.add(blockingY.get(i) + 1);
                }
                if (checkBoth(blockingX.get(i), blockingY.get(i) - 1)) {
                    blockingX.add(blockingX.get(i));
                    blockingY.add(blockingY.get(i) - 1);
                }
                if (checkBoth(blockingX.get(i) + 1, blockingY.get(i))) {
                    blockingX.add(blockingX.get(i) + 1);
                    blockingY.add(blockingY.get(i));
                }
                if (checkBoth(blockingX.get(i) - 1, blockingY.get(i))) {
                    blockingX.add(blockingX.get(i) - 1);
                    blockingY.add(blockingY.get(i));
                }
            }
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
            if(checkBoth(tempBlockingX.get(i), tempBlockingY.get(i)+1)) {
                GuiBattleShip.fields[tempBlockingY.get(i) + 1][tempBlockingX.get(i)].setEnabled(true);
            }
            if(checkBoth(tempBlockingX.get(i), tempBlockingY.get(i)-1)) {
                GuiBattleShip.fields[tempBlockingY.get(i) - 1][tempBlockingX.get(i)].setEnabled(true);
            }
            if(checkBoth(tempBlockingX.get(i)+1, tempBlockingY.get(i))) {
                GuiBattleShip.fields[tempBlockingY.get(i)][tempBlockingX.get(i) + 1].setEnabled(true);
            }
            if(checkBoth(tempBlockingX.get(i)-1, tempBlockingY.get(i))) {
                GuiBattleShip.fields[tempBlockingY.get(i)][tempBlockingX.get(i) - 1].setEnabled(true);
            }
        }
    }
}