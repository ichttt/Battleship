package ichttt.battleship.gui;

import ichttt.battleship.logic.ShipRegistry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerShipChooser implements ActionListener {

    public static void processShipChoose(int shipPos, int[] shipList) {
        ShipRegistry.setPlaced(shipPos);
        ShipRegistry.stepPos(false);
        GuiBattleShip.desiredLength = shipList[shipPos];
        StatusBarManager.updatePlacingBar();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        processShipChoose(Integer.parseInt(arg0.getActionCommand()), ShipRegistry.getShipList());
        GuiBattleShip.chooseShip.dispose();
	}
}