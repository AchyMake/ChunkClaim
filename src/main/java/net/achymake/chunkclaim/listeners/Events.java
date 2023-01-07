package net.achymake.chunkclaim.listeners;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.listeners.block.BlockBreak;
import net.achymake.chunkclaim.listeners.block.BlockPlace;
import net.achymake.chunkclaim.listeners.bucket.BucketEmpty;
import net.achymake.chunkclaim.listeners.bucket.BucketFill;
import net.achymake.chunkclaim.listeners.entity.PlayerDamageEntity;
import net.achymake.chunkclaim.listeners.interact.PlayerInteractBlock;
import net.achymake.chunkclaim.listeners.interact.PlayerInteractEntity;

public class Events {
    public static void start(ChunkClaim plugin){
        new BlockBreak(plugin);
        new BlockPlace(plugin);
        new BucketEmpty(plugin);
        new BucketFill(plugin);
        new PlayerDamageEntity(plugin);
        new PlayerInteractBlock(plugin);
        new PlayerInteractEntity(plugin);
    }
}
