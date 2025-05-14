package com.wraith.nocturneorigins.client;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinDataStorage {

    private static final Map<UUID, ResourceLocation> skinMap = new HashMap<>();

    public static void setSkin(UUID playerId, ResourceLocation skin) {
        skinMap.put(playerId, skin);
    }

    public static ResourceLocation getSkin(UUID playerId) {
        return skinMap.get(playerId);
    }

    public static boolean hasSkin(UUID playerId) {
        return skinMap.containsKey(playerId);
    }
}