
package net.apocalypse.careunite.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.procedures.GetMissRateProcedure;
import net.apocalypse.careunite.procedures.GetMagicResistanceBarProcedure;
import net.apocalypse.careunite.procedures.GetMagicResisProcedure;
import net.apocalypse.careunite.procedures.GetLivingBarrierProcedure;
import net.apocalypse.careunite.procedures.GetLivingBarrierBarProcedure;
import net.apocalypse.careunite.procedures.GetDefenseProcedure;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.Font;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.client.gui.GuiGraphics;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class AttrShowOverlay {

	public static ResourceLocation MISS = new ResourceLocation("caerula_arbor_reunite:textures/screens/miss.png");
	public static ResourceLocation BARRIER = new ResourceLocation("caerula_arbor_reunite:textures/screens/living_barrier.png");
	public static ResourceLocation RESIS = new ResourceLocation("caerula_arbor_reunite:textures/screens/magic_resistance.png");
	public static ResourceLocation DEFENSE = new ResourceLocation("caerula_arbor_reunite:textures/screens/defense.png");
	public static ResourceLocation RESIS_BAR = new ResourceLocation("caerula_arbor_reunite:textures/screens/magic_resistance_bar.png");
	public static ResourceLocation BARRIER_BAR = new ResourceLocation("caerula_arbor_reunite:textures/screens/living_barrier_bar.png");
	private static final ResourceLocation LAYERED = new ResourceLocation("caerula_arbor_reunite:textures/screens/layered_barrier.png");
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity.isSpectator()) return;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		Font font = Minecraft.getInstance().font;

		int dx = CaerulaConfigsConfiguration.X_OFFSET_ATTR.get().intValue();
		int dy = CaerulaConfigsConfiguration.Y_OFFSET_ATTR.get().intValue();
		if (entity.isAlive()) {
			String defense = GetDefenseProcedure.execute(entity);
			String resis = GetMagicResisProcedure.execute(entity);
			String miss = GetMissRateProcedure.execute(entity);
			String barrier = GetLivingBarrierProcedure.execute(entity);
			GuiGraphics gui = event.getGuiGraphics();
			int layered = getLayered(entity);
			if (!defense.isEmpty()){
				gui.blit(DEFENSE, 4+dx, h - 24+dy, 0, 0, 9, 9, 9, 9);
				gui.drawString(font, defense, 13+dx, h - 25+dy, -16777216, false);
				gui.drawString(font, defense, 14+dx, h - 25+dy, -1, false);
			}
			if (!resis.isEmpty()){
				gui.blit(RESIS, 4+dx, h - 13+dy, 0, 0, 9, 9, 9, 9);
				int len = (int) GetMagicResistanceBarProcedure.execute(entity);
				gui.blit(RESIS_BAR, 13+dx, h - 8+dy, 0, 0, 25, 3, 25, 6);
				gui.blit(RESIS_BAR, 13+dx, h - 8+dy, 0, 3, len, 3, 25, 6);
				gui.drawString(font, resis, 15+dx, h - 14+dy, -16777216, false);
				gui.drawString(font, resis, 14+dx, h - 14+dy, -1, false);
			}
			if (!miss.isEmpty()){
				gui.blit(MISS, 42+dx, h - 22+dy, 0, 0, 16, 6, 16, 6);
				gui.drawString(font, miss, 60+dx, h - 25+dy, -16777216, false);
				gui.drawString(font, miss, 59+dx, h - 25+dy, -1, false);
			}
			if (!barrier.isEmpty()){
				event.getGuiGraphics().blit(BARRIER, 42+dx, h - 13+dy, 0, 0, 9, 9, 9, 9);
				int len = (int) GetLivingBarrierBarProcedure.execute(entity);
				gui.blit(BARRIER_BAR, 51+dx, h - 7+dy, 0, 3, 25, 3, 50, 6);
				gui.blit(BARRIER_BAR, 51+dx, h - 8+dy, 0, 0, len, 3, 50, 6);
				gui.drawString(font, barrier, 52+dx, h - 14+dy, -16777216, false);		
				gui.drawString(font, barrier, 51+dx, h - 14+dy, -1, false);
			}
			if (layered > 0){
				if (layered > 9) layered = 9;
				layered -= 1;
				int corX = layered % 3;
				int corY = layered / 3;
				gui.blit(LAYERED, 41+dx, h - 14 +dy, corX * 12, corY * 12, 12, 12, 36, 36);
			}
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	private static int getLayered(Player player){
		AttributeInstance ins = player.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get());
		if (ins == null) return 0;
		return (int)ins.getBaseValue();
	}
}
