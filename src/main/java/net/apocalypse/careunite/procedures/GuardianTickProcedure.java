package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.PromotedGuardianEntity;

public class GuardianTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double lockT = 0;
		double damage = 0;
		double eye = 0;
		if (entity.isAlive()) {
			lockT = entity instanceof PromotedGuardianEntity _datEntI ? _datEntI.getEntityData().get(PromotedGuardianEntity.DATA_LOCK_TICK) : 0;
			if (lockT > 0) {
				if (entity instanceof PromotedGuardianEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedGuardianEntity.DATA_LOCK_TICK, (int) (lockT - 1));
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null) && enemy.isAlive()) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
					if (lockT == 29) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.attack")), SoundSource.HOSTILE, 2,
										(float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.attack")), SoundSource.HOSTILE, 2, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15), false);
							}
						}
					}
					if (lockT <= 20 && lockT % 2 == 1) {
						eye = entity instanceof PromotedGuardianEntity _datEntI ? _datEntI.getEntityData().get(PromotedGuardianEntity.DATA_EXTRA_EYE) : 0;
						damage = (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity13.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * (1 + eye * 0.4);
						if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 6, 9, false, false));
						enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
								(float) (damage * 0.04));
						enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), entity),
								(float) (damage * 0.16));
					}
				}
			}
		}
	}
}
