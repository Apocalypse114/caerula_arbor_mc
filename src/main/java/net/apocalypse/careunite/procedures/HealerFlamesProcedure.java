package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class HealerFlamesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double vx = 0;
		double vz = 0;
		double dist = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_blast")), SoundSource.PLAYERS, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_blast")), SoundSource.PLAYERS, 2, 1, false);
			}
		}
		{
			final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside")))) {
					continue;
				}
				if (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
					continue;
				}
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 6) {
					vx = entityiterator.getX() - entity.getX();
					vz = entityiterator.getZ() - entity.getZ();
					if (vx == 0) {
						vx = 1;
					}
					if (vz == 0) {
						vz = 1;
					}
					dist = Math.sqrt(vx * vx + vz * vz);
					entityiterator.push((0.85 / Math.max(vx, vx / dist)), 0.25, (0.85 / Math.max(vz, vz / dist)));
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 60, 0, false, false));
					CaerulaArborMod.queueServerWork(8, () -> {
						if (entity.isAlive()) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.INDIRECT_MAGIC), entity),
									(float) ((entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2));
						}
					});
				}
			}
		}
		CaerulaArborMod.queueServerWork(7, () -> {
			if (entity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_attack")), SoundSource.PLAYERS, (float) 1.25, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_attack")), SoundSource.PLAYERS, (float) 1.25, 1, false);
					}
				}
				new Object() {
					void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
						for (int index0 = 0; index0 < 60; index0++) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), (entity.getX() + 1 * (timedloopiterator + 1) * Math.sin(Math.toRadians(index0 * 6))), (entity.getY()),
										(entity.getZ() + 1 * (timedloopiterator + 1) * Math.cos(Math.toRadians(index0 * 6))), 2, 0.1, 0.15, 0.1, 0.1);
						}
						final int tick2 = ticks;
						CaerulaArborMod.queueServerWork(tick2, () -> {
							if (timedlooptotal > timedloopiterator + 1) {
								timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
							}
						});
					}
				}.timedLoop(0, 6, 1);
			}
		});
	}
}
