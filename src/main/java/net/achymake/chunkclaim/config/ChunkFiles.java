package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;

public class ChunkFiles {
    public static void start(ChunkClaim plugin){
        ChunkConfig.setup(plugin);
        ChunkData.setup(plugin);
    }
    public static void reload(){
        ChunkData.reload();
        ChunkConfig.reload();
    }
}
