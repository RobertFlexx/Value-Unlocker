package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Redirect(
        method = "giveExperiencePoints",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I")
    )
    private int valueunlocker$unlimitedXp(int value, int min, int max) {
        return value;
    }
}
