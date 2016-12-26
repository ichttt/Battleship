package ichttt.battleship.gui;

import ichttt.battleship.Battleship;
import ichttt.battleship.logic.ShipRegistry;
import ichttt.battleship.logic.WinningCondition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;


/**
 * Created by Tobias on 16.11.2016.
 */

class WindowListenerMain extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e){
        if (JOptionPane.showConfirmDialog(null, "This will exit the game. Continue?")==0) {
            System.exit(0);
        }
    }
}

public class GuiBattleShip implements ActionListener {
    private static boolean isPlacing = true;
    private static boolean p1 = true;
    private static int previousX;
	private static int desiredLength;
	private static int currentLength = 1;

    private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static JFrame window;
    private static JDialog chooseShip;
    private static JPanel mainPanel;
    static JButton[][] fields;//Use like this: [posy][posx]
    private static Color color;
    private static JLabel[] horizontalLabels;
    private static JLabel[] verticalLabels;
    private JMenuBar menuBar;
    private JMenu menuitem1;
    private JMenuItem exit, restart, settings;

    /**
     * Creates the main User Interface
     */
    private void createElements() {
        window = new JFrame();
        window.setTitle("Battleship - Player 1");
        mainPanel = new JPanel(new GridLayout(Battleship.verticalLength+1,0, 2, 2));
        fields = new JButton[Battleship.verticalLength][Battleship.horizontalLength];
        horizontalLabels  = new JLabel[Battleship.horizontalLength];
        verticalLabels = new JLabel[Battleship.verticalLength];
        for(int i = 0; i< Battleship.verticalLength; i++) {
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                if(i==0) {
                    horizontalLabels[i2] = new JLabel(alphabet[i2] + "");
                    horizontalLabels[i2].setHorizontalAlignment(SwingConstants.CENTER);
                }
                fields[i][i2] = new JButton();
                //TODO Mark the fields as clear so it doesn't repaint every time it updates
                //RepaintManager.currentManager(fields[i][i2]).markCompletelyClean(fields[i][i2]);
                fields[i][i2].setActionCommand(i + " " + i2);
                fields[i][i2].addActionListener(this);
            }
            verticalLabels[i] = new JLabel((i+1)+" ");
            verticalLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }
        menuBar = new JMenuBar();
        menuitem1 = new JMenu("File");
        settings = new JMenuItem("Settings");
        settings.setActionCommand("settings");
        settings.addActionListener(this);
        exit = new JMenuItem("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        restart = new JMenuItem("Restart");
        restart.setActionCommand("restart");
        restart.addActionListener(this);
    }

