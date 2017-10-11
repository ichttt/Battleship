package ichttt.battleship.logic.StatusBar;

import com.sun.istack.internal.NotNull;
import ichttt.battleship.util.LogManager;

import java.util.Comparator;
import java.util.LinkedHashMap;

/**
 * Created by Tobias on 04.01.2017.
 */
public class StatusBar {
    private boolean hasChanged = false;
    private String buffer;
    private LinkedHashMap<String, StatusBarContent> map = new LinkedHashMap<>();

    public void addOrChangeContent(@NotNull StatusBarContent content) {
        if (!map.containsKey(content.identifier)) {
            map.put(content.identifier, content);
            hasChanged = true;
        } else {
            changeContent(content.identifier, content.content);
        }
    }

    private StatusBarContent getStatusBarContentByIdentifier(@NotNull String identifier) {
        return map.get(identifier);
    }

    private void changeContent(@NotNull String identifier, @NotNull String newContent) {
        StatusBarContent c = getStatusBarContentByIdentifier(identifier);
        if (!c.content.equals(newContent)) {
            map.get(identifier).content = newContent;
            hasChanged = true;
        }
    }

    public String buildString() {
        if (hasChanged) {
            LogManager.logger.finer("Rebuilding status bar cache");
            //rebuild the buffer
            StringBuilder builder = new StringBuilder();
            LinkedHashMap<String, StatusBarContent> tmp = new LinkedHashMap<>(map.size());
            map.entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().prio)).forEach(entry -> tmp.put(entry.getKey(), entry.getValue()));
            map = tmp;
            for (StatusBarContent c : map.values())
                builder.append(c.content).append(" || ");
            buffer = builder.toString();
            if (buffer.length() != 0) buffer = buffer.substring(0, buffer.length() - 4);
            hasChanged = false;
        }
        return buffer;
    }
}