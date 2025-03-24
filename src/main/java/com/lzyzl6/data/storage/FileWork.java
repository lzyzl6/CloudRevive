package com.lzyzl6.data.storage;

import com.lzyzl6.block.blockentity.BirthBeaconEntity;
import com.lzyzl6.entity.WanderingSpirit;
import com.lzyzl6.registry.ModBlocks;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class FileWork {

    //文件操作
    public static File rootDir() {
        File rootDir = new File(System.getProperty("user.dir"), "Cloud_Revive_Data");
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return rootDir;
    }

    public static void warnFile() {
        File rootDir = rootDir();
        File warningFileZH = new File(rootDir, "请勿修改此文件夹下的文件！！！会导致进入世界崩溃！！！");
        File warningFileEN = new File(rootDir, "Don't modify the files under this folder!!! Worlds will crash on launch!!!");
        try {
            if (!warningFileZH.createNewFile()) {
                warningFileZH.mkdirs();
            }
            if (!warningFileEN.createNewFile()) {
                warningFileEN.mkdirs();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static File spiltDirByString(String string, File parentDir) {
        File dir = new File(parentDir, string);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static <T extends Entity> String getLevelName(T entity) {
        if (!entity.level().isClientSide) {
            String levelRawName = Objects.requireNonNull(entity.level()).toString();
            int borderIndex;
            for (borderIndex = 0; borderIndex < levelRawName.length(); borderIndex++) {
                if (levelRawName.charAt(borderIndex) == '[') {
                    break;
                }
            }
            return levelRawName.substring(borderIndex + 1, levelRawName.length() - 1);
        }
        return "FAILED_TO_GET_LEVEL_NAME";
    }

    //往生信标匹配UUID
    //文件路径：/rootDir/levelName/BeaconMatch/BlockPos/playerUUID(file)
    public static void createBlockMatch(BlockPos blockPos, BlockState blockState, Player player) {
        if (blockState.is(ModBlocks.BIRTH_BEACON) && player != null && !player.level().isClientSide) {
            File rootDir = rootDir();
            String levelName = getLevelName(player);
            File levelDir = new File(rootDir, levelName);
            File beaconMatchDir = new File(levelDir, "BeaconMatch");
            File blockPosDir = new File(beaconMatchDir, blockPos.toString());
            File playerUUID = new File(blockPosDir, player.getStringUUID());
            try {
                if (!levelDir.exists()) {
                    levelDir.mkdirs();
                }
                if (!beaconMatchDir.exists()) {
                    beaconMatchDir.mkdirs();
                }
                if (!blockPosDir.exists()) {
                    blockPosDir.mkdirs();
                }
                if (!playerUUID.createNewFile()) {
                    playerUUID.mkdirs();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void matchBlockAndFix(BirthBeaconEntity blockEntity) {
        if (blockEntity.playerUUID == null && blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
            File rootDir = rootDir();
            String levelRawName = Objects.requireNonNull(blockEntity.getLevel()).toString();
            int borderIndex;
            for (borderIndex = 0; borderIndex < levelRawName.length(); borderIndex++) {
                if (levelRawName.charAt(borderIndex) == '[') {
                    break;
                }
            }
            String levelName = levelRawName.substring(borderIndex + 1, levelRawName.length() - 1);
            File levelDir = new File(rootDir, levelName);
            File beaconMatchDir = new File(levelDir, "BeaconMatch");
            File blockPosDir = new File(beaconMatchDir, blockEntity.getBlockPos().toString());
            if (!levelDir.exists()) {
                levelDir.mkdirs();
            }
            if (!beaconMatchDir.exists()) {
                beaconMatchDir.mkdirs();
            }
            if (!blockPosDir.exists()) {
                blockPosDir.mkdirs();
            }
            File[] playerUUIDs = blockPosDir.listFiles();
            if (playerUUIDs != null) {
                Arrays.stream(playerUUIDs).toList().forEach(playerUUID -> {
                    String playerUUIDStr = playerUUID.getName();
                    Player player = blockEntity.getLevel().getPlayerByUUID(UUID.fromString(playerUUIDStr));
                    if (player != null) {
                        blockEntity.playerUUID = player.getUUID();
                    }
                    playerUUID.delete();
                });
                blockPosDir.delete();
            }
        }
    }

    //匹配UUID
    //文件路径：/rootDir/levelName/playerUUID/ghostUUID(file)
    public static void createMatchFile(WanderingSpirit wanderingSpirit, File parentDir) {
        String matchUUID = wanderingSpirit.getStringUUID();
        File matchFile = new File(parentDir, matchUUID);
        try {
            if (!matchFile.createNewFile()) {
                matchFile.mkdirs();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Entity> File makeMatch(T entity) {
        String matchUUID = entity.getStringUUID();
        File rootDir = rootDir();
        File levelDir = new File(rootDir, getLevelName(entity));
        AtomicReference<File> targetPlayerDir = new AtomicReference<>(null);
        if (levelDir.list() != null) {
            Arrays.stream(Objects.requireNonNull(levelDir.list())).toList().forEach(playerUUID -> {
                File playerDir = new File(levelDir, playerUUID);
                if (Arrays.asList(Objects.requireNonNull(playerDir.list())).contains(matchUUID)) {
                    targetPlayerDir.set(playerDir);
                }
            });
        }
        return targetPlayerDir.get();
    }

    public static <T extends Entity> void deleteMatchFile(T entity) {
        String matchUUID = entity.getStringUUID();
        File rootDir = rootDir();
        File levelDir = new File(rootDir, getLevelName(entity));
        Arrays.stream(Objects.requireNonNull(levelDir.list())).toList().forEach(playerUUID -> {
            File playerDir = new File(levelDir, playerUUID);
            if (Arrays.asList(Objects.requireNonNull(playerDir.list())).contains(matchUUID)) {
                File matchFile = new File(playerDir, matchUUID);
                if (matchFile.isFile()) {
                    matchFile.delete();
                }
            }
        });
    }

    //确认相遇状态
    //生成相遇状态文件
    //文件路径：/rootDir/Ghost_Data/ghostUUID/if_meet(file)
    public static void createMeetFile(WanderingSpirit wanderingSpirit, File parentDir) {
        String ghostUUID = wanderingSpirit.getStringUUID();
        File ghostDir = new File(parentDir, ghostUUID);
        File meetStatus = new File(ghostDir, "no");
        try {
            if (!ghostDir.exists()) {
                ghostDir.mkdirs();
            }
            if (!meetStatus.createNewFile()) {
                meetStatus.mkdirs();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Entity> boolean checkIfMeetAndFix(T entity) {
        String ghostUUID = entity.getStringUUID();
        File rootDir = rootDir();
        String levelNameGD = getLevelName(entity);
        levelNameGD += "GD";
        File levelDirGD = new File(rootDir, levelNameGD);
        File ghostDir = new File(levelDirGD, ghostUUID);
        File meetStatus = new File(ghostDir, "no");
        if (meetStatus.isFile()) {
            meetStatus.delete();
            ghostDir.delete();
            return true;
        }
        return false;
    }

    //安装模组检查
    public static boolean isAccessoriesInstalled() {
        Collection<ModContainer> modList = FabricLoader.getInstance().getAllMods();
        return modList.stream().anyMatch(mod -> mod.getMetadata().getId().equals("accessories"));
    }

    public static boolean isTrinketsInstalled() {
        Collection<ModContainer> modList = FabricLoader.getInstance().getAllMods();
        return modList.stream().anyMatch(mod -> mod.getMetadata().getId().equals("trinkets"));
    }

    public static boolean isBackpackedInstalled() {
        Collection<ModContainer> modList = FabricLoader.getInstance().getAllMods();
        return modList.stream().anyMatch(mod -> mod.getMetadata().getId().equals("backpacked"));
    }

    public static void initialize() {
        rootDir();
        warnFile();
    }
}
