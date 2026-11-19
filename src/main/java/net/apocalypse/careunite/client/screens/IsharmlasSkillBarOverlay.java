
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
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.procedures.IsharmlaAroundProcedure;
import net.apocalypse.careunite.procedures.GetIsharmlaSkillIndexProcedure;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class IsharmlasSkillBarOverlay {

	public static final ResourceLocation BAR = new ResourceLocation("caerula_arbor_reunite:textures/screens/isharmla_bar.png");

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
		if (IsharmlaAroundProcedure.execute(world, x, y, z)) {

			double process = GetIsharmlaSkillIndexProcedure.execute(world, x, y, z);
			int len = (int) (process * 100);
			event.getGuiGraphics().blit(BAR, 9, h / 2 -54, 0, 0, 8, 109, 10, 109);

			event.getGuiGraphics().blit(BAR, 12, h / 2 -50 + (100-len), 8, 100 - len, 2, len, 10, 109);
		}
	}
}
