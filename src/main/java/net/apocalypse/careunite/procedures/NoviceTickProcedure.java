package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.NoviceAlchemistEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class NoviceTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp = 0;
		double dura = 0;
		if (entity.isAlive()) {
			sklp = entity instanceof NoviceAlchemistEntity _datEntI ? _datEntI.getEntityData().get(NoviceAlchemistEntity.DATA_SKILL_P) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (sklp > 0) {
				if (entity instanceof NoviceAlchemistEntity _datEntSetI)
					_datEntSetI.getEntityData().set(NoviceAlchemistEntity.DATA_SKILL_P, (int) (sklp - 1));
			} else {
				if (entity.tickCount % 5 == 0) {
					if (HasValidAlchemyProcedure.execute(world, x, y, z, entity)) {
						if (entity instanceof NoviceAlchemistEntity _datEntSetI)
							_datEntSetI.getEntityData().set(NoviceAlchemistEntity.DATA_SKILL_P, 400);
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false));
						if (entity instanceof NoviceAlchemistEntity) {
							((NoviceAlchemistEntity) entity).setAnimation("skill");
						}
						CaerulaArborMod.queueServerWork(22, () -> {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.NEUTRAL, 1,
											(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
								}
							}
							NoviceDoInjectBaseProcedure.execute(world, x, y, z, entity);
						});
					}
				}
			}
			dura = entity instanceof NoviceAlchemistEntity _datEntI ? _datEntI.getEntityData().get(NoviceAlchemistEntity.DATA_DURATION) : 0;
			if (dura > 0) {
				if (entity instanceof NoviceAlchemistEntity _datEntSetI)
					_datEntSetI.getEntityData().set(NoviceAlchemistEntity.DATA_DURATION, (int) (dura - 1));
			}
		}
	}
}
