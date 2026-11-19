package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.IzumikEntity;

import java.util.List;
import java.util.Comparator;

public class IzumikShokedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double r) {
		if (entity == null)
			return;
		double rate = 0;
		rate = 0.25;
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 4) {
			rate = 0.35;
		}
		for (int index0 = 0; index0 < 120; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (x + r * Math.sin(Math.toRadians(index0 * 3))), (y + 0.5), (z + r * Math.cos(Math.toRadians(index0 * 3))), 3, 0.15, 0.5, 0.15, 0.15);
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
						continue;
					}
				}
				if (!(entityiterator instanceof Mob) && !(entityiterator instanceof Player)) {
					continue;
				}
				if (new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
						}
						return false;
					}
				}.checkGamemode(entityiterator)) {
					continue;
				}
				if (new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
						}
						return false;
					}
				}.checkGamemode(entityiterator)) {
					continue;
				}
				if (IsSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= r) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:izumik_skill")))),
							(float) ((entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate));
					if (!(entityiterator instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(CaerulaArborModMobEffects.IZUMIK_SHOCK.get()))) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IZUMIK_SHOCK.get(), 160, 0, false, false));
					}
					if (!(entityiterator instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(CaerulaArborModMobEffects.DIZZY.get()))) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 160, 0, false, false));
					}
					if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
						_livingEntity18.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								.setBaseValue(Math.min((entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
										? _livingEntity16.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
										: 0) + 0.25, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05));
					if ((entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) >= 2) {
						if (Math.random() < 0.33) {
							if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 4) {
								if (entityiterator instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
									_livingEntity21.getAttribute(CaerulaArborModAttributes.NUMB.get())
											.setBaseValue(((entityiterator instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get())
													? _livingEntity20.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue()
													: 0) + 2));
							} else {
								if (entityiterator instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
									_livingEntity23.getAttribute(CaerulaArborModAttributes.NUMB.get())
											.setBaseValue(((entityiterator instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get())
													? _livingEntity22.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue()
													: 0) + 1));
							}
						}
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.01));
					}
				}
			}
		}
	}
}
