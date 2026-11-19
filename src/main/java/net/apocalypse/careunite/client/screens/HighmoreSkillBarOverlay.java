
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
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.procedures.HighmoreAroundProcedure;
import net.apocalypse.careunite.procedures.GetHighmoreSkillbraProcedure;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class HighmoreSkillBarOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
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
		if (HighmoreAroundProcedure.execute(world, x, y, z)) {

			event.getGuiGraphics().blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/highmore_skill_bar.png"), 8, h / 2 + -41, Mth.clamp((int) GetHighmoreSkillbraProcedure.execute(world, x, y, z) * 4, 0, 340), 0, 4, 87, 344, 87);

		}
	}
}
