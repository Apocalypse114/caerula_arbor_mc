
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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.procedures.GetEchoProcedure;
import net.apocalypse.careunite.procedures.IfShowProcedure;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class EchoOnScreenOverlay {

	private static final ResourceLocation EXPLORE = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_explore.png");
	private static final ResourceLocation FIGHT = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_fight.png");
	private static final ResourceLocation REMOULD = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_remould.png");
	private static final ResourceLocation VISSIS = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_vissisitude.png");
	private static final ResourceLocation FEED = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_feed.png");
	private static final ResourceLocation CROWD = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_crowd.png");
	private static final ResourceLocation SPINDLING = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_spindling.png");
	private static final ResourceLocation ADAPT = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_adapt.png");
	private static final ResourceLocation BUTTON = new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_button_16_48.png");
	private static final ResourceLocation BUTTON_1 = new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_button_16_48_linked.png");
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		//int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Player entity = Minecraft.getInstance().player;
		if (!IfShowProcedure.execute(entity)) return;
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		GuiGraphics gui = event.getGuiGraphics();

		int[] renderState = new int[8];
		int renderingIcon = 0;
		for(int i=0;i<8;i++){
			int t = (int) GetEchoProcedure.execute(entity, i);
			renderState[i] = (int) t;
			if(t != 0) renderingIcon ++;
		}
		int dy = -1 * getDy(renderingIcon) + CaerulaConfigsConfiguration.Y_OFFSET_ECHO.get().intValue();
		int dx = CaerulaConfigsConfiguration.X_OFFSET_ECHO.get().intValue();
		
		if (renderState[0] != 0){
			blitEcho(gui, EXPLORE, dx, dy, h); dy += 22;
		}
		if (renderState[1] != 0){
			blitEcho(gui, FIGHT, dx, dy, h); dy += 22;
		}
		if (renderState[2] != 0){
			blitEcho(gui, REMOULD, dx, dy, h); dy += 22;
		}
		if (renderState[3] != 0){
			blitEcho(gui, VISSIS, dx, dy, h); dy += 22;
		}
		if (renderState[4] != 0){
			blitEcho(gui, FEED, dx, dy, h); dy += 22;
		}
		if (renderState[5] != 0){
			blitEcho(gui, CROWD,dx, dy, h); dy += 22;
		}
		if (renderState[6] != 0){
			blitEcho(gui, SPINDLING, dx, dy, h); dy += 22;
		}
		if (renderState[7] != 0){
			blitEcho(gui, ADAPT, dx, dy, h); dy += 22;
		}
		
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	public static void blitEcho(GuiGraphics gui, ResourceLocation res, int dx, int dy, int h){
		gui.blit(res, 2 + dx, h / 2 + dy - 9, 0, 0, 18, 18, 18, 18);
	}

	public static int getDy(int n){
		if (n % 2 == 0) return 20 * (n / 2);
		return 20 * (n / 2) + 9;
	}
}
