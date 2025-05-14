package com.wraith.nocturneorigins.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("nocturneorigins", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        CHANNEL.registerMessage(
                nextId(),
                SkinSelectPacket.class,
                SkinSelectPacket::encode,
                SkinSelectPacket::decode,
                SkinSelectPacket::handle
        );
        CHANNEL.registerMessage(
                nextId(),
                SkinSyncPacket.class,
                SkinSyncPacket::encode,
                SkinSyncPacket::decode,
                SkinSyncPacket::handle
        );
        CHANNEL.registerMessage(
                nextId(),
                OpenSkinGuiPacket.class,
                OpenSkinGuiPacket::encode,
                OpenSkinGuiPacket::decode,
                OpenSkinGuiPacket::handle
        );
    }
}
