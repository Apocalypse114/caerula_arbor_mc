package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

public class HoarderDropTuriaProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack drop = ItemStack.EMPTY;
		String name = "";
		name = entity.getDisplayName().getString();
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && ((name).equals("\u56FE\u8389\u5A05") || (name).equals("Turia"))) {
			drop = new ItemStack(Items.PUFFERFISH).copy();
			drop.setHoverName(Component.literal(name));
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, drop);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
		}
	}
}
