package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.TombstoneUndertidesEntity;
import net.apocalypse.careunite.entity.TombstoneStultiferaEntity;

public class OnTombstoneEatenProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity eater, Entity entity) {
		if (eater == null || entity == null)
			return;
		double hhh = 0;
		double atk = 0;
		String V = "";
		eater.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x, y, z));
		hhh = entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity1.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0;
		atk = entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0;
		if (eater instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
			_livingEntity5.getAttribute(Attributes.MAX_HEALTH)
					.setBaseValue(Math.min((eater instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity3.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) + hhh,
							(eater instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity4.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 2));
		if (eater instanceof LivingEntity _entity)
			_entity.setHealth((float) ((eater instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + hhh));
		if (eater instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
			_livingEntity10.getAttribute(Attributes.ATTACK_DAMAGE)
					.setBaseValue(Math.min((eater instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) + atk,
							(eater instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 2));
		if (!entity.level().isClientSide())
			entity.discard();
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, (y + 0.5), z, 16, 0.5, 0.5, 0.5, 0.1);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.HOSTILE, (float) 1.5, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.HOSTILE, (float) 1.5, 1, false);
			}
		}
		eater.getPersistentData().putBoolean("caerulaTombEaten", true);
		if (entity instanceof TombstoneUndertidesEntity) {
			V = entity instanceof TombstoneUndertidesEntity _datEntS ? _datEntS.getEntityData().get(TombstoneUndertidesEntity.DATA_Varint) : "";
			if ((V).equals("reaper")) {
				if (eater instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
					_livingEntity18.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							.setBaseValue(((eater instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
									? _livingEntity17.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
									: 0) + 4));
				if (eater instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
					_livingEntity20.getAttribute(Attributes.ATTACK_DAMAGE)
							.setBaseValue(((eater instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity19.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.1));
			} else if ((V).equals("creeper")) {
				if (eater instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COUNTER_BACK.get(), 800, 0, false, false));
			} else if ((V).equals("piercer")) {
				if (eater instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
					_livingEntity23.getAttribute(Attributes.ATTACK_DAMAGE)
							.setBaseValue(((eater instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity22.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.25));
			}
		} else if (entity instanceof TombstoneStultiferaEntity) {
			V = entity instanceof TombstoneStultiferaEntity _datEntS ? _datEntS.getEntityData().get(TombstoneStultiferaEntity.DATA_Varint) : "";
			if ((V).equals("umbrella")) {
				if (eater instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
					_livingEntity27.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							.setBaseValue(((eater instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
									? _livingEntity26.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
									: 0) + 25));
				if (eater instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), 2000, 0, false, false));
			} else if ((V).equals("brandguider")) {
				if (eater instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.GUIDE_PATH_AHEAD.get(), 300, 0, false, false));
			} else if ((V).equals("reefbreaker")) {
				if (eater instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 300, 4, false, false));
			}
		}
	}
}
