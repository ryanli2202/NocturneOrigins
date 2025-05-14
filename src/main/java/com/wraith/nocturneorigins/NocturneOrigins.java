package com.wraith.nocturneorigins;

import com.wraith.nocturneorigins.character.BackstoryManager;
import com.wraith.nocturneorigins.client.FirstJoinHandler;
import com.wraith.nocturneorigins.commands.SkinCommand;
import com.wraith.nocturneorigins.network.NetworkHandler;
import com.wraith.nocturneorigins.registry.SkinRegistry;
import com.wraith.nocturneorigins.events.ServerEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("nocturneorigins")
public class NocturneOrigins {

    public NocturneOrigins() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(FirstJoinHandler.class);
        MinecraftForge.EVENT_BUS.register(SkinCommand.class);
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(ServerEventHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkHandler.register();
        BackstoryManager.load();
        registerDefaultSkins();
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    private void registerDefaultSkins() {
        for (int i = 1; i <= 15; i++) {
            SkinRegistry.registerSkin("character" + i + ".png");
        }
    }
}
