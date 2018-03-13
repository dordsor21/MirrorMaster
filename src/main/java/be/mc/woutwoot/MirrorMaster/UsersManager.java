package be.mc.woutwoot.MirrorMaster;

import org.bukkit.entity.Player;

import java.util.ArrayList;


public class UsersManager {
    static User user;
    private static ArrayList<User> users = new ArrayList<>();

    static void Set(Player player) {
        if (CheckUser(player))
            user = GetUser(player);
        else {
            RegisterUser(player);
            user = GetUser(player);
        }
    }

    private static User GetUser(Player player) {
        for (User user : users) {
            if (user.player.getName().equals(player.getName()))
                return user;
        }
        return null;
    }

    private static boolean CheckUser(Player player) {
        try {
            for (User user : users) {
                if (user.player.getName().equals(player.getName()))
                    return true;
            }
            return false;
        } catch (Exception ignored) {
        }
        return false;
    }


    private static void RegisterUser(Player player) {
        users.add(new User(player));
    }
}