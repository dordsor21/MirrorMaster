package me.dordsor21.MirrorMaster.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class User {

    public Player player;
    public Mirrors mirror;
    public Boolean mirrorBlockDestroyed;
    public Block mirrorPoint;
    public Material mirrorPointMat;
    public Variables variables;

    public User(Player player) {
        this.player = player;
        this.mirror = Mirrors.None;
        this.mirrorBlockDestroyed = false;
        variables = new Variables();
    }

    public void variables(Variables variables) {
        this.variables = variables;
    }

    public void mirror(Mirrors mirror) {
        this.mirror = mirror;
    }

    public void mirrorBlockDestroyed(Boolean mirrorBlockDestroyed) {
        this.mirrorBlockDestroyed = mirrorBlockDestroyed;
    }

    public void mirrorPoint(Block mirrorPoint) {
        this.mirrorPoint = mirrorPoint;
    }

    public void mirrorPointMat(Material mirrorPointMat) {
        this.mirrorPointMat = mirrorPointMat;
    }

}