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

    void variables(Variables variables) {
        this.variables = variables;
    }

    void mirror(Mirroring mirror) {
        this.mirror = mirror;
    }

    void mirrorBlockDestroyed(Boolean mirrorBlockDestroyed) {
        this.mirrorBlockDestroyed = mirrorBlockDestroyed;
    }

    void mirrorPoint(Block mirrorPoint) {
        this.mirrorPoint = mirrorPoint;
    }

    void mirrorPointMat(Material mirrorPointMat) {
        this.mirrorPointMat = mirrorPointMat;
    }

}