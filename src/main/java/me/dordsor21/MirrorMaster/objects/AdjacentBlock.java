package me.dordsor21.MirrorMaster.objects;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class AdjacentBlock {

    public BlockFace blockFace;
    public Block block;

    public AdjacentBlock(BlockFace blockFace, Block block) {
        this.blockFace = blockFace;
        this.block = block;
    }

}
