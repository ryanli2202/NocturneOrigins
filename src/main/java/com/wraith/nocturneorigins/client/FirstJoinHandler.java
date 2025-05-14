package com.wraith.nocturneorigins.client;

import com.wraith.nocturneorigins.gui.SelectedSkinManager;
import com.wraith.nocturneorigins.gui.SkinScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nocturneorigins", value = Dist.CLIENT)
public class FirstJoinHandler {

    private static boolean checked = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (!checked && mc.player != null && mc.level != null && mc.screen == null) {
            checked = true;

            CompoundTag tag = mc.player.getPersistentData();
            SelectedSkinManager.readFromNBT(tag);

            if (!SelectedSkinManager.hasSkin(tag)) {
                mc.setScreen(new SkinScreen(null));
            }
        }
    }
}
