package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import java.util.List;
import java.util.Comparator;

public class EndspeakerToPhase2Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double phase) {
		if (entity == null)
			return;
		Entity sacrifice = null;
		double tx = 0;
		double ty = 0;
		double tz = 0;
		double r = 0;
		sacrifice = FindNearestSwallowableMobProcedure.execute(world, x, y, z, entity);
		if (!(sacrifice == null)) {
			tx = sacrifice.getX();
			ty = sacrifice.getY();
			tz = sacrifice.getZ();
			r = 1 + phase;
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_eat")), SoundSource.HOSTILE, 4, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_eat")), SoundSource.HOSTILE, 4, 1, false);
				}
			}
			{
				final Vec3 _center = new Vec3(tx, ty, tz);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player && (entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization > 2) {
						continue;
					}
					if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && (entityiterator != null ? sacrifice.distanceTo(entityiterator) : -1) < r) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))),
								(float) ((double) CaerulaConfigsConfiguration.SANITY_BREAK.get() * 6));
					}
				}
			}
			if (!(sacrifice instanceof Player)) {
				for (int index0 = 0; index0 < 3; index0++) {
					if (sacrifice.isAlive()) {
						sacrifice.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))),
								(float) ((sacrifice instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 2));
					}
				}
			}
		} else {
			tx = x;
			ty = y;
			tz = z;
		}
		if (world instanceof ServerLevel _level)
{
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ENDSPEAKER_PARTICLE.get()), tx, (ty + 1), tz, 128, 1, 1, 1, 0.075);
			Entity entityToSpawn = null;
			if (phase == 1) {
				entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_1.get()
					.spawn(_level, BlockPos.containing(tx, ty, tz), MobSpawnType.MOB_SUMMONED);
			} else if (phase == 2) {
				entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_2.get()
					.spawn(_level, BlockPos.containing(tx, ty, tz), MobSpawnType.MOB_SUMMONED);
			} else {
				entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_3.get()
					.spawn(_level, BlockPos.containing(tx, ty, tz), MobSpawnType.MOB_SUMMONED);
			}
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				SyncTeamAndTargetProcedure.execute(entity, entityToSpawn);
			}
		}
	}
}
