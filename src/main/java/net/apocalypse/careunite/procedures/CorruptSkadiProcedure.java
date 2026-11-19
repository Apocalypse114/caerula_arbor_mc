package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.SkadiEntity;

import javax.annotation.Nullable;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CorruptSkadiProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		execute(null, world, x, y, z, damagesource, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		double p = 0;
		if (damagesource.is(DamageTypes.GENERIC_KILL)) {
			return;
		}
		if (!event.isCanceled() && entity instanceof SkadiEntity) {
			if (damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:isharmla_cursed")))) {
				for (Entity entityiterator : new ArrayList<>(world.players())) {
					if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 32) {
						if (entityiterator instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("entity.caerula_arbor_reunite.skadi_corrupted.start").getString())), false);
					}
				}
				if (!entity.level().isClientSide())
					entity.discard();
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 1), z, 5, 0, 0, 0, 0.1);
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.SKADI_CORRUPTED.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else {
				p = entity instanceof SkadiEntity _datEntI ? _datEntI.getEntityData().get(SkadiEntity.DATA_phase) : 0;
				if (p == 0) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof SkadiEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SkadiEntity.DATA_phase, 1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_talk")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_talk")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 100, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 100, 3, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 131071, 1, false, true));
					if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity18.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity17.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 0.75));
					if (entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
						_livingEntity20.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
								((entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity19.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.5));
					if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Attributes.ARMOR))
						_livingEntity22.getAttribute(Attributes.ARMOR)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity21.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 2));
				} else if (p == 1) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof SkadiEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SkadiEntity.DATA_phase, 2);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_talk")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_talk")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 100, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 100, 3, false, false));
					if (entity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity28.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity27.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 0.8));
					if (entity instanceof LivingEntity _livingEntity30 && _livingEntity30.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
						_livingEntity30.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
								((entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity29.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.25));
					if (entity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(Attributes.ARMOR))
						_livingEntity32.getAttribute(Attributes.ARMOR)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity31.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 2));
				}
			}
		}
	}
}
