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
    private static final Logger logger = LogManager.getLogger(i18n.class.getName());
    private static ResourceBundle messages, fallback;

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
     * Configures and starts the logging and translation system
     */
    public static void initTranslationSystem() {
        Locale currentLocale;
        String userLanguage, userCountry;

        userCountry = System.getProperty("user.country");
        userLanguage = System.getProperty("user.language");

        currentLocale = new Locale(userLanguage, userCountry);
        //If this isn't found, we do not have any fallback
        try {
            fallback = ResourceBundle.getBundle("Battleship", new Locale("en", "US"));
        } catch (MissingResourceException e) {
            logger.severe("Could not load en_US. This File should be always available!");
        }
        logger.fine(String.format("Loading %s_%s", userLanguage, userCountry));
        try {
            messages = ResourceBundle.getBundle("Battleship", currentLocale);
        } catch (MissingResourceException e) {
            logger.warning("Could not load " + currentLocale);
        }
    }
}