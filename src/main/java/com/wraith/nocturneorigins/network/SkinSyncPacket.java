package com.wraith.nocturneorigins.network;

import com.wraith.nocturneorigins.gui.SkinType;
import com.wraith.nocturneorigins.client.SkinDataStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SkinSyncPacket {

    private final UUID playerUUID;
    private final ResourceLocation skin;
    private final SkinType type;

    public SkinSyncPacket(UUID playerUUID, ResourceLocation skin, SkinType type) {
        this.playerUUID = playerUUID;
        this.skin = skin;
        this.type = type;
    }

    public static void encode(SkinSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerUUID);
        buf.writeResourceLocation(msg.skin);
        buf.writeEnum(msg.type);
    }

    public static SkinSyncPacket decode(FriendlyByteBuf buf) {
        UUID uuid = buf.readUUID();
        ResourceLocation skin = buf.readResourceLocation();
        SkinType type = buf.readEnum(SkinType.class);
        return new SkinSyncPacket(uuid, skin, type);
    }

    public static void handle(SkinSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Client-side only: store the skin for this UUID
            SkinDataStorage.setSkin(msg.playerUUID, msg.skin);
            // You could optionally store SkinType too if needed later
        });
        ctx.get().setPacketHandled(true);
    }
}
