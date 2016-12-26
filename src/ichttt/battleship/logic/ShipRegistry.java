package ichttt.battleship.logic;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tobias on 16.11.2016.
 */
public class ShipRegistry {
    private static boolean isClosed = false;
    private static List<Integer> ShipList = new ArrayList<Integer>();
    private static boolean[] isPlaced;
    private static HitTable[] shipRows;//[Ship]
    private static int pos = -1;

    public static void stepPos(boolean reset) {
        if(!reset) {
            pos++;
            shipRows[pos].setAllPos(-1, -1);
        }
        else {
            pos = -1;
        }
    }

    public static int getPos() {
        return pos;
    }

    public static void setShipRow(int x, int y) throws IllegalArgumentException, IndexOutOfBoundsException {
        if(!isClosed)
            throw new IllegalStateException("Cannot setShipRow when Registry is opened!");
        shipRows[pos].setPos(x, y);
    }

    public static void registerShip(int ship) throws IllegalStateException {
        if(!isClosed)
            ShipList.add(ship);
        else
            throw new IllegalStateException("Cannot add to registerShip when Registry is closed!");
    }

    public static void registerShip(List<Integer> ships) throws IllegalStateException {
        if(!isClosed)
            ShipList.addAll(ships);
        else
            throw new IllegalStateException("Cannot add to registerShip when Registry is closed!");
    }

    public static void registerShip(int[] ships) throws IllegalStateException {
        if(!isClosed)
            for (int length : ships)
                ShipList.add(length);
        else
            throw new IllegalStateException("Cannot add to registerShip when Registry is closed!");
    }

    public static boolean removeShip(int ship) throws IllegalStateException {
        if(isClosed)
            throw new IllegalStateException("Cannot remove Ship when Registry is closed!");
        for(int i = 0; i<ShipList.size();i++) {
            if(ShipList.get(i)==ship) {
                ShipList.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void removeShipByPos(int pos) throws IllegalStateException, IndexOutOfBoundsException {
        if(isClosed)
            throw new IllegalStateException("Cannot remove Ship when Registry is closed!");
        ShipList.remove(pos);
    }

    /**
     * Returns all Ships registered until now
     * @return Ships
     */
    public static int[] getShipList() {
        int[] output = new int[ShipList.size()];
        for(int i = 0; i<ShipList.size();i++)
            output[i] = ShipList.get(i);
        return output;
    }

    public static void setPlaced(int pos) throws IllegalStateException {
        if(!isClosed)
            throw new IllegalStateException("registerShip needs to be closed!");
        isPlaced[pos] = true;
    }

    public static boolean isPlaced(int pos) throws IllegalStateException {
        if(!isClosed)
            throw new IllegalStateException("registerShip needs to be closed!");
        return isPlaced[pos];
    }

    private static int getLargestShipIgnoreStatus() {//Be careful with this one. Only use this in State transition
        int largest = 0;
        for(int i:ShipList) {
            if(i>largest)
                largest = i;
        }
        return largest;
    }

    public static int getLargestShip() {
        if(!isClosed)
            throw new IllegalStateException("registerShip needs to be closed!");
        return getLargestShipIgnoreStatus();
    }

    public static HitTable[] exportShipRowsAndWipe() {
        HitTable internal[] = shipRows;
        stepPos(true);
        shipRows = null;
        stepPos(true);
        shipRows = new HitTable[ShipList.size()];
        for(int i = 0;i<ShipList.size();i++)
            shipRows[i] = new HitTable(getLargestShipIgnoreStatus());
        return internal;
    }

    public static void closeRegistry() throws IllegalStateException {
        if(isClosed)
            throw new IllegalStateException("registerShip is already closed!");
        isPlaced = new boolean[ShipList.size()];
        shipRows = new HitTable[ShipList.size()];
        for(int i = 0;i<ShipList.size();i++)
            shipRows[i] = new HitTable(getLargestShipIgnoreStatus());
        isClosed = true;
    }

    public static void reopenRegistry(boolean reset) throws IllegalStateException {
        if(!isClosed)
            throw new IllegalStateException("registerShip is already opened!");
        isClosed = false;
        List<Integer> internal = new ArrayList<Integer>();
        internal.addAll(ShipList);
        ShipList.clear();
        shipRows = null;
        isPlaced = null;
        stepPos(true);
        if(reset) {
            registerShip(internal);
            closeRegistry();
        }
    }

    public static boolean getShipRegistryStatus() {
        return isClosed;
    }
}