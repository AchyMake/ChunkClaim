package net.achymake.chunkclaim.config;

import net.achymake.chunkclaim.ChunkClaim;

public class Files {
    public static void start(ChunkClaim plugin){
        ChunkData.setup();
        Config.setup(plugin);
    }
    public static void reload(){
        ChunkData.reload();
        Config.reload();
    }
}
