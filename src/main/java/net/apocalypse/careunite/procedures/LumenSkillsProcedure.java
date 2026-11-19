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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.LumenEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class LumenSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double atk = 0;
		double blink = 0;
		double dura = 0;
		if (entity.isAlive()) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			sklp1 = entity instanceof LumenEntity _datEntI ? _datEntI.getEntityData().get(LumenEntity.DATA_SKILLP_1) : 0;
			sklp2 = entity instanceof LumenEntity _datEntI ? _datEntI.getEntityData().get(LumenEntity.DATA_SKILLP_2) : 0;
			blink = entity instanceof LumenEntity _datEntI ? _datEntI.getEntityData().get(LumenEntity.DATA_BLINK_T) : 0;
			dura = entity instanceof LumenEntity _datEntI ? _datEntI.getEntityData().get(LumenEntity.DATA_DURATION) : 0;
			if (dura > 0) {
				if (entity instanceof LumenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LumenEntity.DATA_DURATION, (int) (dura - 1));
			}
			if (blink > 0) {
				if (entity instanceof LumenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LumenEntity.DATA_BLINK_T, (int) (blink - 1));
			}
			if (sklp1 > 0) {
				if (entity instanceof LumenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LumenEntity.DATA_SKILLP_1, (int) (sklp1 - 1));
			} else if (dura <= 0) {
				if (entity.tickCount % 5 == 0 && !(enemy == null) && enemy.isAlive()) {
					atk = entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:lumen_skill")), SoundSource.NEUTRAL, (float) 1.8,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:lumen_skill")), SoundSource.NEUTRAL, (float) 1.8, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(24 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (CanInquisitionHealProcedure.execute(entityiterator)) {
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 9, false, false));
								if (entity instanceof LumenEntity _datEntSetI)
									_datEntSetI.getEntityData().set(LumenEntity.DATA_SKILLP_1, 300);
								if (entity instanceof LumenEntity _datEntSetI)
									_datEntSetI.getEntityData().set(LumenEntity.DATA_DURATION, 25);
								dura = 25;
								if (entity instanceof LumenEntity) {
									((LumenEntity) entity).setAnimation("skill");
								}
								CaerulaArborMod.queueServerWork(13, () -> {
									if (entity.isAlive()) {
										LumenSkillHealProcedure.execute(world, x, y, z, entity);
									}
								});
								break;
							}
						}
					}
				}
			}
			if (sklp2 > 0) {
				if (entity instanceof LumenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LumenEntity.DATA_SKILLP_2, (int) (sklp2 - 1));
			} else if (dura <= 0) {
				if (!(enemy == null) && enemy.isAlive()) {
					if ((entity != null ? enemy.distanceTo(entity) : -1) <= 3) {
						if (entity instanceof LumenEntity _datEntSetI)
							_datEntSetI.getEntityData().set(LumenEntity.DATA_SKILLP_2, 400);
						if (entity instanceof LumenEntity _datEntSetI)
							_datEntSetI.getEntityData().set(LumenEntity.DATA_DURATION, 20);
						if (entity instanceof LumenEntity) {
							((LumenEntity) entity).setAnimation("melee");
						}
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 9, false, false));
						CaerulaArborMod.queueServerWork(8, () -> {
							if (entity.isAlive() && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).isAlive()) {
								if ((entity != null ? (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).distanceTo(entity) : -1) <= 4) {
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.crit")), SoundSource.NEUTRAL, 1,
													(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
										} else {
											_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.crit")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1),
													false);
										}
									}
									(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).hurt(
											new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))),
													entity),
											(float) ((entity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity39.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
													* 2));
								}
							}
						});
					}
				}
			}
		}
	}
}
