package ichttt.battleship.logic.StatusBar;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Tobias on 04.01.2017.
 */
public class StatusBarContent {
    public final String identifier;
    public final int prio;
    public String content;

    public StatusBarContent(@NotNull String identifier, @Nullable String content, @NotNull int prio) throws IllegalArgumentException {
        if (prio < 0)
            throw new IllegalArgumentException("Priority must be positive");
        this.identifier = identifier;
        this.content = content;
        this.prio = prio;
    }
}
