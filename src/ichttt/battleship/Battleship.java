package ichttt.battleship;

import ichttt.battleship.gui.Settings;
import ichttt.battleship.logic.HitTable;
import ichttt.battleship.util.I18n;
import ichttt.battleship.util.LogManager;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Created by Tobias on 16.11.2016.
 */
public class Battleship implements Thread.UncaughtExceptionHandler {
    //MAX:25
    public static int horizontalLength, verticalLength;
    public static boolean[][] player1, player2;
    //X=Hit;O=No hit;null= not tested
    public static String[][] player1hit = new String[verticalLength][horizontalLength];
    public static String[][] player2hit = new String[verticalLength][horizontalLength];
    public static HitTable[] shipRowsP1, shipRowsP2;

    public static void main(String[] args) {
        LogManager.initFileLogging();
        I18n.initTranslationSystem();
        LogManager.logger.fine("Registering exception handler");
        Thread.setDefaultUncaughtExceptionHandler(new Battleship());
        Settings settings = new Settings();
        //Disable this command and uncomment the other two lines if you can't compile the Settings.form
        settings.createUIComponents();
//        ShipRegistry.registerShip(new int[]{2,3,4,5});
//        GuiBattleShip.initPlacementGui();
    }

    /**
     * Returns true, if Text equals X
     *
     * @param toconv List to convert
     * @return dest
     */
    public static boolean[][] buttonToBooleanAdapter(JButton[][] toconv) {
        boolean[][] dest = new boolean[toconv.length][toconv[0].length];
        for (int i = 0; i < toconv.length; i++) {
            for (int i2 = 0; i2 < toconv[0].length; i2++) {
                if (toconv[i][i2].getText().equals("X")) dest[i][i2] = true;
            }
        }
        return dest;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogManager.logger.severe(String.format("Caught exception from thread %s, ID %s. Exception: %s", t.getName(), t.getId(), e));
        StackTraceElement[] st = e.getStackTrace();
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement traceElement : st) {
            builder.append("at ").append(traceElement).append("\n");
        }
        LogManager.logger.severe("Stacktrace: " + builder.toString());
        JOptionPane.showMessageDialog(null, String.format(I18n.translate("UncaughtException"), t.getName(), e), I18n.translate("Error"), JOptionPane.ERROR_MESSAGE);
    }

}
