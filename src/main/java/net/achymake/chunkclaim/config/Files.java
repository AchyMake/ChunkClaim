package net.achymake.chunkclaim.config;

public class Files {
    public static void start(){
        Message.setup();
    }
    public static void reload(){
        Config.reload();
        Message.reload();
        PlayerConfig.reload();
    }
}