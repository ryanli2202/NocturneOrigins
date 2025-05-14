package com.wraith.nocturneorigins.network;

import com.wraith.nocturneorigins.gui.SkinType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkinSelectPacket {
    public ResourceLocation skin;
    public SkinType type;

    public SkinSelectPacket(ResourceLocation skin, SkinType type) {
        this.skin = skin;
        this.type = type;
    }

    public static void encode(SkinSelectPacket msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.skin);
        buf.writeEnum(msg.type);
    }

    public static SkinSelectPacket decode(FriendlyByteBuf buf) {
        return new SkinSelectPacket(buf.readResourceLocation(), buf.readEnum(SkinType.class));
    }

    public static void handle(SkinSelectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                CompoundTag tag = player.getPersistentData();
                tag.putString("NocturneSkin", msg.skin.toString());
                tag.putString("NocturneSkinType", msg.type.name());
                // Optionally: broadcast to others if you want
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
