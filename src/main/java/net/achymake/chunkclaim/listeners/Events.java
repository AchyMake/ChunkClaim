package net.achymake.chunkclaim.listeners;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.listeners.block.BlockBreak;
import net.achymake.chunkclaim.listeners.block.BlockPlace;
import net.achymake.chunkclaim.listeners.bucket.BucketEmpty;
import net.achymake.chunkclaim.listeners.bucket.BucketFill;
import net.achymake.chunkclaim.listeners.connection.PlayerJoin;
import net.achymake.chunkclaim.listeners.connection.PlayerQuitWithTask;
import net.achymake.chunkclaim.listeners.entity.PlayerDamageEntity;
import net.achymake.chunkclaim.listeners.entity.PlayerDamageEntityByArrow;
import net.achymake.chunkclaim.listeners.entity.PlayerDamageEntityBySnowball;
import net.achymake.chunkclaim.listeners.entity.PlayerDamageEntityBySpectralArrow;
import net.achymake.chunkclaim.listeners.interact.PlayerInteractBlock;
import net.achymake.chunkclaim.listeners.interact.PlayerInteractEntity;
import net.achymake.chunkclaim.listeners.movement.PlayerMove;

public class Events {
    public static void start(ChunkClaim plugin){
        new BlockBreak(plugin);
        new BlockPlace(plugin);
        new BucketEmpty(plugin);
        new BucketFill(plugin);
        new PlayerJoin(plugin);
        new PlayerQuitWithTask(plugin);
        new PlayerDamageEntity(plugin);
        new PlayerDamageEntityByArrow(plugin);
        new PlayerDamageEntityBySnowball(plugin);
        new PlayerDamageEntityBySpectralArrow(plugin);
        new PlayerInteractBlock(plugin);
        new PlayerInteractEntity(plugin);
        new PlayerMove(plugin);
    }
}
