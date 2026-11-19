package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

public class RunvolutionLaserProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity, Entity target, boolean shouldAttackTwo) {
		if (sourceentity == null || target == null)
			return;
		double dada = 0;
		Entity subTarget1 = null;
		Entity subtarget2 = null;
		Entity anotherTarget0 = null;
		Entity anothertarget1 = null;
		Entity anotherTarget2 = null;
		dada = sourceentity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		LinkDensePtcToEntityProcedure.execute(world, x, y, z, target);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_attack")), SoundSource.HOSTILE, (float) 2.5,
						(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_attack")), SoundSource.HOSTILE, (float) 2.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
			}
		}
		target.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), sourceentity), (float) dada);
		subTarget1 = FindRunvolutionEnemyProcedure.execute(world, sourceentity, target, target, target);
		if (!(subTarget1 == null)) {
			LinkDensePtcToEntityProcedure.execute(world, target.getX(), (target.getY() + target.getBbHeight() * 0.5) - 4, target.getZ(), subTarget1);
			subTarget1.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), sourceentity),
					(float) (dada * 0.9));
			subtarget2 = FindRunvolutionEnemyProcedure.execute(world, sourceentity, target, subTarget1, subTarget1);
			if (!(subtarget2 == null)) {
				LinkDensePtcToEntityProcedure.execute(world, subTarget1.getX(), (subTarget1.getY() + subTarget1.getBbHeight() * 0.5) - 4, subTarget1.getZ(), subtarget2);
				subtarget2.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), sourceentity),
						(float) (dada * 0.81));
			}
		}
		if (shouldAttackTwo) {
			anotherTarget0 = FindWideRnvlutionEnemyProcedure.execute(world, sourceentity, target, target, sourceentity);
			if (!(anotherTarget0 == null)) {
				LinkDensePtcToEntityProcedure.execute(world, x, y, z, anotherTarget0);
				anotherTarget0.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), sourceentity),
						(float) (dada * 1));
				anothertarget1 = FindRunvolutionEnemyProcedure.execute(world, sourceentity, anotherTarget0, anotherTarget0, anotherTarget0);
				if (!(anothertarget1 == null)) {
					LinkDensePtcToEntityProcedure.execute(world, anotherTarget0.getX(), (anotherTarget0.getY() + anotherTarget0.getBbHeight() * 0.5) - 4, anotherTarget0.getZ(), anothertarget1);
					anothertarget1.hurt(
							new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), sourceentity),
							(float) (dada * 0.9));
					anotherTarget2 = FindRunvolutionEnemyProcedure.execute(world, sourceentity, anotherTarget0, anothertarget1, anothertarget1);
					if (!(anotherTarget2 == null)) {
						LinkDensePtcToEntityProcedure.execute(world, anothertarget1.getX(), (anothertarget1.getY() + anothertarget1.getBbHeight() * 0.5) - 4, anothertarget1.getZ(), anotherTarget2);
						anotherTarget2.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), sourceentity),
								(float) (dada * 0.81));
					}
				}
			}
		}
	}
}
