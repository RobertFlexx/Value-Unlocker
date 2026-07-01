package com.valueunlocker.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import com.mojang.serialization.DataResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "validateStrict", at = @At("HEAD"), cancellable = true)
    private static void valueunlocker$validateAnyStackSize(ItemStack stack, CallbackInfoReturnable<DataResult<ItemStack>> cir) {
        // strict validation is where oversized stacks go to die, so we just lie and say the stack is fine.
        cir.setReturnValue(DataResult.success(stack));
    }

    @Redirect(
        method = "setDamageValue",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I")
    )
    private int valueunlocker$unclampDamageSet(int value, int min, int max) {
        // item damage is just a number, stop sanding the damn edges off it.
        return value;
    }

    @Redirect(
        method = "getDamageValue",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I")
    )
    private int valueunlocker$unclampDamageGet(int value, int min, int max) {
        return value;
    }
}
