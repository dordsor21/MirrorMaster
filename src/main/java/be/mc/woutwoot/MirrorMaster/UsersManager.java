package be.mc.woutwoot.MirrorMaster;

import org.bukkit.entity.Player;

import java.util.HashMap;


public class UsersManager {
    private static HashMap<Player, User> users = new HashMap<>();

    static void Set(Player player) {
        if (CheckUser(player))
            RegisterUser(player);
    }

    static User GetUser(Player player) {
        if (users.containsKey(player))
            return users.get(player);
        else {
            return RegisterUser(player);
        }
    }

    private static boolean CheckUser(Player player) {
        return users.containsKey(player);
    }


    private static User RegisterUser(Player player) {
        User user = new User(player);
        users.put(player, user);
        return user;
    }
}