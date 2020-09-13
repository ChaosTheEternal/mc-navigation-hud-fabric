package com.github.chaostheeternal.navigation_hud.display;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper.ColorMixer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

public class Hud extends Screen {
    private static int TextColor;
    public Hud() {
        super(new LiteralText("Navigation HUD"));
        TextColor = ColorMixer.getArgb(218, 218, 218, 218); //TODO: maybe see about making this a configuration item?
        // Could only work for the one thing though, I don't know if I could do the compass and horizon "graphics" like this as well
    }

    public static void draw(MatrixStack matrices, float tickDelta)
	{
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
        //NOTE: The graphic will probably have to be "W...NW...N...NE...E...SE...S...SW...W...NW...N...NE...E"
        // with 0 deciding to render from S and moving left for negative and right for positive (with the "180" area flipping)
        // It'd be hard to do this as text and keep the NW/NE/SW/SE items
        //client.getTextureManager().bindTexture(TEXTURE_ID);
        //drawTexture(matrices, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
        float yaw = MathHelper.wrapDegrees(camera.yaw);
        String direction = "N ";
        if (157.5F > yaw && yaw >= 112.5F) {
            direction = "NW";
        } else if (112.5F > yaw && yaw >= 67.5F) {
            direction = "W ";
        } else if (67.5F > yaw && yaw >= 22.5F) {
            direction = "SW";
        } else if (22.5F > yaw && yaw >= -22.5F) {
            direction = "S ";
        } else if (-22.5F > yaw && yaw >= -67.5F) {
            direction = "SE";
        } else if (-67.5F > yaw && yaw >= -112.5F) {
            direction = "E ";
        } else if (-112.5F > yaw && yaw >= -157.5F) {
            direction = "NE";
        }
        String compass = String.format("Direction: %s (%.1f)", direction, yaw);
        drawStringWithShadow(matrices, client.textRenderer, compass, 1, 1, TextColor); //This is going to change out to a "graphic"
    }
    private static void drawHorizonGuide(MatrixStack matrices, MinecraftClient client, Entity camera) {
        //client.getTextureManager().bindTexture(TEXTURE_ID);
        //drawTexture(matrices, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
        String horizon = String.format("Flying Angle: %.1f", MathHelper.wrapDegrees(camera.pitch));
        drawStringWithShadow(matrices, client.textRenderer, horizon, 1, 19, TextColor); //This is also going to change out to a "graphic"
    }
}
