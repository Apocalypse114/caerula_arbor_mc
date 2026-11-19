package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import java.util.ArrayList;

public class CurruptedConvertProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof SkadiCorruptedEntity _datEntL0 && _datEntL0.getEntityData().get(SkadiCorruptedEntity.DATA_mayCorrupt)) {
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:corrupted_corrupt")), SoundSource.HOSTILE, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:corrupted_corrupt")), SoundSource.HOSTILE, 2, 1, false);
					}
				}
			}
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 32) {
					if (entityiterator instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("entity.caerula_arbor_reunite.skadi_corrupted.corrupted").getString())), false);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.ISHARMLA.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		} else {
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:corrupted_convert")), SoundSource.HOSTILE, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:corrupted_convert")), SoundSource.HOSTILE, 2, 1, false);
					}
				}
			}
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 32) {
					if (entityiterator instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("entity.caerula_arbor_reunite.skadi_corrupted.convert").getString())), false);
				}
			}
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.INCANDESCENT_ANIMA.get()));
				entityToSpawn.setPickUpDelay(10);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
			SummonHurtSkadiProcedure.execute(world, x, y, z);
		}
		if (world instanceof ServerLevel _level) {
			ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.TEAR_ISHARMLA.get()));
			entityToSpawn.setPickUpDelay(10);
			_level.addFreshEntity(entityToSpawn);
		}
		if (world instanceof ServerLevel _level) {
			ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.RECORD_UNDERTIDES.get()));
			entityToSpawn.setPickUpDelay(10);
			entityToSpawn.setUnlimitedLifetime();
			_level.addFreshEntity(entityToSpawn);
		}
	}
}
