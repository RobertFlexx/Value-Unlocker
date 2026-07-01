package com.valueunlocker.mixin;

import java.util.Collection;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.core.Holder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantCommand.class)
public abstract class EnchantCommandMixin {
    @Shadow @Final private static DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY;
    @Shadow @Final private static DynamicCommandExceptionType ERROR_NO_ITEM;
    @Shadow @Final private static SimpleCommandExceptionType ERROR_NOTHING_HAPPENED;

    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    private static void valueunlocker$enchantAnyItem(
        CommandSourceStack source,
        Collection<? extends Entity> targets,
        Holder<Enchantment> enchantment,
        int level,
        CallbackInfoReturnable<Integer> cir
    ) throws CommandSyntaxException {
        // vanilla and fabric both poke this command, so screw it, we run the whole damn thing ourselves.
        int changed = 0;

        for (Entity entity : targets) {
            if (!(entity instanceof LivingEntity livingEntity)) {
                if (targets.size() == 1) {
                    throw ERROR_NOT_LIVING_ENTITY.create(entity.getName().getString());
                }
                continue;
            }

            ItemStack stack = livingEntity.getMainHandItem();
            if (stack.isEmpty()) {
                if (targets.size() == 1) {
                    throw ERROR_NO_ITEM.create(livingEntity.getName().getString());
                }
                continue;
            }

            // no "wrong item" or "incompatible" whining here. if it has a hand item, it gets the stupid enchant.
            stack.enchant(enchantment, level);
            changed++;
        }

        if (changed == 0) {
            throw ERROR_NOTHING_HAPPENED.create();
        }

        if (targets.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.enchant.success.single", Enchantment.getFullname(enchantment, level), targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.enchant.success.multiple", Enchantment.getFullname(enchantment, level), targets.size()), true);
        }

        cir.setReturnValue(changed);
    }
}
