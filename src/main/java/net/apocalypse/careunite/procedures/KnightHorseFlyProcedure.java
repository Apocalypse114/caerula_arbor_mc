package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class KnightHorseFlyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double damage = 0;
		damage = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		{
			final Vec3 _center = new Vec3((x + 2 * entity.getLookAngle().x), y, (z + 2 * entity.getLookAngle().z));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside"))) && !(entityiterator == enemy)) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 4) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:last_knight_attack"))), entity),
							(float) (damage * 1.5));
					entityiterator.push(0, 0.64, 0);
					if ((entityiterator instanceof LivingEntity _entUseItem12 ? _entUseItem12.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
						if (entityiterator instanceof Player _player)
							_player.getCooldowns().addCooldown((entityiterator instanceof LivingEntity _entUseItem14 ? _entUseItem14.getUseItem() : ItemStack.EMPTY).getItem(), 100);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.break")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.break")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
					}
					CaerulaArborMod.queueServerWork(7, () -> {
						entityiterator.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:last_knight_attack"))), entity),
								(float) ((entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity17.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2));
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 150, 0, false, false));
						entityiterator.push(0, (-1), 0);
					});
				}
			}
		}
	}
}
