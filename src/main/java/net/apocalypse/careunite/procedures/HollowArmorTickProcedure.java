package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.HollowArmorEntity;

public class HollowArmorTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp = 0;
		double atk = 0;
		double dura = 0;
		double base = 0;
		if (!world.isClientSide()) {
			base = GetBaseHeldProcedure.execute(entity, false, 0);
			if (entity instanceof HollowArmorEntity _datEntSetI)
				_datEntSetI.getEntityData().set(HollowArmorEntity.DATA_BASE_HELD, (int) base);
		}
		if (entity.isAlive()) {
			if (base > 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 5, (int) (base - 1), false, false));
			}
			dura = entity instanceof HollowArmorEntity _datEntI ? _datEntI.getEntityData().get(HollowArmorEntity.DATA_DURATION) : 0;
			if (dura > 0) {
				if (entity instanceof HollowArmorEntity _datEntSetI)
					_datEntSetI.getEntityData().set(HollowArmorEntity.DATA_DURATION, (int) (dura - 1));
			}
			sklp = entity instanceof HollowArmorEntity _datEntI ? _datEntI.getEntityData().get(HollowArmorEntity.DATA_SKILL_P) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (sklp > -400) {
				if (sklp > 0) {
					if (sklp % 5 == 2) {
						atk = entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
						HollowArmorSpinProcedure.execute(world, x, y, z, entity, 0.4 * atk, 6);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					}
				}
				if (entity instanceof HollowArmorEntity _datEntSetI)
					_datEntSetI.getEntityData().set(HollowArmorEntity.DATA_SKILL_P, (int) (sklp - 1));
			} else {
				if (!(enemy == null) && enemy.isAlive() && base > 4 && (enemy != null ? entity.distanceTo(enemy) : -1) <= 5.5) {
					if (entity instanceof HollowArmorEntity _datEntSetI)
						_datEntSetI.getEntityData().set(HollowArmorEntity.DATA_SKILL_P, 140);
				}
			}
		}
	}
}
