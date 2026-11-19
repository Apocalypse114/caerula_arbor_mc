package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.LingeringFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;

import java.util.List;
import java.util.Comparator;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.scores.Team;

public class LingeringRebornProcedure {

	public static boolean checkSameTeam(Entity a, Entity b){
		if (a == null || b == null)
			return false;
		Team at = a.getTeam();
		Team bt = b.getTeam();
		if (at == null) return bt == null;
        return at.isAlliedTo(bt);
	}

	public static final ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage"));
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount > 1200 && entity.tickCount % 20 == 7) {
			if (world.getEntitiesOfClass(LineringPathshaperEntity.class, 
					AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> checkSameTeam(entity, e)).isEmpty()) {
				final Vec3 _center = new Vec3(x, y, z);
				List<LingeringFractalEntity> _entfound = world.getEntitiesOfClass(
					LingeringFractalEntity.class, 
					new AABB(_center, _center).inflate(32), 
					e -> checkSameTeam(entity, e))
						.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				if (_entfound.isEmpty()) return;
				for (LingeringFractalEntity entityiterator : _entfound) {
					entityiterator.hurt(
						new DamageSource(world.registryAccess()
							.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key)),
						1145141919);
				}
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.LINGERING_PATHSHAPER.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
					}
				}
			}
		}
	}
}
