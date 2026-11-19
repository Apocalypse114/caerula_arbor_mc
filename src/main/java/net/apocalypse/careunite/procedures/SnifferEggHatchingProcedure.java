package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedSnifferEntity;
import net.apocalypse.careunite.entity.OceanizedSnifferEggEntity;

import java.util.Comparator;

public class SnifferEggHatchingProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double h = 0;
		Entity animal = null;
		Entity sniffer = null;
		h = entity instanceof OceanizedSnifferEggEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEggEntity.DATA_HATCH_TIME) : 0;
		if (h > 0) {
			if (entity instanceof OceanizedSnifferEggEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEggEntity.DATA_HATCH_TIME, (int) (h - 1));
		} else {
			animal = SummonRandonAnimalProcedure.execute(world, x, y, z);
			if (!(animal == null)) {
				if (animal instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get(), 999999, 0, false, false));
				if (animal instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 999999, 0, false, true));
				EmrelySyncTeamProcedure.execute(entity, animal);
			}
			if (!entity.level().isClientSide())
				entity.discard();
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.hatch")), SoundSource.NEUTRAL, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.hatch")), SoundSource.NEUTRAL, 2, 1, false);
				}
			}
			world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.PHLOEM_BLOCK.get().defaultBlockState()));
			sniffer = (Entity) world.getEntitiesOfClass(OceanizedSnifferEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (!(sniffer == null)) {
				LivingHeal1Procedure.execute(sniffer, (sniffer instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05);
				if (sniffer instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
					_livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
							((sniffer instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) + 1.5));
			}
		}
	}
}
