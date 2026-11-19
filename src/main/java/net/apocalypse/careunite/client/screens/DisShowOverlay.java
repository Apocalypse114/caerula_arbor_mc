
package net.apocalypse.careunite.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.procedures.HasDisoProcedure;
import net.apocalypse.careunite.procedures.HasDisoNeuroProcedure;
import net.apocalypse.careunite.procedures.HasDisoFleshProcedure;
import net.apocalypse.careunite.procedures.HasDisoBloodProcedure;
import net.apocalypse.careunite.procedures.HasDisoAttentionProcedure;
import net.apocalypse.careunite.procedures.IfShowProcedure;
import net.apocalypse.careunite.procedures.CanPlayerEvoProcedure;

import net.apocalypse.careunite.procedures.IsNexusNoRejectionSelectedProcedure;
import net.apocalypse.careunite.procedures.IsNodesTerminateProcedure;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.Mth;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class DisShowOverlay {

	public static final ResourceLocation BG = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_bg.png");
	public static final ResourceLocation ATTENTION = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_attention.png");
	public static final ResourceLocation BLOOD = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_blood.png");
	public static final ResourceLocation NEURO = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_neuro.png");
	public static final ResourceLocation FLESH = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_flesh.png");

	public static final ResourceLocation PEVO_0 = new ResourceLocation("caerula_arbor_reunite:textures/screens/pevo_layer.png");
	public static final ResourceLocation PEVO_1 = new ResourceLocation("caerula_arbor_reunite:textures/screens/pevo_layer_1.png");

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Player entity = Minecraft.getInstance().player;
		if (entity == null) {
			return;
		}
		Level world = entity.level();
		int t = entity.tickCount;
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		if (HasDisoProcedure.execute(entity)) {
			event.getGuiGraphics().blit(BG, w - 64, h - 128, 0, 0, 64, 128, 64, 128);
			if (HasDisoAttentionProcedure.execute(entity)) {
				event.getGuiGraphics().blit(ATTENTION, w - 37, h - 80 + degCos(t * 3), 0, 0, 32, 32, 32, 32);
			}
			if (HasDisoBloodProcedure.execute(entity)) {
				event.getGuiGraphics().blit(BLOOD, w - 37, h - 80 + degCos(t * 3 + 60), 0, 0, 32, 32, 32, 32);
			}
			if (HasDisoNeuroProcedure.execute(entity)) {
				event.getGuiGraphics().blit(NEURO, w - 37, h - 80 + degCos(t * 3+ 120), 0, 0, 32, 32, 32, 32);
			}
			if (HasDisoFleshProcedure.execute(entity)) {
				event.getGuiGraphics().blit(FLESH, w - 37, h - 80 + degCos(t * 3 + 180), 0, 0, 32, 32, 32, 32);
			}
		}
		if (CanPlayerEvoProcedure.execute(entity) && IfShowProcedure.execute(entity)){
			if (IsNodesTerminateProcedure.execute(entity)){
				event.getGuiGraphics().blit(PEVO_1, w - 32, h - 32, 0, 0, 32, 32, 32, 32);
			} else if (IsNexusNoRejectionSelectedProcedure.execute(entity)){
				event.getGuiGraphics().blit(PEVO_0, w - 32, h - 32, 0, 0, 32, 32, 32, 32);
			}
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	private static int degCos(int d){
		return 8 + (int)(16 * Math.cos(Math.toRadians(d)));
	}
}
