package com.valueunlocker.mixin;

import com.valueunlocker.api.UnlockedGamerules;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerChunkCache.class)
public abstract class RandomTickSpeedMixin {
    @Unique
    private double valueunlocker$randomTickRemainder;

    @Redirect(
        method = "tickChunks(Lnet/minecraft/util/profiling/ProfilerFiller;J)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;")
    )
    private Object valueunlocker$readNumericRule(GameRules rules, GameRule<?> rule) {
        if (rule != GameRules.RANDOM_TICK_SPEED) {
            return rules.get((GameRule) rule);
        }

        double value = UnlockedGamerules.getDouble(rules, rule);

        if (!Double.isFinite(value)) {
            this.valueunlocker$randomTickRemainder = 0.0D;
            return 0;
        }

        double whole = Math.floor(value);
        // decimals do not fit in vanilla's int loop, so we drag the leftover bullshit into later ticks.
        this.valueunlocker$randomTickRemainder += value - whole;
        int ticks = (int) Math.min(Integer.MAX_VALUE, Math.max(Integer.MIN_VALUE, whole));
        if (this.valueunlocker$randomTickRemainder >= 1.0D && ticks < Integer.MAX_VALUE) {
            ticks++;
            this.valueunlocker$randomTickRemainder -= 1.0D;
        } else if (this.valueunlocker$randomTickRemainder <= -1.0D && ticks > Integer.MIN_VALUE) {
            ticks--;
            this.valueunlocker$randomTickRemainder += 1.0D;
        }
        return ticks;
    }
}
