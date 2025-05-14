package com.wraith.nocturneorigins.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wraith.nocturneorigins.client.RemoteSkinPreviewPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SkinEntry extends AbstractSelectionList.Entry<SkinEntry> {

    private final Minecraft minecraft = Minecraft.getInstance();
    private final SkinListWidget list;

    public final String name;
    public final ResourceLocation texture;
    public SkinType skinType;

    public SkinEntry(String name, String texturePath, SkinListWidget list, SkinType type) {
        this.name = name;
        this.texture = new ResourceLocation("nocturneorigins", "textures/skins/" + texturePath);
        this.list = list;
        this.skinType = type;
    }

    @Override
    public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
        Player player = new RemoteSkinPreviewPlayer(minecraft.level, this.texture, this.skinType);

        SkinUtils.renderPlayerPreviewAt(player, left + 16, top + 32, 20, 0, 0);

        Component nameComponent = new TextComponent("Preset " + (index + 1));
        minecraft.font.draw(poseStack, nameComponent, left + 40, top + 10, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        list.setSelected(this);
        return true;
    }

    public void toggleSkinType() {
        skinType = (skinType == SkinType.CLASSIC) ? SkinType.SLIM : SkinType.CLASSIC;
    }

    public Component getNarration() {
        return new TextComponent(name);
    }
}
