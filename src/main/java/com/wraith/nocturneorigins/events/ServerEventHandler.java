package com.wraith.nocturneorigins.events;

import com.wraith.nocturneorigins.gui.SkinType;
import com.wraith.nocturneorigins.network.NetworkHandler;
import com.wraith.nocturneorigins.network.SkinSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = "nocturneorigins")
public class ServerEventHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        CompoundTag tag = player.getPersistentData();

        if (tag.contains("NocturneSkin") && tag.contains("NocturneSkinType")) {
            ResourceLocation skin = new ResourceLocation(tag.getString("NocturneSkin"));
            SkinType type = SkinType.valueOf(tag.getString("NocturneSkinType"));
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.ALL.noArg(),
                    new SkinSyncPacket(player.getUUID(), skin, type)
            );
        }
    }
}
