package net.achymake.chunkclaim.listeners;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.listeners.block.ChunkBlockBreak;
import net.achymake.chunkclaim.listeners.block.ChunkBlockPlace;
import net.achymake.chunkclaim.listeners.bucket.ChunkBucketEmpty;
import net.achymake.chunkclaim.listeners.bucket.ChunkBucketFill;
import net.achymake.chunkclaim.listeners.entity.ChunkPlayerDamageEntity;
import net.achymake.chunkclaim.listeners.interact.ChunkPlayerInteractBlock;
import net.achymake.chunkclaim.listeners.interact.ChunkPlayerInteractEntity;

public class ChunkEvents {
    public static void start(ChunkClaim plugin){
        new ChunkBlockBreak(plugin);
        new ChunkBlockPlace(plugin);
        new ChunkBucketEmpty(plugin);
        new ChunkBucketFill(plugin);
        new ChunkPlayerDamageEntity(plugin);
        new ChunkPlayerInteractBlock(plugin);
        new ChunkPlayerInteractEntity(plugin);
    }
}
