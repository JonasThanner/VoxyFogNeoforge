package eu.qsmium.voxyfog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import eu.qsmium.voxyfog.VoxyCompat;
import eu.qsmium.voxyfog.VoxyFogConfig;
import eu.qsmium.voxyfog.VoxyFogTuning;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FogRenderer.class, priority = 2000)
public abstract class MixinFogRenderer
{
    @Inject(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/client/ClientHooks;onFogRender(Lnet/minecraft/client/renderer/FogRenderer$FogMode;Lnet/minecraft/world/level/material/FogType;Lnet/minecraft/client/Camera;FFFFLcom/mojang/blaze3d/shaders/FogShape;)V",
                    remap = true
            )
    )
    private static void voxyfog$applyVoxyDistanceFog(
            Camera camera,
            FogRenderer.FogMode fogMode,
            float farPlaneDistance,
            boolean shouldCreateFog,
            float partialTick,
            CallbackInfo ci,
            @Local(type = FogRenderer.FogData.class) FogRenderer.FogData fogData
    ) {
        VoxyCompat.State state = VoxyCompat.getState();
        if (!VoxyFogConfig.SafeConfigGetValue(VoxyFogConfig.CONFIG.enabled)) {
            return;
        }

        if (fogData.end < 16.0f) {
            return;
        }

        float vanillaDistanceBlocks = Minecraft.getInstance().gameRenderer.getRenderDistance();
        VoxyFogTuning.FogRange fogRange = VoxyFogTuning.compute(state.renderDistanceBlocks(), vanillaDistanceBlocks);
        if (fogRange == null) {
            return;
        }

        //We want to run this after roxy does fog modifications, because roxy is dumb and doesnt run fog modifications the simple way
        //and does some super duper complicated stuffs where it does alot of math just to then not apply any fog ꒰(´。＿。｀)꒱
        fogData.start = Math.min(fogData.start, fogRange.start());
        fogData.end = Math.max(fogData.end, fogRange.end());
        RenderSystem.setShaderFogStart(fogData.start);
        RenderSystem.setShaderFogEnd(fogData.end);
    }
}
