
package net.apocalypse.careunite.client.screens;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.procedures.ShowSanityProcedure;
import net.apocalypse.careunite.procedures.GetSanityIndexProcedure;
import net.apocalypse.careunite.procedures.GetSanityPercentageProcedure;
import net.apocalypse.careunite.procedures.GetOverlayOffsetProcedure;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class SanityShowOverlay {
	public static final ResourceLocation SANITY = new ResourceLocation("caerula_arbor_reunite:textures/screens/sanity.png");
	public static final ResourceLocation BAR = new ResourceLocation("caerula_arbor_reunite:textures/screens/sanity_player_bar.png");
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (ShowSanityProcedure.execute(entity)) {
			int dx = GetOverlayOffsetProcedure.x();
			int dy = GetOverlayOffsetProcedure.y();
			if (CaerulaConfigsConfiguration.SANITY_BAR_STYLE.get()){
				event.getGuiGraphics().blit(BAR, w / 2 + 93 + dx, h - 12 + dy, 
				0, 4, 62, 8, 62, 12);
				event.getGuiGraphics().blit(BAR, w / 2 + 93 + dx + 10, h - 12 + dy + 3, 
				0, 0, (int)(50 * GetSanityPercentageProcedure.execute(entity)), 4, 62, 12);

			} else {
				event.getGuiGraphics().blit(SANITY, w / 2 + 92 + dx, h - 19 + dy, 
				Mth.clamp((int) GetSanityIndexProcedure.execute(entity) * 16, 0, 304), 0, 16, 16, 320, 16);
			}
		}
	}
}
