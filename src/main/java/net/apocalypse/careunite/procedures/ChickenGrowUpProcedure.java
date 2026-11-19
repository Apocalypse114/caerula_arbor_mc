package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanizedChickenEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class ChickenGrowUpProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double lay = 0;
		double grow = 0;
		boolean is_child = false;
		is_child = entity instanceof OceanizedChickenEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedChickenEntity.DATA_IS_CHILD);
		if (is_child) {
			if (entity instanceof OceanizedChickenEntity animatable)
				animatable.setTexture("oceanized_chicken_child");
			grow = entity instanceof OceanizedChickenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedChickenEntity.DATA_GROW_TIME) : 0;
			if (grow > 0) {
				if (entity instanceof OceanizedChickenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_GROW_TIME, (int) (grow - 1));
			} else {
				if (entity instanceof OceanizedChickenEntity _datEntSetL)
					_datEntSetL.getEntityData().set(OceanizedChickenEntity.DATA_IS_CHILD, false);
			}
		} else {
			if (entity instanceof OceanizedChickenEntity animatable)
				animatable.setTexture("oceanized_chicken_adult");
			lay = entity instanceof OceanizedChickenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedChickenEntity.DATA_LAY_COOLDOWN) : 0;
			if (lay > 0) {
				if (entity instanceof OceanizedChickenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_LAY_COOLDOWN, (int) (lay - 1));
			} else {
				if (entity instanceof OceanizedChickenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_LAY_COOLDOWN, Mth.nextInt(RandomSource.create(), 2400, 4800));
				if (entity instanceof OceanizedChickenEntity) {
					((OceanizedChickenEntity) entity).setAnimation("animation.oceanized_chicken.lay");
				}
				CaerulaArborMod.queueServerWork(5, () -> {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.egg")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.egg")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()), LayNetherseaEggProcedure.execute(entity));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
				});
			}
		}
	}
}
