package net.apocalypse.careunite.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.player.Player;
import net.apocalypse.careunite.client.renderer.ThirsterRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.apocalypse.careunite.CARenderUtil;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.procedures.IsAlchemistSuitProcedure;
import net.minecraft.client.Minecraft;

@Mixin(EntityRenderer.class)
public abstract class RendererMixin {
	@Inject(
		method = "render", 
		at = @At("RETURN")
		)		
    private void renderBarr(Entity pEntity,
                            float pEntityYaw, float pPartialTick,
                            PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci){
        if (pEntity instanceof Player) return;
        if (pEntity instanceof LivingEntity liv){
        	ThirsterRenderer.renderPrism(pEntity, pPartialTick, pPoseStack, pBuffer, LightTexture.FULL_BRIGHT);
        	CARenderUtil.renderSanity(pEntity, pPoseStack, pBuffer, pPackedLight);
        	CARenderUtil.renderLayeredBarrier(liv, pPoseStack, pBuffer);
        }
        Player plr = Minecraft.getInstance().player;
        if ((plr.isSpectator() || IsAlchemistSuitProcedure.execute(plr, false)) && pEntity instanceof AlchemyCreation ac){
        	CARenderUtil.renderAlchemy(ac, pPoseStack, pBuffer, pPackedLight);
        }
    }
}
