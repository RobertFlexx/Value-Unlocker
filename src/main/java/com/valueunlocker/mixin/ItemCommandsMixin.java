package com.valueunlocker.mixin;

import net.minecraft.server.commands.ItemCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemCommands.class)
public abstract class ItemCommandsMixin {
    @ModifyConstant(method = "register", constant = @Constant(intValue = 99))
    private static int valueunlocker$unlimitedCount(int original) {
        return Integer.MAX_VALUE;
    }
}
