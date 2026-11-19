
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

import net.apocalypse.careunite.procedures.IsCorruptedAroundProcedure;
import net.apocalypse.careunite.procedures.GetCorruptedFrameProcedure;
import net.apocalypse.careunite.procedures.GetCorruptedBarProcedure;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class CorruptedBarOverlay {
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
		if (IsCorruptedAroundProcedure.execute(world, x, y, z)) {

			event.getGuiGraphics().blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/corrupted_bar_inner.png"), 28, h / 2 + -42, Mth.clamp((int) GetCorruptedBarProcedure.execute(world, x, y, z) * 2, 0, 100), 0, 2, 88, 102, 88);

			event.getGuiGraphics().blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/corrupted_bar.png"), 25, h / 2 + -48, 0, Mth.clamp((int) GetCorruptedFrameProcedure.execute(world, x, y, z) * 99, 0, 99), 8, 99, 8, 198);

		}
	}
}
