package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.IsharmlaTearEntity;
import net.apocalypse.careunite.entity.GladiiaWhirlEntity;

import java.util.List;
import java.util.Comparator;

public class IsharmlaAbsorptionProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double absP) {
		if (entity == null)
			return;
		boolean isFullSecond = false;
		double damage = 0;
		double d = 0;
		double healthBonus = 0;
		double attackBonus = 0;
		double itrHealth = 0;
		double itrAttack = 0;
		double beforeHealth = 0;
		double beforeAttack = 0;
		double t = 0;
		t = 60 - absP;
		damage = (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.15;
		isFullSecond = t > 0 && t % 20 == 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator instanceof GladiiaWhirlEntity) {
					continue;
				}
				if (entityiterator instanceof Player) {
					continue;
				}
				if (entityiterator instanceof IsharmlaTearEntity) {
					continue;
				}
				if (entity == entityiterator) {
					continue;
				}
				if (!entityiterator.isAlive()) {
					continue;
				}
				d = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
				if (d <= 32) {
					TurnRoundsProcedure.execute(entityiterator, entity);
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))
							&& !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
						itrHealth = (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.01;
						itrAttack = (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 0.01;
						if (Math.random() < 0.25) {
							IsharmlaLinkPtcToEntityProcedure.execute(world, x - 0.5, y + 0.5, z - 0.5, entityiterator);
							if (d >= 3) {
								PullToGladiiaProcedure.execute(entityiterator, entity);
							}
						}
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))),
								(float) damage);
						healthBonus = healthBonus + itrHealth;
						attackBonus = attackBonus + itrAttack;
					}
				}
			}
		}
		beforeHealth = entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity15.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0;
		beforeAttack = entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0;
		if (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
			_livingEntity17.getAttribute(Attributes.MAX_HEALTH).setBaseValue((beforeHealth + Math.min(beforeHealth * 0.025, healthBonus)));
		if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
			_livingEntity18.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((beforeAttack + Math.min(beforeAttack * 0.025, attackBonus)));
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
	}
}
