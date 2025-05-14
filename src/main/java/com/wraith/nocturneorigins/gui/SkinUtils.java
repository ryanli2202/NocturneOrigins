package com.wraith.nocturneorigins.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SkinUtils {

    public static void renderPlayerPreview(int x, int y, int scale, float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            InventoryScreen.renderEntityInInventory(x, y, scale, x - mouseX, y - mouseY, player);
        }
    }
    public static void renderPlayerPreviewAt(Player player, int x, int y, int scale, float mouseX, float mouseY) {
        InventoryScreen.renderEntityInInventory(x, y, scale, mouseX, mouseY, player);
    }
    public static void render2DSkinIcon(PoseStack poseStack, int x, int y, ResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bindForSetup(texture);
        net.minecraft.client.gui.GuiComponent.blit(poseStack, x, y, 32, 32, 32, 32, 256, 256);
    }
}
