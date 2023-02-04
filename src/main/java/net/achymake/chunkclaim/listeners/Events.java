package net.achymake.chunkclaim.listeners;

import net.achymake.chunkclaim.ChunkClaim;
import net.achymake.chunkclaim.listeners.block.BlockBreak;
import net.achymake.chunkclaim.listeners.block.BlockPlace;
import net.achymake.chunkclaim.listeners.bucket.BucketEmpty;
import net.achymake.chunkclaim.listeners.bucket.BucketFill;
import net.achymake.chunkclaim.listeners.connection.join.PlayerJoin;
import net.achymake.chunkclaim.listeners.connection.login.PlayerLogin;
import net.achymake.chunkclaim.listeners.connection.quit.PlayerQuitWithChunkEdit;
import net.achymake.chunkclaim.listeners.entity.*;
import net.achymake.chunkclaim.listeners.interact.*;
import net.achymake.chunkclaim.listeners.movement.PlayerMove;
import net.achymake.chunkclaim.listeners.player.PlayerBedEnter;

public class Events {
    public static void start(ChunkClaim plugin){
        new BlockBreak(plugin);
        new BlockPlace(plugin);
        new BucketEmpty(plugin);
        new BucketFill(plugin);
        new PlayerJoin(plugin);
        new PlayerLogin(plugin);
        new PlayerQuitWithChunkEdit(plugin);
        new PlayerDamageEntity(plugin);
        new PlayerDamageEntityByArrow(plugin);
        new PlayerDamageEntityBySnowball(plugin);
        new PlayerDamageEntityBySpectralArrow(plugin);
        new PlayerDamageEntityByTrident(plugin);
        new Physical(plugin);
        new PlayerInteractBlock(plugin);
        new PlayerInteractEntity(plugin);
        new PlayerMove(plugin);
        new PlayerBedEnter(plugin);
    }
}
