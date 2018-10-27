package me.dordsor21.MirrorMaster.util;

import me.dordsor21.MirrorMaster.objects.User;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class UsersManager {
    private static HashMap<Player, User> users = new HashMap<>();

    static void Set(User user) {
        users.replace(user.player, user);
    }

    public static User GetUser(Player player) {
        if (users.containsKey(player))
            return users.get(player);
        else {
            return RegisterUser(player);
        }
    }

    private static User RegisterUser(Player player) {
        User user = new User(player);
        users.put(player, user);
        return user;
    }
}