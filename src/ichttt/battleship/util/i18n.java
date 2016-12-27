package ichttt.battleship.util;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 27.12.2016.
 */
public class i18n {
    private static final Logger logger = Logger.getLogger(i18n.class.getName());
    private static ResourceBundle messages, fallback;

    public static Logger getLogger(@NotNull String name) {
        Logger log = Logger.getLogger(name);
        log.setLevel(Level.ALL);
        return log;
    }

    public static String translate(@NotNull String string) {
        try {
            return messages.getString(string);
        }
        catch (Exception e) {
            try {
                return fallback.getString(string);
            } catch (Exception e1) {
                return string;
            }
        }
    }

    /**
     * Verwaltet das Logging-System
     */
    public static void initLogging() {
        logger.setLevel(Level.ALL);
        Locale currentLocale;
        String userLanguage, userCountry, userdir;
        Handler fileHandler;

        userdir = System.getProperty("user.home");
        userCountry = System.getProperty("user.country");
        userLanguage = System.getProperty("user.language");

        currentLocale = new Locale(userLanguage, userCountry);
        //If this isn't found, we can't continue as no fallback is defined
        try {
            fallback = ResourceBundle.getBundle("Battleship", new Locale("en", "US"));
        }
        catch (MissingResourceException e) {
            logger.severe("Could not load en_US. This File should be always available!");
        }

        try {
            messages = ResourceBundle.getBundle("Battleship", currentLocale);
        }
        catch(MissingResourceException e) {
            logger.warning("Could not load " + currentLocale);
        }

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
            logger.severe(translate("LogError"));
            e.printStackTrace();
        }
        logger.info(translate("LogStart"));
    }

}