package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.RaidSignEntity;

public class DrinkVengetideBottleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.RAID_SIGN.get().spawn(_level, entity.blockPosition(), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn instanceof RaidSignEntity _datEntSetL && 
				(_level.isVillage(entity.blockPosition()) || itemstack.getDisplayName().getString().contains("hard"))) {
				_datEntSetL.getEntityData().set(RaidSignEntity.DATA_IS_HARD, true);
			}
			if (entityToSpawn != null) entityToSpawn.setPos(entity.position());
		}
		itemstack.shrink(1);
	}
}
