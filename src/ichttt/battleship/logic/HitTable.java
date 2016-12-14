package ichttt.battleship.logic;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Tobias on 14.12.2016.
 */

public class HitTable {
    int[] posx, posy;
    int i = 0;

    /**
     * Creates a new hit Table. Only one value may be null.
     * If one value is null, the other value will initialize with an empty array with the size of the other array
     * @param posx The position on the X-Axis
     * @param posy The position on the Y-Axis
     * @throws IllegalArgumentException if both values are null, or the arrays are not the same size
     */
    public HitTable(@Nullable int posx[], @Nullable int posy[]) throws IllegalArgumentException {
        if(posx == null&&posy==null)
            throw new IllegalArgumentException("Both arguments are null!");
        if(posx.length!=posy.length)
            throw new IllegalArgumentException("The arguments are not the same size!");
        this.posx = posx;
        this.posy = posy;
    }

    public HitTable(@NotNull int shipTilesLength) {
        this.posx = new int[shipTilesLength];
        this.posy = new int[shipTilesLength];
    }

    public void setPos(@NotNull int posx, @NotNull int posy) throws IndexOutOfBoundsException {
        this.posx[i] = posx;
        this.posy[i] = posy;
        i++;
    }

    public void setPos(@NotNull int posx, @NotNull int posy, @NotNull int shipTile) {
        this.posx[shipTile] = posx;
        this.posy[shipTile] = posy;
    }

    @NotNull
    public String encodeToString() {
        String table =  "";//"X" + posx + "Y" + posy + "";
        for(int i = 0;i<posx.length;i++) {
            table += "X" + posx[i] + "Y" + posy[i] + "new";
        }
        return table;
    }

    @Nullable
    public static HitTable decodeFormString(@NotNull String input) {
        String strings[] = input.split("new");
        HitTable hitTable = new HitTable(strings.length);
        for(String s:strings) {
            if(!s.startsWith("X"))
                System.out.println("Error");
            String split[] = s.split("Y");
            if(!split[0].startsWith("X"))
                System.out.println("Error2");
            split[0] = split[0].substring(1);
            int posx = Integer.parseInt(split[0]);
            int posy = Integer.parseInt(split[1]);
            hitTable.setPos(posx, posy);
        }
        return hitTable;
    }

    public int size() {
        assert posx.length==posy.length;
        return posx.length;
    }

    public void setAllPos(int posx, int posy) {
        for(int i = 0;i<this.size();i++) {
            this.posx[i] = -1;
            this.posy[i] = -1;
        }
    }
}
