package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class KnightCrossAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double damage = 0;
		Entity enemy = null;
		damage = (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		for (int index0 = 0; index0 < 96; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ENCHANTED_HIT, x - 12 + index0 * 0.25, y, (z + 2), 2, 0, 0.5, 0, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ENCHANTED_HIT, x - 12 + index0 * 0.25, y, (z - 2), 2, 0, 0.5, 0, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ENCHANTED_HIT, (x + 2), y, z - 12 + index0 * 0.25, 2, 0, 0.5, 0, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ENCHANTED_HIT, (x - 2), y, z - 12 + index0 * 0.25, 2, 0, 0.5, 0, 0.1);
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_knight_attack")), SoundSource.HOSTILE, 5, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_knight_attack")), SoundSource.HOSTILE, 5, 1, false);
			}
		}
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 16), (y + 4), (z + 1.5), (x - 16), (y - 2), (z - 1.5)))) {
			if (entityiterator instanceof LivingEntity) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside")))) {
					if (!(entityiterator == enemy)) {
						continue;
					}
				}
				entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:last_knight_attack"))), entity),
						(float) damage);
			}
		}
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 1.5), (y + 4), (z + 16), (x - 1.5), (y - 2), (z - 16)))) {
			if (entityiterator instanceof LivingEntity) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside")))) {
					if (!(entityiterator == enemy)) {
						continue;
					}
				}
				entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:last_knight_attack")))),
						(float) damage);
			}
		}
	}
}
