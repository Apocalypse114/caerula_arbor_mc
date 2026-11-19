package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class ShowMutePtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MUTENESS.get()), x, y, z, 2, 1, 1, 1, 0.1);
		if (entity instanceof Creeper) {
			CompoundTag dataIndex2 = new CompoundTag();
			entity.saveWithoutId(dataIndex2);
			dataIndex2.putBoolean("ignited", false);
			entity.load(dataIndex2);
			if (entity instanceof Creeper _creeper)
				_creeper.setSwellDir(0);
		}
	}
}
