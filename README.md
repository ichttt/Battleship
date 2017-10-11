# Battleship
A Battleship Version written in Java.

**IntelliJ is recommended to compile the settings GUI code.**

If you can't compile the GUI form change the main method to look like this:


        LogManager.initFileLogging();
        i18n.initTranslationSystem();
        logger.fine("Registering exception handler");
        Thread.setDefaultUncaughtExceptionHandler(new Battleship());
        ShipRegistry.registerShip(new Ship(2),new Ship(3),new Ship(4),new Ship(5));
        GuiBattleShip.initPlacementGui();