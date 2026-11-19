package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class EnchantedAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double num = 0;
		if (entity.isAlive() && !(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))
				&& !(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get()))) {
			if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 6) {
				num = 0;
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!(entityiterator == entity) && (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) >= 10) {
							num = num + 1;
						}
					}
				}
				if (num >= 2 || (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
					if (entity instanceof TideDeathrepellerEntity) {
						((TideDeathrepellerEntity) entity).setAnimation("animation.deathrepeller.enchantattack");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 60, 0, false, false));
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())));
					CaerulaArborMod.queueServerWork(12, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.HOSTILE, 2, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.HOSTILE, 2, 1, false);
							}
						}
						{
							final Vec3 _center = new Vec3((x + 1.8 * entity.getLookAngle().x), (y + 1.5), (z + 1.8 * entity.getLookAngle().z));
							List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
							for (Entity entityiterator : _entfound) {
								if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
									entityiterator.hurt(
											new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:repeller_attack"))), entity),
											(float) ((entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity18.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
													* 2.5));
									for (int index0 = 0; index0 < 2; index0++) {
										GiveLessArmorProcedure.execute(entityiterator, 11);
									}
								}
							}
						}
					});
				}
			}
		}
	}
}
