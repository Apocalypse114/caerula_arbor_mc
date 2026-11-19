package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
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

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.CreeperPetEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class RangedPetSanityAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double atk = 0;
		double angle = 0;
		Entity owner = null;
		Entity enemy = null;
		boolean charged = false;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				ThirsterRimedParticleProcedure.execute(world, x, y, z, timedloopiterator + 1);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 4, 2);
		owner = entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REGENERATION_PERCLY.get(), 40, 0, false, false));
		if (entity instanceof CreeperPetEntity _datEntL5 && _datEntL5.getEntityData().get(CreeperPetEntity.DATA_CHARGED)) {
			atk = (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2.5;
		} else {
			atk = entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:creeper_fish_explode")), SoundSource.HOSTILE, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:creeper_fish_explode")), SoundSource.HOSTILE, 3, 1, false);
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if (IsSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				if (!(entityiterator instanceof Monster)) {
					if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == owner || enemy == entityiterator)) {
						continue;
					}
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 4) {
					if (owner == entityiterator) {
						LivingHeal1Procedure.execute(owner, atk * 2);
					} else {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
								(float) atk);
						DeductPlayerSanityProcedure.execute(entityiterator, atk * 150);
					}
				}
			}
		}
	}
}
