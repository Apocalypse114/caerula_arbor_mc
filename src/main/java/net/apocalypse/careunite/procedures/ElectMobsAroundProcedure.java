package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

import java.util.List;
import java.util.Comparator;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ElectMobsAroundProcedure {

	public static DamageSource getSource(Entity immediatesourceentity, Entity entity, LevelAccessor world){
		return new DamageSource(world.registryAccess()
			.registryOrThrow(Registries.DAMAGE_TYPE)
			.getHolderOrThrow(
				ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))
				),
 			immediatesourceentity, entity
			);
	}
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ELECT_SPARK.get()), x, y, z, 2, 0.1, 0.1, 0.1, 0.1);
		if (immediatesourceentity.tickCount % 2 == 0) return;
		double ddd = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		final Vec3 _center = new Vec3(x, y, z);
		List<LivingEntity> _entfound = world.getEntitiesOfClass(LivingEntity.class, 
			new AABB(_center, _center).inflate(8), e -> isValid(e, immediatesourceentity))
			.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
		if (_entfound.isEmpty()) return;
		int index = Mth.nextInt(world.getRandom(), 0, _entfound.size()-1);
		LivingEntity chosenOne = _entfound.get(index);
		if (chosenOne != null)
			chosenOne.hurt(getSource(immediatesourceentity, entity, world), (float) ddd);
	}

	public static final TagKey<EntityType<?>> SEABORN = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring"));

	public static boolean isValid(LivingEntity entityiterator, Entity entity){
		if (entityiterator.getType().is(SEABORN)) {
			return false;
		}
		if (entityiterator == entity) {
			return false;
		}
		if (entityiterator instanceof Player) {
			Player player = (Player) entityiterator;
			if (player.isCreative() || player.isSpectator()) return false;
		}
		if (entity.distanceTo(entityiterator) > 8) return false;
		return true;
	}
}
