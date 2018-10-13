package be.mc.woutwoot.MirrorMaster;

import be.mc.woutwoot.MirrorMaster.objects.User;
import org.bukkit.entity.Player;

import java.util.HashMap;


class UsersManager {
    private static HashMap<Player, User> users = new HashMap<>();

    static void Set(User user) {
        users.replace(user.player, user);
    }

    static User GetUser(Player player) {
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