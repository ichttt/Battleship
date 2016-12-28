package ichttt.battleship.gui;

import ichttt.battleship.logic.ShipRegistry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerShipChooser implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
	    int cmd = Integer.parseInt(arg0.getActionCommand());
        int[] shipList = ShipRegistry.getShipList();
        ShipRegistry.setPlaced(cmd);
        ShipRegistry.stepPos(false);
        GuiBattleShip.processShipChoosed(shipList[cmd]);
	}
}