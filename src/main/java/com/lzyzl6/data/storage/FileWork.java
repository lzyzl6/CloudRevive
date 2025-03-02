package com.lzyzl6.data.storage;

import com.lzyzl6.entity.WanderingSpirit;
import net.minecraft.world.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class FileWork {
    //文件路径：/rootDir/levelName/playerUUID/ghostUUID
    public static File rootDir() {
        File rootDir = new File(System.getProperty("user.dir"),"Cloud_Revive_Data");
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return rootDir;
    }

    public static File spiltDirByString(String string, File parentDir) {
        File dir = new File(parentDir, string);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static<T extends Entity > String getLevelName(T entity)  {
        if(!entity.level().isClientSide()) {
            String levelRawName = Objects.requireNonNull(entity.level()).toString();
            int borderIndex;
            for(borderIndex = 0; borderIndex < levelRawName.length(); borderIndex++) {
                if(levelRawName.charAt(borderIndex) == '['){
                    break;
                }
            }
            return levelRawName.substring(borderIndex+1, levelRawName.length()-1);
        }
        return "";
    }

    public static void createMatchFile(WanderingSpirit wanderingSpirit, File parentDir) {
        String matchUUID = wanderingSpirit.getStringUUID();
        File matchFile = new File(parentDir,matchUUID);
        try {
            if (!matchFile.createNewFile()) {
                matchFile.mkdirs();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static<T extends Entity> File makeMatch(T entity) {
        String matchUUID = entity.getStringUUID();
        File rootDir = rootDir();
        File levelDir = new File(rootDir, getLevelName(entity));
        AtomicReference<File> targetPlayerDir = new AtomicReference<>(null);
        Arrays.stream(Objects.requireNonNull(levelDir.list())).toList().forEach(playerUUID -> {
            File playerDir = new File(levelDir, playerUUID);
           if(Arrays.asList(Objects.requireNonNull(playerDir.list())).contains(matchUUID)) {
                   targetPlayerDir.set(playerDir);
           }
        });
        return targetPlayerDir.get();
    }

    public static<T extends Entity> void deleteMatchFile(T entity) {
        String matchUUID = entity.getStringUUID();
        File rootDir = rootDir();
        File levelDir = new File(rootDir, getLevelName(entity));
        Arrays.stream(Objects.requireNonNull(levelDir.list())).toList().forEach(playerUUID -> {
            File playerDir = new File(levelDir, playerUUID);
            if(Arrays.asList(Objects.requireNonNull(playerDir.list())).contains(matchUUID)) {
                File matchFile = new File(playerDir, matchUUID);
                if(matchFile.isFile()) {
                    matchFile.delete();
                }
            }
        });
    }

    public static void initialize() {
        rootDir();
    }
}
