package com.wraith.nocturneorigins.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SkinRegistry {

    private static final List<ResourceLocation> SKIN_RESOURCES = new ArrayList<>();

    public static void registerSkin(String name) {
        ResourceLocation location = new ResourceLocation("nocturneorigins", "textures/skins/" + name);
        SKIN_RESOURCES.add(location);
    }

    public static List<ResourceLocation> getRegisteredSkins() {
        return SKIN_RESOURCES;
    }

    public static void clear() {
        SKIN_RESOURCES.clear();
    }
}