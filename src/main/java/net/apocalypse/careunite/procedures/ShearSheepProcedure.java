package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedSheepEntity;

public class ShearSheepProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if (((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(Items.SHEARS) : false) && entity instanceof OceanizedSheepEntity _datEntL1 && _datEntL1.getEntityData().get(OceanizedSheepEntity.DATA_fur)) {
			if (entity instanceof OceanizedSheepEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedSheepEntity.DATA_fur, false);
			if (entity instanceof OceanizedSheepEntity animatable)
				animatable.setTexture("oceanized_sheep_furless");
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sheep.shear")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sheep.shear")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 0.65), z, new ItemStack(Blocks.WHITE_WOOL));
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 1, 3); index0++) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 0.65), z, new ItemStack(CaerulaArborModItems.SEA_TRAIL_MOR.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			}
			DeductPlayerSanityProcedure.execute(sourceentity, 64);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
