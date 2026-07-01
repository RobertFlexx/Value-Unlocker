package com.valueunlocker.api;

import net.minecraft.world.level.gamerules.GameRule;

public interface UnlockedGamerulesAccess {
    double valueunlocker$getDouble(GameRule<?> rule);

    void valueunlocker$setDouble(GameRule<?> rule, double value);
}
