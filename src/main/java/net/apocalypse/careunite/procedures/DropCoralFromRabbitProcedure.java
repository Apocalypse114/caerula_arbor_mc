package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizeRabbitEntity;

public class DropCoralFromRabbitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double vvv = 0;
		ItemStack coral = ItemStack.EMPTY;
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			vvv = entity instanceof OceanizeRabbitEntity _datEntI ? _datEntI.getEntityData().get(OceanizeRabbitEntity.DATA_variant) : 0;
			if (vvv == 0) {
				coral = new ItemStack(Blocks.BRAIN_CORAL_FAN).copy();
			} else if (vvv == 1) {
				coral = new ItemStack(Blocks.BUBBLE_CORAL_FAN).copy();
			} else if (vvv == 2) {
				coral = new ItemStack(Blocks.HORN_CORAL_FAN).copy();
			} else if (vvv == 3) {
				coral = new ItemStack(Blocks.FIRE_CORAL_FAN).copy();
			} else if (vvv == 4) {
				coral = new ItemStack(Blocks.TUBE_CORAL_FAN).copy();
			} else if (vvv == 5) {
				coral = new ItemStack(CaerulaArborModItems.BLOODY_RECORD.get()).copy();
			}
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, coral);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
		}
	}
}
