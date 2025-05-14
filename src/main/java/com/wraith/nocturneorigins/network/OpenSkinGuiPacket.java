
package com.wraith.nocturneorigins.network;

import com.wraith.nocturneorigins.gui.SkinScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenSkinGuiPacket {

    public OpenSkinGuiPacket() {}

    public static void encode(OpenSkinGuiPacket msg, FriendlyByteBuf buf) {}

    public static OpenSkinGuiPacket decode(FriendlyByteBuf buf) {
        return new OpenSkinGuiPacket();
    }

    public static void handle(OpenSkinGuiPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new SkinScreen(null));
        });
        ctx.get().setPacketHandled(true);
    }
}