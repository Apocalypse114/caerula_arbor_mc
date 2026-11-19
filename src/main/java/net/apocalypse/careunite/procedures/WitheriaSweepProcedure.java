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
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Comparator;

public class WitheriaSweepProcedure {
	public static void execute(LevelAccessor world, Entity obj, double cosine, double rate) {
		if (obj == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		{
			final Vec3 _center = new Vec3((obj.getX()), (obj.getY()), (obj.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator == obj) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == (obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
						continue;
					}
				}
				if ((entityiterator != null ? obj.distanceTo(entityiterator) : -1) <= 4 && (GetEntityCosineProcedure.execute(obj, entityiterator) >= cosine || entityiterator == (obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
					damage = (obj instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate;
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 0));
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_wither"))), obj),
							(float) damage);
				}
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(obj.getX(), obj.getY(), obj.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:wither_scythe_pre")), SoundSource.HOSTILE, 12, 1);
			} else {
				_level.playLocalSound((obj.getX()), (obj.getY()), (obj.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:wither_scythe_pre")), SoundSource.HOSTILE, 12, 1, false);
			}
		}
	}
}
