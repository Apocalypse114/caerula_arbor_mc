package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class ApocataPlayDieSoundProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		String name = "";
		if (!world.isClientSide()) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:apocata_die")), SoundSource.NEUTRAL, 4, (float) 1.5);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:apocata_die")), SoundSource.NEUTRAL, 4, (float) 1.5, false);
				}
			}
		}
		name = sourceentity.getDisplayName().getString();
		if (name.contains("goodsquid") || name.contains("Goodsquid")) {
			if (!entity.level().isClientSide())
				entity.discard();
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.TIDE_CHIMERA.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		}
	}
}
