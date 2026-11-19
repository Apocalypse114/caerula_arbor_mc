package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

import java.util.List;
import java.util.Comparator;

public class MoistCrystalExplodeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double d = 0;
		Entity enderina = null;
		if (world.isClientSide()) {
			return;
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_crystal_explode")), SoundSource.NEUTRAL, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_crystal_explode")), SoundSource.NEUTRAL, 3, 1, false);
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 0.5), z, 4, 0.5, 0.5, 0.5, 0.1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), x, (y + 0.5), z, 32, 1, 1, 1, 0.18);
		d = entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		if (d > 0) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
						continue;
					}
					if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 4) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.EXPLOSION), entity), (float) d);
					}
				}
			}
		}
		enderina = (Entity) world.getEntitiesOfClass(OceanizedEnderinaEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (enderina == null) {
			return;
		}
		if ((enderina instanceof OceanizedEnderinaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEnderinaEntity.DATA_REVIVE_TICK) : 0) > 0) {
			enderina.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))),
					(float) ((enderina instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 0.25));
		} else {
			LivingHeal1Procedure.execute(enderina, (enderina instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05);
			if (enderina instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
				_livingEntity18.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(1000);
		}
	}
}
