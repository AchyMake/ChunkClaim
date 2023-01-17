package net.achymake.chunkclaim.config;

public class Files {
    public static void start(){
        MessageConfig.setup();
    }
    public static void reload(){
        Config.reload();
        MessageConfig.reload();
    }
}
