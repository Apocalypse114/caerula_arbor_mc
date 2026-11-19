package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.apocalypse.careunite.AlchemyCreation;

import net.apocalypse.careunite.entity.NoviceAlchemistEntity;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;

public class NoviceDoInjectBaseProcedure {

	public static boolean shouldInclude(Entity me, AlchemyCreation ac){
		if (me instanceof NoviceAlchemistEntity nov && nov.getEntityData().get(NoviceAlchemistEntity.DATA_IS_BLUE))
			return ac.canInject();
		return ac.shouldTryAbsorb();

	}
	
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		if (world.isClientSide()) return false;
		if (!entity.isAlive()) return false;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<AlchemyCreation> _entfound = world.getEntitiesOfClass(AlchemyCreation.class,
				new AABB(_center, _center).inflate(48 / 2d), e -> shouldInclude(entity, e));
			if (_entfound.isEmpty()) return false;
			for (AlchemyCreation entityiterator : _entfound) {
				if (NotSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				entityiterator.gainBase(1);
				return true;
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.ALCHEMY_BASE.get().spawn(_level, entity.blockPosition(), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
				}
			}

		}
		return false;
	}
}
