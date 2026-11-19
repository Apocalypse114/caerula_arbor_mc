
package net.apocalypse.careunite.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.procedures.ValidTransporterInHandProcedure;
import net.apocalypse.careunite.procedures.GetTransporterContainedEntityProcedure;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class EntityTransporterDisplayerOverlay {
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
		if (ValidTransporterInHandProcedure.execute(entity)) {
			if (GetTransporterContainedEntityProcedure.execute(entity) instanceof LivingEntity livingEntity) {
				InventoryScreen.renderEntityInInventoryFollowsAngle(event.getGuiGraphics(), w - 134, h - 22, 30, 1.1f, 0, livingEntity);
			}
		}
	}
}
