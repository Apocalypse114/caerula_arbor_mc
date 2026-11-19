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
import net.apocalypse.careunite.entity.TombstoneStultiferaSmallEntity;

public class OnSmallTombstoneEatenProcedure {
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
		eater.getPersistentData().putBoolean("caerulaTombEatenSmall", true);
		if (entity instanceof TombstoneStultiferaSmallEntity) {
			V = entity instanceof TombstoneStultiferaSmallEntity _datEntS ? _datEntS.getEntityData().get(TombstoneStultiferaSmallEntity.DATA_Varint) : "";
			if ((V).equals("predator")) {
				if (eater instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
					_livingEntity18.getAttribute(CaerulaArborModAttributes.MISSRATE.get())
							.setBaseValue(((eater instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get())
									? _livingEntity17.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).getBaseValue()
									: 0) + 25));
			} else if ((V).equals("spewer")) {
				if (eater instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SPLASHER_ATTACK.get(), 400, 0, false, false));
			}
		}
	}
}
