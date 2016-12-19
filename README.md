# Battleship
A Battleship Version written in Java.

**IntelliJ is recommended to compile the settings GUI code.**

If you can't compile the GUI form change the main method to look like this:


    Thread.setDefaultUncaughtExceptionHandler(new Battleship());
     Settings settings = new Settings();
     //Disable this command and uncomment the other two lines if you can't compile the Settings.form
     //settings.createUIComponents();
     ShipRegistry.registerShip(new int[]{2,3,4,5});
     GuiBattleShip.initPlacementGui();