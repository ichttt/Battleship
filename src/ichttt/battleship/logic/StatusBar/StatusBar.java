package ichttt.battleship.logic.StatusBar;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tobias on 04.01.2017.
 */
public class StatusBar {
    private List<StatusBarContent> content = new ArrayList<StatusBarContent>();
    private boolean hasChanged = false;
    private String buffer;
    private Map<String, Integer> map = new HashMap<String, Integer>();

    public StatusBar() {

    }

    public StatusBar(@NotNull StatusBarContent content) {
        addContent(content);
    }

    public void addContent(@NotNull StatusBarContent content) {
        if(!map.containsKey(content.identifier)) {
            map.put(content.identifier, map.size());
            this.content.add(content);
            hasChanged = true;
        }
        else {
            changeContent(content.identifier, content.content);
        }
    }

    public StatusBarContent getStatusBarContentByIdentifier(@NotNull String identifier) {
        assert content.get(map.get(identifier)).identifier.equals(identifier);
        return content.get(map.get(identifier));
    }

    public void changeContent(@NotNull String identifier,@NotNull String newContent) {
        StatusBarContent c = getStatusBarContentByIdentifier(identifier);
        if(!c.content.equals(newContent)) {
            content.get(map.get(identifier)).content = newContent;
            hasChanged= true;
        }
    }

    public void flushContent() {
        content.clear();
        hasChanged = true;
    }

    public String buildString() {
        if(hasChanged) {
            //rebuild the buffer
            buffer = "";
            //TODO sorting breaks our map...
            //content.sort((o1, o2) -> o1.prio - o2.prio);
            for (StatusBarContent c : content) {
                buffer += c.content + "||";
            }
            if (buffer.length() != 0)
                buffer = buffer.substring(0, buffer.length() - 2);
            hasChanged = false;
        }
        return buffer;
    }
}