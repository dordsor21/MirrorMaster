package be.mc.woutwoot.MirrorMaster;

import java.util.ArrayList;
import org.bukkit.entity.Player;


public class UsersManager {
	static User user;
	static ArrayList<User> users = new ArrayList<User>();

	static void Set(Player player) {
		if (CheckUser(player))
			user = GetUser(player);
		else {
			RegisterUser(player);
			user = GetUser(player);
		}
	}

	static User GetUser(Player player) {
		for (User user : users) {
			if (user.player.getName() == player.getName())
				return user;
		}
		return null;
	}

	static boolean CheckUser(Player player) {
		try {
			for (User user : users) {
				if (user.player.getName() == player.getName())
					return true;
			}
			return false;
		} catch (Exception e) {}
		return false;
	}


	static void RegisterUser(Player player) {
		users.add(new User(player));
	}
}