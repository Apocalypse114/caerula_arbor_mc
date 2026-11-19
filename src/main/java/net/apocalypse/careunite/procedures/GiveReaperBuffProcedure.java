package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.ReaperFishEntity;

import java.util.List;
import java.util.Comparator;

public class GiveReaperBuffProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double angle = 0;
		double cTick = 0;
		boolean isCharging = false;
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && entity.tickCount % 20 == 0) {
			if (entity instanceof Mob _mobEnt3 && _mobEnt3.isAggressive() && entity.isAlive()) {
				for (int index0 = 0; index0 < 120; index0++) {
					angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), (x + 5 * Math.sin(angle)), y, (z + 4 * Math.cos(angle)), 8, 0.1, 0.1, 0.1, 0.2);
				}
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 5) {
							if (!(entityiterator == entity)) {
								DeductPlayerSanityProcedure.execute(entityiterator,
										(entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 12);
							}
						}
					}
				}
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.DRY_OUT)),
						(float) ((entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity11.getAttribute(Attributes.MAX_HEALTH).getValue() : 0) * 0.01));
			}
		}
		isCharging = entity instanceof ReaperFishEntity _datEntL14 && _datEntL14.getEntityData().get(ReaperFishEntity.DATA_IS_CHARGING);
		cTick = entity instanceof ReaperFishEntity _datEntI ? _datEntI.getEntityData().get(ReaperFishEntity.DATA_CHARGE_TICK) : 0;
		if (cTick > 0) {
			if (entity instanceof ReaperFishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ReaperFishEntity.DATA_CHARGE_TICK, (int) (cTick - 1));
		}
		if (entity instanceof Mob _mobEnt17 && _mobEnt17.isAggressive()) {
			if (!isCharging && cTick <= 0) {
				if (entity instanceof ReaperFishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ReaperFishEntity.DATA_CHARGE_TICK, 200);
				if (entity instanceof ReaperFishEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ReaperFishEntity.DATA_IS_CHARGING, true);
				if (!world.isClientSide()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:reaper_angry")), SoundSource.HOSTILE, (float) 1.5, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:reaper_angry")), SoundSource.HOSTILE, (float) 1.5, 1, false);
						}
					}
				}
			}
		} else {
			if (entity instanceof ReaperFishEntity _datEntSetL)
				_datEntSetL.getEntityData().set(ReaperFishEntity.DATA_IS_CHARGING, false);
		}
	}
}
