package be.mc.woutwoot.MirrorMaster;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class User {
    Player player;
    Mirroring mirror;
    Boolean mirrorBlockDestroyed;
    Block mirrorPoint;
    Material mirrorPointMat;
    Variables variables;

    User(Player player) {
        this.player = player;
        this.mirror = Mirroring.None;
        this.mirrorBlockDestroyed = false;
        variables = new Variables();
    }

    void setVariables(Variables variables) {
        this.variables = variables;
    }

}