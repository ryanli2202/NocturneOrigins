package com.wraith.nocturneorigins.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class SelectedSkinManager {

    private static ResourceLocation selectedSkin;
    private static SkinType skinType;

    private static final String TAG_SKIN = "NocturneSkin";
    private static final String TAG_TYPE = "NocturneSkinType";

    public static void setSelectedSkin(ResourceLocation skin, SkinType type) {
        selectedSkin = skin;
        skinType = type;
    }

    public static ResourceLocation getSelectedSkin() {
        return selectedSkin;
    }

    public static SkinType getSkinType() {
        return skinType;
    }

    public static void writeToNBT(CompoundTag tag) {
        if (selectedSkin != null) {
            tag.putString(TAG_SKIN, selectedSkin.toString());
            tag.putString(TAG_TYPE, skinType.name());
        }
    }

    public static void readFromNBT(CompoundTag tag) {
        if (tag.contains(TAG_SKIN)) {
            selectedSkin = new ResourceLocation(tag.getString(TAG_SKIN));
            skinType = SkinType.valueOf(tag.getString(TAG_TYPE));
        }
    }

    public static boolean hasSkin(CompoundTag tag) {
        return tag.contains(TAG_SKIN);
    }
}
