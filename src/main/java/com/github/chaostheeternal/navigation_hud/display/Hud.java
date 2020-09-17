package com.github.chaostheeternal.navigation_hud.display;

import java.util.Locale;

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
    private static int TextColor;
    public Hud() {
        super(new LiteralText("Navigation HUD"));
        TextColor = ColorMixer.getArgb(218, 218, 218, 218);
    }
    private static final Identifier COMPASS_TEXTURE = new Identifier(NavigationHUDMod.MOD_ID, "textures/gui/compass.png");
    private static final Identifier HORIZON_TEXTURE = new Identifier(NavigationHUDMod.MOD_ID, "textures/gui/horizon.png");
    // java.io.FileNotFoundException: navigation_hud:textures/gui/compass.png
    // But, that path *does* exist.

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
        drawTexture(matrices, (client.getWindow().getScaledWidth() / 2) - 130, 1, 260, 8, 266.5F + (MathHelper.wrapDegrees(camera.yaw) * 1.4444F), 0.0F, 260, 8, 793, 8);
        //I think this works except that the texture loading isn't actually working
    }
    private static void drawHorizonGuide(MatrixStack matrices, MinecraftClient client, Entity camera) {
        /* Think the graphic should have DANGER printed at 90 and -90 in the middle of the horizon line
           So it looks like
                -90 ===         DANGER         === -90
                -80 ====                      ==== -80
                -70 =====                    ===== -70
                -60 ======                  ====== -60
                -50 =======                ======= -50
                -40 ========              ======== -40
                -30 =========            ========= -30
                -20 ==========          ========== -20
                -10 ===========        =========== -10
                =================[  ]=================
                +10 ===========        =========== +10
                +20 ==========          ========== +20
                +30 =========            ========= +30
                +40 ========              ======== +40
                +50 =======                ======= +50
                +60 ======                  ====== +60
                +70 =====                    ===== +70
                +80 ====                      ==== +80
                +90 ===         DANGER         === +90
          But obviously with more space between the lines
        */
        //client.getTextureManager().bindTexture(TEXTURE_ID);
        //drawTexture(matrices, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
        String horizon = String.format("Flying Angle: %.1f", MathHelper.wrapDegrees(camera.pitch));
        drawStringWithShadow(matrices, client.textRenderer, horizon, 1, 19, TextColor); //This is also going to change out to a "graphic"
    }
}
