package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.entity.BeaconSelectorEntity;

public class PutBeaconSelectorProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			_level.playSound(null, x, y, z, CaerulaArborModSounds.BEACON_SKILL_RELEASE.get(), SoundSource.NEUTRAL, 1,
				world.getRandom().nextFloat() * 0.2f + 0.9f);
			Entity entityToSpawn = CaerulaArborModEntities.BEACON_SELECTOR.get().create(_level);
			if (entityToSpawn instanceof BeaconSelectorEntity bb && entity instanceof LivingEntity ll) {
				bb.setOwner(ll);
				bb.setPos(x, y, z);
				_level.addFreshEntity(bb);
			}
		}
	}
}
