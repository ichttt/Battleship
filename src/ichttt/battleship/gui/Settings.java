package ichttt.battleship.gui;

import ichttt.battleship.Battleship;
import ichttt.battleship.logic.ShipRegistry;
import ichttt.battleship.logic.WinningCondition;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Created by Tobias on 08.12.2016.
 */

//class WindowListenerSettings extends WindowAdapter {
//
//    @Override
//    public void windowClosing(WindowEvent e){
//        Settings settings = new Settings();
//        settings.onWindowClose((JDialog) e.getSource());
//    }
//}

public class Settings implements ActionListener, KeyListener, ChangeListener, MouseListener {
    private JFrame window;
    private JButton okButton;
//    private JButton cancelButton;
    private JSlider SizeY;
    private JTextField text;
    private JSlider SizeX;
    private JButton addToList;
    private JLabel SizeYLabel;
    private JLabel SizeXLabel;
    private JCheckBox ShipbyShipCheckBox;
    private JList ShipList;
    private JSplitPane SplitPane;
    private JScrollPane ShipListPane;
    private JCheckBox showShipDown;
    private JTextField SizeXField;
    private JTextField SizeYField;
    private JButton removeFromList;
    //    private static boolean hasChanged;
    private DefaultListModel<Integer> list = new DefaultListModel<Integer>();

    private void registerListeners() {
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);
//        cancelButton.setActionCommand("cancel");
//        cancelButton.addActionListener(this);
        SizeX.addChangeListener(this);
        SizeY.addChangeListener(this);
        SizeXField.addKeyListener(this);
        SizeYField.addKeyListener(this);
        showShipDown.addActionListener(this);
        showShipDown.setActionCommand("shipdown");
        ShipbyShipCheckBox.addActionListener(this);
        ShipbyShipCheckBox.setActionCommand("shipbyship");
        addToList.setActionCommand("add");
        addToList.addActionListener(this);
        removeFromList.setActionCommand("remove");
        removeFromList.addActionListener(this);
        ShipList.addMouseListener(this);
        text.addKeyListener(this);
    }

    public void createUIComponents() {
        boolean shipRegistryClosed = ShipRegistry.getShipRegistryStatus();
        if(shipRegistryClosed) {
            list.clear();
            int[] ships = ShipRegistry.getShipList();
            Arrays.sort(ships);
            for(int i:ships)
                list.addElement(i);
            ShipRegistry.reopenRegistry(false);
            GuiBattleShip.resetEverything(false);
            //Get rid of the main window if its valid
            if (GuiBattleShip.window != null || GuiBattleShip.window.isValid()) {
                GuiBattleShip.window.dispose();
            }
            //Load the Values
            SizeX.setValue(Battleship.horizontalLength);
            SizeY.setValue(Battleship.verticalLength);
            SizeXField.setText(Battleship.horizontalLength+"");
            SizeYField.setText(Battleship.verticalLength+"");
        }
        else {
            //default Values
            list.addElement(2);
            list.addElement(3);
            list.addElement(4);
            list.addElement(5);
        }
        registerListeners();
        ShipList.setModel(list);
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.add(SplitPane);
        window.pack();
        window.setTitle("Settings");
        window.setResizable(false);
        window.setVisible(true);
    }

