package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
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
import net.apocalypse.careunite.entity.ChitinGolemEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class GolemSmashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double num = 0;
		if (!entity.isAlive()) {
			return;
		}
		if (!(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
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
			if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 5) {
				if (num >= 2) {
					if (entity instanceof ChitinGolemEntity) {
						((ChitinGolemEntity) entity).setAnimation("animation.chitgolem.smash");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 60, 0, false, false));
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())));
					CaerulaArborMod.queueServerWork(13, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.damage")), SoundSource.HOSTILE, 2, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.damage")), SoundSource.HOSTILE, 2, 1, false);
							}
						}
						{
							final Vec3 _center = new Vec3((x + 2 * entity.getLookAngle().x), (y + 2), (z + 2 * entity.getLookAngle().z));
							List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
							for (Entity entityiterator : _entfound) {
								if (entityiterator instanceof Monster) {
									entityiterator.hurt(
											new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:golem_attack"))), entity),
											(float) ((entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
													* 3.5));
								} else if (entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) {
									entityiterator.hurt(
											new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:golem_attack"))), entity),
											(float) ((entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity21.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
													* 3.5));
								}
							}
						}
					});
				}
			}
		}
	}
}
