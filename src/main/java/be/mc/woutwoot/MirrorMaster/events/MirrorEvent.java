package be.mc.woutwoot.MirrorMaster.events;

import be.mc.woutwoot.MirrorMaster.objects.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MirrorEvent extends Event implements Cancellable {

    private final HandlerList handlers = new HandlerList();
    private User user;
    private boolean cancelled;

    public MirrorEvent(User user) {
        this.user = user;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
