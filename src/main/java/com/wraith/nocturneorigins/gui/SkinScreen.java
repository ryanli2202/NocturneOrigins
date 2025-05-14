package com.wraith.nocturneorigins.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wraith.nocturneorigins.client.RemoteSkinPreviewPlayer;
import com.wraith.nocturneorigins.registry.SkinRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class SkinScreen extends Screen {
    private float previewMouseX = 0;
    private float previewMouseY = 0;
    private SkinListWidget skinList;
    private final Screen parent;

    public SkinScreen(Screen parent) {
        super(new TextComponent("Select Your Character"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();
        this.skinList = new SkinListWidget(mc, this.width, this.height - 60, 30, this.height - 30, 36, this);

        List<ResourceLocation> skins = SkinRegistry.getRegisteredSkins();
        for (ResourceLocation skin : skins) {
            String path = skin.getPath();
            String filename = path.substring(path.lastIndexOf('/') + 1);
            String displayName = filename.replace(".png", "");

            SkinEntry entry = new SkinEntry(displayName, filename, skinList, SkinType.CLASSIC);
            skinList.addSkinEntry(entry);
        }

        this.addRenderableWidget(skinList);

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 25, 100, 20,
                new TextComponent("Confirm"), button -> {
            SkinEntry selected = skinList.getSelected();
            if (selected != null) {
                SelectedSkinManager.setSelectedSkin(selected.texture, selected.skinType);

                if (mc.player != null) {
                    SelectedSkinManager.writeToNBT(mc.player.getPersistentData());
                    com.wraith.nocturneorigins.network.NetworkHandler.CHANNEL.sendToServer(
                            new com.wraith.nocturneorigins.network.SkinSelectPacket(selected.texture, selected.skinType)
                    );
                }

                mc.setScreen(new FinalizeCharacterScreen(this));
            }
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.previewMouseX = mouseX;
        this.previewMouseY = mouseY;

        this.renderBackground(poseStack);
        this.skinList.render(poseStack, mouseX, mouseY, partialTicks);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 10, 0xFFFFFF);
        this.font.draw(poseStack, "Click a skin on the left, then press Confirm.", this.width / 2 - 120, 25, 0xAAAAAA);

        SkinEntry selected = skinList.getSelected();
        if (selected != null) {
            Player player = new RemoteSkinPreviewPlayer(this.minecraft.level, selected.texture, selected.skinType);
            SkinUtils.renderPlayerPreviewAt(
                    player,
                    this.width - 100,                  // x
                    this.height / 2 + 50,              // y
                    60,                                // scale
                    this.width - 100 - previewMouseX,  // horizontal rotation
                    this.height / 2 - previewMouseY    // vertical rotation
            );
        }

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
