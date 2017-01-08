package ichttt.battleship.logic.StatusBar;

import com.sun.istack.internal.NotNull;
import ichttt.battleship.util.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Tobias on 04.01.2017.
 */
public class StatusBar {
    private List<StatusBarContent> content = new ArrayList<StatusBarContent>();
    private boolean hasChanged = false;
    private String buffer;
    private Map<String, Integer> map = new HashMap<String, Integer>();

    private static final Logger logger = LogManager.getLogger(StringBuffer.class.getName());

    public StatusBar() {

    }

    public StatusBar(@NotNull StatusBarContent content) {
        addOrChangeContent(content);
    }

    public void addOrChangeContent(@NotNull StatusBarContent content) {
        if(!map.containsKey(content.identifier)) {
            map.put(content.identifier, map.size());
            this.content.add(content);
            hasChanged = true;
        }
        else {
            changeContent(content.identifier, content.content);
        }
    }

    private StatusBarContent getStatusBarContentByIdentifier(@NotNull String identifier) {
        assert content.get(map.get(identifier)).identifier.equals(identifier);
        return content.get(map.get(identifier));
    }

    private void changeContent(@NotNull String identifier,@NotNull String newContent) {
        StatusBarContent c = getStatusBarContentByIdentifier(identifier);
        if(!c.content.equals(newContent)) {
            content.get(map.get(identifier)).content = newContent;
            hasChanged= true;
        }
    }

    public String buildString() {
        if(hasChanged) {
            logger.finer("rebuilding the buffer");
            //rebuild the buffer
            buffer = "";
            //TODO sorting breaks our map...
            //content.sort((o1, o2) -> o1.prio - o2.prio);
            for (StatusBarContent c : content) {
                buffer += c.content + " || ";
            }
            if (buffer.length() != 0)
                buffer = buffer.substring(0, buffer.length() - 4);
            hasChanged = false;
        }
        else
            logger.finer("Was able to use buffer");
        return buffer;
    }
}