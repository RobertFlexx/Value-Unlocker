package com.valueunlocker;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ValueUnlocker implements ModInitializer {
    public static final String MOD_ID = "value-unlocker";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Value Unlocker loaded");
    }
}
