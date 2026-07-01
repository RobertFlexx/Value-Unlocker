package com.valueunlocker.mixin;

import net.minecraft.server.players.PlayerList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Inject(method = "respawn", at = @At("TAIL"))
    private void valueunlocker$resendTickStateOnRespawn(ServerPlayer oldPlayer, boolean keepData, Entity.RemovalReason reason, CallbackInfoReturnable<ServerPlayer> cir) {
        PlayerList self = (PlayerList)(Object)this;
        ServerPlayer newPlayer = cir.getReturnValue();
        if (newPlayer != null && newPlayer.connection != null) {
            self.getServer().tickRateManager().updateJoiningPlayer(newPlayer);
        }
    }
}
