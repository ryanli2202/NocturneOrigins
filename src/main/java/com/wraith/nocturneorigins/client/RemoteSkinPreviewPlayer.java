package com.wraith.nocturneorigins.client;

import com.wraith.nocturneorigins.gui.SkinType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class RemoteSkinPreviewPlayer extends AbstractClientPlayer {

    private final ResourceLocation customSkin;

    public RemoteSkinPreviewPlayer(Level level, ResourceLocation skin, SkinType type) {
        super((ClientLevel) level, new GameProfile(UUID.randomUUID(), "Preview"));
        this.customSkin = skin;
    }

    @Override
    public ResourceLocation getSkinTextureLocation() {
        return customSkin;
    }

    @Override
    public String getModelName() {
        return SkinType.SLIM.equals(SkinType.CLASSIC) ? "default" : "slim";
    }
}