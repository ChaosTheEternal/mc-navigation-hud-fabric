package com.github.chaostheeternal.navigation_hud;

import net.fabricmc.api.ClientModInitializer;

import com.github.chaostheeternal.navigation_hud.display.Hud;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationHUDMod implements ClientModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "navigation_hud";
    public static final String MOD_NAME = "Navigation HUD";

    public static Hud HUD = new Hud();

    @Override
    public void onInitializeClient() {
        LOGGER.info("{}:initialize", getClass().getName());
    }
}