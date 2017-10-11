package ichttt.battleship.logic;

import com.sun.istack.internal.NotNull;

/**
 * Created by Tobias on 14.12.2016.
 */

@SuppressWarnings("WeakerAccess")
public class HitTable {
    public int[] posx, posy;
    private int i = 0;
    private int cacheActualSize = -1;

    public HitTable(@NotNull int shipTilesLength) {
        this.posx = new int[shipTilesLength];
        this.posy = new int[shipTilesLength];
    }

    /**
     * Sets the next values in the arrays and automatically forwards to the next pos
     *
     * @param posx The x value
     * @param posy The y value
     * @throws IndexOutOfBoundsException If there is no more space available for the parameters
     */
    public void setPos(@NotNull int posx, @NotNull int posy) throws IndexOutOfBoundsException {
        this.posx[i] = posx;
        this.posy[i] = posy;
        i++;
    }

    /**
     * Returns the size of the arrays in this field.
     * This is may be NOT the actual size of the content
     *
     * @return The size
     */
    public int size() {
        return posx.length;
    }

    /**
     * Returns the size of the ship
     * This method is not threadsafe
     *
     * @return The size
     */
    public int actualSize() {
        if (cacheActualSize == -1) {
            cacheActualSize = 0;
            for (int i : posx) {
                if (i != -1)
                    cacheActualSize++;
            }
        }
        return cacheActualSize;
    }

    /**
     * Sets all positions of an array to a given value
     *
     * @param posx The x value
     * @param posy The y value
     */
    public void setAllPos(int posx, int posy) {
        for (int i = 0; i < this.size(); i++) {
            this.posx[i] = posx;
            this.posy[i] = posy;
        }
    }
}