package ichttt.battleship.util;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 03.01.2017.
 */
public class LogManager {
    private static Logger logger = getLogger(LogManager.class.getName());

    public static Logger getLogger(@NotNull String name) {
        Logger log = Logger.getLogger(name);
        log.setLevel(Level.ALL);
        return log;
    }

    public static void initFileLogging() {
        Handler fileHandler;
        String userdir = System.getProperty("user.home");

        try {
            if(System.getProperty("os.name").startsWith("Windows")) {
                new File(userdir + "/AppData/Local/Battleship").mkdirs();
                fileHandler = new FileHandler(userdir + "/AppData/Local/Battleship/LogBattleship.xml");
            }
            else {
                new File(userdir + "Battleship").mkdirs();
                fileHandler = new FileHandler(userdir+ "/Battleship/LogBattleship.xml");
            }
            //Add a global FileHandler
            logger.getParent().addHandler(fileHandler);
        }
        catch (IOException e) {
            logger.severe("An error occurred while creating the log file!");
            e.printStackTrace();
        }
        logger.info("Logger started.");
    }
}
