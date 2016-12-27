package ichttt.battleship;

import ichttt.battleship.gui.Settings;
import ichttt.battleship.logic.HitTable;
import ichttt.battleship.util.i18n;

import javax.swing.*;

/**
 * Created by Tobias on 16.11.2016.
 */
public class Battleship implements Thread.UncaughtExceptionHandler{
    //MAX:26
    public static int horizontalLength;
    //MAX:As much as you want
    public static int verticalLength;

    public static boolean[][] player1;
    public static boolean[][] player2;
    //X=Hit;O=No hit;null= not tested
    public static String[][] player1hit = new String[verticalLength][horizontalLength];
    public static String[][] player2hit = new String[verticalLength][horizontalLength];
    public static HitTable[] shipRowsP1;
    public static HitTable[] shipRowsP2;
    public static void main(String[] args) {
        i18n.initLogging();
        Thread.setDefaultUncaughtExceptionHandler(new Battleship());
        Settings settings = new Settings();
        //Disable this command and uncomment the other two lines if you can't compile the Settings.form
        settings.createUIComponents();
//        ShipRegistry.registerShip(new int[]{2,3,4,5});
//        GuiBattleShip.initPlacementGui();
    }

    /**
     * Returns true, if Text equals X
     * @param toconv List to convert
     * @return dest
     */
    public static boolean[][] buttonToBooleanAdapter(JButton[][]toconv) {
        boolean[][] dest = new boolean[toconv.length][toconv[0].length];
        for(int i =0;i<toconv.length;i++) {
            for(int i2=0;i2<toconv[0].length;i2++) {
                if(toconv[i][i2].getText().equals("X"))
                    dest[i][i2] = true;
            }
        }
        return dest;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        System.out.println(t.getName());
        JOptionPane.showMessageDialog(null, String.format(i18n.translate("UncaughtException"), t.getName(), e), i18n.translate("Error") , JOptionPane.ERROR_MESSAGE);
    }

}
