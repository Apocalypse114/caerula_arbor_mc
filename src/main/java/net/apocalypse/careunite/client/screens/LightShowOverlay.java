
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

import net.apocalypse.careunite.procedures.LightIsWavingProcedure;
import net.apocalypse.careunite.procedures.LightIsDimProcedure;
import net.apocalypse.careunite.procedures.LightIsCeasedProcedure;
import net.apocalypse.careunite.procedures.LightIsBrightProcedure;
import net.apocalypse.careunite.procedures.IfShowProcedure;
import net.apocalypse.careunite.procedures.HaveShieldProcedure;
import net.apocalypse.careunite.procedures.GetShieldProcedure;
import net.apocalypse.careunite.procedures.GetLivesProcedure;
import net.apocalypse.careunite.procedures.GetLiveMaxShownProcedure;
import net.apocalypse.careunite.procedures.GetLightProcedure;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.client.gui.Font;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class LightShowOverlay {
	public static final ResourceLocation EXTINGUISH = new ResourceLocation("caerula_arbor_reunite:textures/screens/light_extinguish.png");
	public static final ResourceLocation DIM = new ResourceLocation("caerula_arbor_reunite:textures/screens/light_dim.png");
	public static final ResourceLocation WAVING = new ResourceLocation("caerula_arbor_reunite:textures/screens/light_waving.png");
	public static final ResourceLocation BRIGHT = new ResourceLocation("caerula_arbor_reunite:textures/screens/light.png");

	public static final ResourceLocation LIFE_POINT = new ResourceLocation("caerula_arbor_reunite:textures/screens/target_health.png");
	public static final ResourceLocation SHIELD_POINT = new ResourceLocation("caerula_arbor_reunite:textures/screens/target_shield.png");

	public static final ResourceLocation NEAT = new ResourceLocation("caerula_arbor_reunite:textures/screens/lights_neat.png");

	@SubscribeEvent(priority = EventPriority.HIGHEST)
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
		if (IfShowProcedure.execute(entity)) {
			boolean isNeat = CaerulaConfigsConfiguration.LIGHTS_NEAT_STYLE.get();
			int lightDx = CaerulaConfigsConfiguration.X_OFFSET_LIGHT.get().intValue();
			int lightDy = CaerulaConfigsConfiguration.Y_OFFSET_LIGHT.get().intValue();
			Font mcFont = Minecraft.getInstance().font;
			if (LightIsCeasedProcedure.execute(entity)) {
				if (isNeat){
					event.getGuiGraphics().blit(NEAT,
					w / 2 - 9 + lightDx, 2 + lightDy, 18, 0, 6, 13, 24, 13);
				} else {
					event.getGuiGraphics().blit(EXTINGUISH,
					w / 2 - 32 + lightDx, lightDy, 0, 0, 64, 32, 64, 32);
				}
			}
			if (LightIsDimProcedure.execute(entity)) {
				if (isNeat){
					event.getGuiGraphics().blit(NEAT,
					w / 2 - 9 + lightDx, 2 + lightDy, 12, 0, 6, 13, 24, 13);
				} else {
					event.getGuiGraphics().blit(DIM, 
					w / 2 - 32 + lightDx, lightDy, 0, 0, 64, 32, 64, 32);
				}
			}
			if (LightIsWavingProcedure.execute(entity)) {
				if (isNeat) {
					event.getGuiGraphics().blit(NEAT,
					w / 2 - 9 + lightDx, 2 + lightDy, 6, 0, 6, 13, 24, 13);
				} else {
					event.getGuiGraphics().blit(WAVING, 
					w / 2 + -32 + lightDx, lightDy, 0, 0, 64, 32, 64, 32);
				}
			}
			if (LightIsBrightProcedure.execute(entity)) {
				if (isNeat) {
					event.getGuiGraphics().blit(NEAT,
					w / 2 - 9 + lightDx, 2 + lightDy, 0, 0, 6, 13, 24, 13);
				} else {
					event.getGuiGraphics().blit(BRIGHT,
					w / 2 + -32 + lightDx, lightDy, 0, 0, 64, 32, 64, 32);
				}
			}

			int lifeDx = CaerulaConfigsConfiguration.X_OFFSET_LIFE.get().intValue();
			int lifeDy = CaerulaConfigsConfiguration.Y_OFFSET_LIFE.get().intValue() - 16;

			event.getGuiGraphics().blit(LIFE_POINT, 
				6 + lifeDx, h - 24 + lifeDy, 0, 0, 24, 16, 24, 16);

			if (HaveShieldProcedure.execute(entity)) {
				int sDx = CaerulaConfigsConfiguration.X_OFFSET_SHIELD.get().intValue();
				int sDy = CaerulaConfigsConfiguration.Y_OFFSET_SHIELD.get().intValue();
				event.getGuiGraphics().blit(SHIELD_POINT, 
					6 + lifeDx + sDx, h - 40 + lifeDy + sDy, 0, 0, 24, 16, 24, 16);
				String shield = GetShieldProcedure.execute(entity);
				event.getGuiGraphics().drawString(mcFont,
						shield, 21 + lifeDx + sDx, h - 36 + lifeDy + sDy, -16777216, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font,
						shield, 20 + lifeDx + sDx, h - 36 + lifeDy + sDy, -1, false);
			}
			String light = GetLightProcedure.execute(entity);
			if (isNeat) {
				event.getGuiGraphics().drawString(mcFont,
						light, w / 2 - 1 + lightDx, 7 + lightDy, -13421773, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font,
						light, w / 2 - 2 + lightDx, 7 + lightDy, -1, false);
			} else {
				event.getGuiGraphics().drawString(mcFont,
						light, w / 2 - 5 + lightDx, 26 + lightDy, -13421773, false);
				event.getGuiGraphics().drawString(mcFont,
						light, w / 2 - 6 + lightDx, 26 + lightDy, -1, false);
			}
			int color = -1, bgColor = -16764109;
			String life_max = GetLiveMaxShownProcedure.execute(entity), life = GetLivesProcedure.execute(entity);
			if (life.equals("1")) color = -26215;
			else if (life.equals("325")) color = rainbow(entity.tickCount, -52, -65536);
			else if (life.equals("799")) color = -3381505;
			int len = mcFont.width(life);
			event.getGuiGraphics().drawString(mcFont,
					life_max, 22 + lifeDx + len, h - 21 + lifeDy, bgColor, false);
				
			event.getGuiGraphics().drawString(mcFont,
					life_max, 21 + lifeDx + len, h - 21 + lifeDy, -10040065, false);
					
			event.getGuiGraphics().drawString(mcFont,
					life, 21 + lifeDx, h - 21 + lifeDy, bgColor, false);
				
			event.getGuiGraphics().drawString(mcFont,
					life, 20 + lifeDx, h - 21 + lifeDy, color, false);
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	private static int rainbow(int t, int C1, int C2){
		return C2 + (int) ((C1 - C2) * circle(t * 3, 200) * 0.005f);
	}

	private static int circle(int t, int c){
		t %= 2 * c;
		return t < c ? t : 2 * c - t;
	}
}