    private void mapElements() {
        menuitem1.add(restart);
        menuitem1.add(settings);
        menuitem1.addSeparator();
        menuitem1.add(exit);
        menuBar.add(menuitem1);
        mainPanel.add(new JLabel(""));
        for(int i = 0; i< Battleship.horizontalLength; i++)
            mainPanel.add(horizontalLabels[i]);

        for(int i = 0; i< Battleship.verticalLength; i++) {
            mainPanel.add(verticalLabels[i]);
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                mainPanel.add(fields[i][i2]);
            }
        }
        window.add(mainPanel);
        window.setJMenuBar(menuBar);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(600, 600);
        window.setVisible(true);
    }

    /**
     * Creates and shows the GUI to place ships
     */
    public static void initPlacementGui() {
        GuiBattleShip gui = new GuiBattleShip();
        gui.createElements();
        gui.mapElements();
        ShipRegistry.closeRegistry();
        gui.chooseShipGui(ShipRegistry.getShipList());
    }

    /**
     * Creates and shows the battle-GUI
     */
    public static void initBattleGui() throws NullPointerException {
        int sum = 0;
        for(int i: ShipRegistry.getShipList())
            sum += i;
        WinningCondition.max = sum;
        System.out.println(sum);
        isPlacing = false;
        GuiBattleShip gui = new GuiBattleShip();
        gui.createElements();
        gui.mapElements();
        p1=true;
    }

    /**
     * Clears the text from the fields
     */
    private static void clearTextFromButtons() {
        for(int i = 0; i< Battleship.verticalLength; i++) {
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                fields[i][i2].setText("");
            }
        }
    }

    /**
     * Prints a "X" for every true in the array
     * @param playList the array
     */
    public static void loadFromBoolean(boolean[][] playList) {
    	clearTextButtons();
        for(int i = 0; i< Battleship.verticalLength; i++) {
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                if(playList[i][i2]) {
                	fields[i][i2].setText("X");
                	fields[i][i2].setEnabled(false);
                }
            }
        }
    }

    /**
     * Loads up the previous hits
     * @param isP1 If the player is player 1
     */
    private static void battleSetAlreadyTried(boolean isP1) {
        for (int i = 0; i < Battleship.verticalLength; i++) {
            for (int i2 = 0; i2 < Battleship.horizontalLength; i2++) {
                if(isP1) {
                    if (Battleship.player1hit[i][i2] != null) {
                        if(Battleship.player1hit[i][i2].equals("X"))
                            fields[i][i2].setBackground(new Color(0, 255, 0));
                        else
                            fields[i][i2].setBackground(new Color(175,0,0));
                        fields[i][i2].setText(Battleship.player1hit[i][i2]);
                        fields[i][i2].setEnabled(false);
                    }
                }
                else {
                    if (Battleship.player2hit[i][i2] != null) {
                        if(Battleship.player2hit[i][i2].equals("X"))
                            fields[i][i2].setBackground(new Color(0, 255, 0));
                        else
                            fields[i][i2].setBackground(new Color(175,0,0));
                        fields[i][i2].setText(Battleship.player2hit[i][i2]);
                        fields[i][i2].setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * Processes the ship chosen by the user
     * @param i the length of the ship
     */
	protected static void processShipChoosed(int i) {
		chooseShip.dispose();
		desiredLength = i;
	}

    /**
     * Resets all buttons in the field
     */
    private static void clearTextButtons() {
    	clearTextFromButtons();
        for(int i = 0; i< Battleship.verticalLength; i++) {
            for(int i2 = 0; i2< Battleship.horizontalLength; i2++) {
                fields[i][i2].setBackground(null);
                fields[i][i2].setEnabled(true);
            }
        }
    }

    /**
     * Resets the whole GUI
     * @param reopenRegistry if the ShipRegistry should be reset, too
     */
    public static void resetEverything(boolean reopenRegistry) {
        if(reopenRegistry)
            ShipRegistry.reopenRegistry(true);
        isPlacing = true;
        p1 = true;
        BlockStatusHandler.clearEntireBlockList();
        Battleship.player1hit = new String[Battleship.verticalLength][Battleship.horizontalLength];
        Battleship.player2hit = new String[Battleship.verticalLength][Battleship.horizontalLength];
        BlockStatusHandler.changeBlockStatus(false, false);
        clearTextButtons();
        WinningCondition.foundp2 = 0;
        WinningCondition.foundp1 = 0;
        currentLength = 1;
    }

    /**
     * Resets the whole GUI
     */
    private static void resetEverything() {
        resetEverything(true);
    }

    /**
     * Show a GUI to choose the next ship
     * @param ships All available ships
     */
    private void chooseShipGui(int ships[]) {
        boolean setSomething = false;
        color = null;
        chooseShip = new JDialog();
        chooseShip.setTitle("Please choose a ship!");
        JPanel shipPanel = new JPanel();
        JButton[] shipButtons = new JButton[ships.length];
        shipPanel.add(new JLabel("Please choose the size of the Ship!"));
        for(int i = 0;i<shipButtons.length;i++) {
        	if(!ShipRegistry.isPlaced(i)) {
	            shipButtons[i] = new JButton(ships[i]+"");
	            shipButtons[i].setActionCommand(i+"");
	            shipButtons[i].addActionListener(new ActionListenerShipChooser());
	            shipPanel.add(shipButtons[i]);
                setSomething = true;
        	}
        }
        if(!setSomething) {
            //Store to Player
            if(p1) {
                Battleship.player1 = Battleship.buttonToBooleanAdapter(fields);
                Battleship.shipRowsP1 = ShipRegistry.exportShipRowsAndWipe();
                window.setTitle("Battleship - Player 2");
                JOptionPane.showMessageDialog(null, "Player 2's turn");
                resetEverything();
                p1 = false;
                chooseShipGui(ShipRegistry.getShipList());
            }
            else {
                Battleship.player2 = Battleship.buttonToBooleanAdapter(fields);
                Battleship.shipRowsP2 = ShipRegistry.exportShipRowsAndWipe();
                JOptionPane.showMessageDialog(null, "ATTACK!");
                resetEverything();
                window.dispose();
                initBattleGui();
            }
            return;
        }
        chooseShip.add(shipPanel);
        chooseShip.setModal(true);
        chooseShip.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        chooseShip.addWindowListener(new WindowListenerMain());
        chooseShip.pack();
        chooseShip.setResizable(false);
        chooseShip.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    	String[] buttonString;
    	int posx, posy;
        switch(event.getActionCommand()) {
            case "exit":
                System.exit(0);
                break;
            case "restart":
                resetEverything();
                chooseShipGui(ShipRegistry.getShipList());
                break;
            case "settings":
                Settings settings = new Settings();
                settings.createUIComponents();
                break;
            default:
                buttonString = event.getActionCommand().split(" ");
                posx = Integer.parseInt(buttonString[1]);
                posy = Integer.parseInt(buttonString[0]);
                if(isPlacing) {
                    fields[posy][posx].setText("X");
                    fields[posy][posx].setEnabled(false);
                    BlockStatusHandler.block(posx, posy);
                    //Set color
                    if (color == null) {
                        Random rdm = new Random();
                        //We add a minimum of 40 to ensure that our color isn't too dark
                        color = new Color(rdm.nextInt(215)+40, rdm.nextInt(215)+40, rdm.nextInt(215)+40);
                    }
                    fields[posy][posx].setBackground(color);
                    ShipRegistry.setShipRow(posx, posy);
                    System.out.println("X:" + posx + "Y:" + posy );
                    //New ship
                    if (desiredLength == currentLength) {
                        currentLength = 1;
                        if(!BlockStatusHandler.shipByShip)
                            BlockStatusHandler.blockShipByShip();
                        BlockStatusHandler.clearTempBlockList();
                        BlockStatusHandler.changeBlockStatus(false, true);
                        chooseShipGui(ShipRegistry.getShipList());
                    }
                    //Continue ship
                    else {
                        if (BlockStatusHandler.getTempSize() == 0) {
                            previousX = posx;
                            BlockStatusHandler.unblockFieldsAround(posx, posy);
                        } else if (previousX == posx) {
                            BlockStatusHandler.changeBlockStatus(true, posx, true);
                            BlockStatusHandler.blockFieldsOnDirection(posx, posy, false);
                        } else {
                            BlockStatusHandler.changeBlockStatus(true, posy, false);
                            BlockStatusHandler.blockFieldsOnDirection(posx, posy, true);
                        }
                        currentLength++;
                    }
                }
                else {
                    fields[posy][posx].setEnabled(false);
                    if(p1) {
                        if(Battleship.player2[posy][posx]) {
                            fields[posy][posx].setText("X");
                            Battleship.player1hit[posy] [posx] = "X";
                            fields[posy][posx].setBackground(new Color(0, 255, 0));
                            WinningCondition.foundp1++;
                            WinningCondition.checkWin(posx, posy, p1);
                        }
                        else {
                            fields[posy][posx].setText("O");
                            Battleship.player1hit[posy] [posx] = "O";
                            p1=!p1;
                            fields[posy][posx].setBackground(new Color(175,0,0));
                            JOptionPane.showMessageDialog(null, "No Hit! Player 2's turn!");
                            window.setTitle("Battleship - Player 2");
                            clearTextButtons();
                            battleSetAlreadyTried(p1);
                        }
                    }
                    else {
                        if(Battleship.player1[posy][posx]) {
                            fields[posy][posx].setText("X");
                            Battleship.player2hit[posy][posx] = "X";
                            fields[posy][posx].setBackground(new Color(0, 255, 0));
                            WinningCondition.foundp2++;
                            WinningCondition.checkWin(posx, posy, p1);
                        }
                        else {
                            fields[posy][posx].setText("O");
                            Battleship.player2hit[posy][posx] = "O";
                            p1=!p1;
                            fields[posy][posx].setBackground(new Color(175,0,0));
                            JOptionPane.showMessageDialog(null, "No Hit! Player 1's turn!");
                            window.setTitle("Battleship - Player 1");
                            clearTextButtons();
                            battleSetAlreadyTried(p1);
                        }
                    }
                }
        }
    }
}