//    private void saveSettings() {
//        JOptionPane.showMessageDialog(null, "Not implemented(yet)");
//    }
//
//    protected void onWindowClose(JDialog dialog) {
//        if(hasChanged) {
//            switch (JOptionPane.showConfirmDialog(null, "Möchten Sie ihre Einstellungen vor den Schließen speichern?")) {
//                case 0:
//                    saveSettings();
//                case 1:
//                    dialog.dispose();
//                    hasChanged = false;
//                    break;
//                case 2:
//                    System.out.println("case 2");
//                    break;
//            }
//        }
//        else {
//            dialog.dispose();
//        }
//    }

    void onWindowClose(JFrame window) {
        //Since the list should be sorted, the last Value should be the largest one. Let's hope :D
        if(list.size()==0) {
            JOptionPane.showMessageDialog(this.window, "The ship list must not be empty!", "Cannot continue", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Check if the largest ship can fit the size
        if(SizeX.getValue() < list.get(list.size()-1)&& SizeY.getValue() < list.get(list.size()-1)) {
            JOptionPane.showMessageDialog(this.window, "The registered ships are larger then the size of the field!", "Cannot continue", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int blockCount = 0;
        for(int i = 0;i<list.size();i++) {
            blockCount += list.get(i);
        }
        if(blockCount>SizeX.getValue()*SizeY.getValue()) {
            JOptionPane.showMessageDialog(this.window, "The registered ships do not fit on the field!", "Cannot continue", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Battleship.horizontalLength = SizeX.getValue();
        Battleship.verticalLength = SizeY.getValue();
        for(int i = 0; i<list.size();i++) {
            ShipRegistry.registerShip(list.get(i));
        }
        window.dispose();
        GuiBattleShip.initPlacementGui();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "ok":
//                saveSettings();
                onWindowClose(window);
                break;
//            case "cancel":
//                onWindowClose(window);
//                break;
            case "shipdown":
                WinningCondition.checkShipDown = !WinningCondition.checkShipDown;
                break;
            case "shipbyship":
                BlockStatusHandler.shipByShip = !BlockStatusHandler.shipByShip;
                break;
            case "add":
                try {
                    int num = Integer.parseInt(text.getText());
                    if(num<=0) {
                        text.setText("");
                        return;
                    }
                    list.addElement(num);
                    int temp[] = new int[list.size()];
                    for(int i = 0;i<list.size();i++)
                        temp[i] = list.get(i);
                    Arrays.sort(temp);
                    list.clear();
                    for (int aTemp : temp)
                        list.addElement(aTemp);
                }
                catch (NumberFormatException ignored) {}
                text.setText("");
                break;
            case "remove":
                int pos = ShipList.getSelectedIndex();
                if(pos!=-1)
                    list.remove(pos);
                break;
            default:
                throw new IllegalArgumentException("ActionCommand contains invalid String!");
        }
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param event
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if (event.getSource().equals(text)) {
                addToList.doClick();
            }
            else if (event.getSource().equals(SizeXField)) {
                try {
                    int pos = Integer.parseInt(SizeXField.getText());
                    if (pos == SizeX.getValue())
                        return;
                    SizeX.setValue(pos);
                    if (pos > 25) SizeXField.setText(25 + "");
                    if (pos < 5) SizeXField.setText(5 + "");
                }
                catch (NumberFormatException e) {
                    SizeXField.setText("");
                }
            } else if (event.getSource().equals(SizeYField)){
                try {
                    int pos = Integer.parseInt(SizeYField.getText());

                    if (pos == SizeY.getValue())
                        return;
                    SizeY.setValue(pos);
                    if (pos > 25) SizeXField.setText(25 + "");
                    if (pos < 5) SizeXField.setText(5 + "");
                }
                catch (NumberFormatException e) {
                    SizeYField.setText("");
                }
            }
            else {
                throw new IllegalArgumentException("Illegal Argument in keyPressed!");
            }
//            hasChanged = true;
        }
    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param event a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent event) {
//        hasChanged = true;
        if(event.getSource().equals(SizeX)) {
            SizeXField.setText(SizeX.getValue()+"");
        } else if(event.getSource().equals(SizeY)) {
            SizeYField.setText(SizeY.getValue()+"");
        }
        else {
            throw new IllegalArgumentException("Illegal Argument in stateChanged!");
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param event
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        JList Jlist = (JList)event.getSource();
        if (event.getClickCount() == 2) {
            // Double-click detected
            list.remove(Jlist.getSelectedIndex());
        }
    }


    //Unused Listeners
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent event) {}
}
