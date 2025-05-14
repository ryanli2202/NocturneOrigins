package com.wraith.nocturneorigins.mixin;

import com.wraith.nocturneorigins.client.SkinDataStorage;
import com.wraith.nocturneorigins.gui.SelectedSkinManager;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    public AbstractClientPlayerMixin() {
        super(null, null, 0, null); // Required to match Player constructor, not actually used
    }

    @Inject(method = "getSkinTextureLocation", at = @At("HEAD"), cancellable = true)
    private void overrideSkinTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        UUID id = this.getUUID();

        if (this.isLocalPlayer() && SelectedSkinManager.getSelectedSkin() != null) {
            cir.setReturnValue(SelectedSkinManager.getSelectedSkin());
        } else if (SkinDataStorage.hasSkin(id)) {
            cir.setReturnValue(SkinDataStorage.getSkin(id));
        }
    }
    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void overrideDisplayName(CallbackInfoReturnable<Component> cir) {
        CompoundTag tag = this.getPersistentData();
        if (tag.contains("ChosenName")) {
            cir.setReturnValue(new TextComponent(tag.getString("ChosenName")));
        }
    }
}
