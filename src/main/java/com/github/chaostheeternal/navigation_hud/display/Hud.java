package com.github.chaostheeternal.navigation_hud.display;

import com.github.chaostheeternal.navigation_hud.NavigationHUDMod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper.ColorMixer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class Hud extends Screen {
    public Hud() {
        super(new LiteralText("Navigation HUD"));
    }
    private static final Identifier COMPASS_TEXTURE = new Identifier(NavigationHUDMod.MOD_ID, "textures/gui/compass.png");
    private static final Identifier HORIZON_TEXTURE = new Identifier(NavigationHUDMod.MOD_ID, "textures/gui/horizon.png");
    private static int TextColor = ColorMixer.getArgb(255, 255, 255, 255);

    public static void draw(MatrixStack matrices, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (!mc.options.debugEnabled) { //Only rendering if not in the "debug" panel
            Entity pc = mc.getCameraEntity();
            drawXYZ(matrices, mc, pc);
            drawCompass(matrices, mc, pc);
            if (mc.player.isFallFlying()) { drawHorizonGuide(matrices, mc, pc); }
        }
    }
    private static void drawXYZ(MatrixStack matrices, MinecraftClient client, Entity camera) {
        String posXYZ = String.format("X: %.0f  Z: %.0f      Y: %.0f", Math.floor(camera.getX()), Math.floor(camera.getZ()), Math.floor(camera.getY()));
        drawCenteredString(matrices, client.textRenderer, posXYZ, client.getWindow().getScaledWidth() / 2, 10, TextColor);
    }
    private static void drawCompass(MatrixStack matrices, MinecraftClient client, Entity camera) {
        client.getTextureManager().bindTexture(COMPASS_TEXTURE);
        drawTexture(matrices, (client.getWindow().getScaledWidth() / 2) - 180, 1, 360, 8, 840.5F + (MathHelper.wrapDegrees(camera.yaw) * 3.755F), 0.0F, 360, 8, 2041, 8);
        //Can't apparently do translucent, e.g. image contains pixels that aren't alpha 255 or 0
    }
    private static void drawHorizonGuide(MatrixStack matrices, MinecraftClient client, Entity camera) {
        client.getTextureManager().bindTexture(HORIZON_TEXTURE);
        drawTexture(matrices, (client.getWindow().getScaledWidth() / 2) - 160, (client.getWindow().getScaledHeight() / 2) - 75, 320, 150, 
                    0.0F, 718F + (MathHelper.wrapDegrees(camera.pitch) * 7.0975F), 320, 150, 320, 1586);
    }
}
