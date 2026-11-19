
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

import net.apocalypse.careunite.procedures.KuntusIsAroundProcedure;
import net.apocalypse.careunite.procedures.GetBoshopSkillIndexProcedure;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class BishopSkillBarOverlay {
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
		if (KuntusIsAroundProcedure.execute(world, x, y, z)) {

			event.getGuiGraphics().blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/bishop_skill_bar.png"), 2, h / 2 + -48, Mth.clamp((int) GetBoshopSkillIndexProcedure.execute(world, x, y, z) * 4, 0, 400), 0, 4, 102, 404, 102);

		}
	}
}
