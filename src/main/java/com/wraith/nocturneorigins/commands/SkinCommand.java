package com.wraith.nocturneorigins.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.wraith.nocturneorigins.network.NetworkHandler;
import com.wraith.nocturneorigins.network.OpenSkinGuiPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = "nocturneorigins")
public class SkinCommand {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("reselectskin")
                        .requires(source -> source.getEntity() instanceof ServerPlayer)
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();

                            NetworkHandler.CHANNEL.send(
                                    PacketDistributor.PLAYER.with(() -> player),
                                    new OpenSkinGuiPacket()
                            );

                            ctx.getSource().sendSuccess(
                                    new TextComponent("Skin selection menu opened."),
                                    false
                            );

                            return 1;
                        })
        );
    }
}