package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.AegirMortarEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class MortarFireProcedure {
	public static void execute(LevelAccessor world, Entity entity, double firemode, double tX, double tY, double tZ) {
		if (entity == null)
			return;
		double x = 0;
		double y = 0;
		double z = 0;
		double commonCost = 0;
		double superCost = 0;
		double cooldown = 0;
		if (entity instanceof AegirMortarEntity) {
			((AegirMortarEntity) entity).setAnimation("animation.aegir_mortar.fire");
		}
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 4), z, 4, 0.5, 0.5, 0.5, 0.1);
		MortarAscendingSmokeProcedure.execute(world, x, y + 4, z);
		if (firemode == 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_shoot_0")), SoundSource.BLOCKS, (float) 1.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_shoot_0")), SoundSource.BLOCKS, (float) 1.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			commonCost = 3;
			cooldown = 40;
		} else if (firemode == 1) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_shoot_1")), SoundSource.BLOCKS, (float) 1.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_shoot_1")), SoundSource.BLOCKS, (float) 1.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			commonCost = 9;
			cooldown = 70;
		} else if (firemode == 2) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_shoot_2")), SoundSource.BLOCKS, (float) 1.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_shoot_2")), SoundSource.BLOCKS, (float) 1.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			commonCost = 12;
			superCost = 1;
			cooldown = 100;
		}
		CaerulaArborMod.queueServerWork(10, () -> {
			MortarDescendingSmokeProcedure.execute(world, tX, tY, tZ, entity, firemode);
		});
		if (entity instanceof AegirMortarEntity _datEntSetI)
			_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_COMMON_AMMO, (int) ((entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_COMMON_AMMO) : 0) - commonCost));
		if (entity instanceof AegirMortarEntity _datEntSetI)
			_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_SUPER_AMMO, (int) ((entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_SUPER_AMMO) : 0) - superCost));
		if (entity instanceof AegirMortarEntity _datEntSetI)
			_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_FIRE_COOLDOWN, (int) cooldown);
	}
}
