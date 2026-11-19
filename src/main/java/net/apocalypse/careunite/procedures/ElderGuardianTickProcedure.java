package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
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
import net.apocalypse.careunite.entity.PromotedElderGuardianEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class ElderGuardianTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double eye = 0;
		double damage = 0;
		double lockT = 0;
		double sklp = 0;
		double dura = 0;
		if (entity.isAlive()) {
			lockT = entity instanceof PromotedElderGuardianEntity _datEntI ? _datEntI.getEntityData().get(PromotedElderGuardianEntity.DATA_LOCK_TICK) : 0;
			dura = entity instanceof PromotedElderGuardianEntity _datEntI ? _datEntI.getEntityData().get(PromotedElderGuardianEntity.DATA_DURATION) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (dura > 0) {
				if (entity instanceof PromotedElderGuardianEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedElderGuardianEntity.DATA_DURATION, (int) (dura - 1));
			}
			if (lockT > 0) {
				if (entity instanceof PromotedElderGuardianEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedElderGuardianEntity.DATA_LOCK_TICK, (int) (lockT - 1));
				if (!(enemy == null) && enemy.isAlive()) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
					if (lockT == 39) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.attack")), SoundSource.HOSTILE, (float) 2.2,
										(float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.attack")), SoundSource.HOSTILE, (float) 2.2, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15), false);
							}
						}
					}
					eye = entity instanceof PromotedElderGuardianEntity _datEntI ? _datEntI.getEntityData().get(PromotedElderGuardianEntity.DATA_EXTRA_EYE) : 0;
					damage = entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
					if (lockT == 10) {
						if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 6, 9, false, false));
						enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
								(float) (damage * 0.3));
						enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), entity),
								(float) (damage * 0.7));
						if (enemy instanceof LivingEntity _livEnt21 && _livEnt21.hasEffect(MobEffects.DIG_SLOWDOWN)) {
							enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_real"))), entity),
									(float) (damage * 0.25));
						}
						if (eye >= 4 && enemy instanceof Player) {
							if (enemy instanceof Player _player)
								_player.getCooldowns().addCooldown((enemy instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem(), 300);
							if (enemy instanceof Player _player)
								_player.getCooldowns().addCooldown((enemy instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem(), 300);
							ShieldBreakProcedure.execute(world, x, y, z, enemy, 300);
						}
					}
					for (int index0 = 0; index0 < (int) eye; index0++) {
						if (lockT == 9 - index0) {
							if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 6, 9, false, false));
							enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
									(float) (damage * 0.15));
							enemy.hurt(
									new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), entity),
									(float) (damage * 0.35));
							if (enemy instanceof LivingEntity _livEnt34 && _livEnt34.hasEffect(MobEffects.DIG_SLOWDOWN)) {
								enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_real"))), entity),
										(float) (damage * 0.125));
							}
						}
					}
				}
			}
			sklp = entity instanceof PromotedElderGuardianEntity _datEntI ? _datEntI.getEntityData().get(PromotedElderGuardianEntity.DATA_SKILL_P) : 0;
			if (sklp > 0) {
				if (entity instanceof PromotedElderGuardianEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedElderGuardianEntity.DATA_SKILL_P, (int) (sklp - 1));
			} else if (!(enemy == null) && enemy.isAlive()) {
				if ((lockT > 20 || lockT <= 0) && (enemy != null ? entity.distanceTo(enemy) : -1) <= 20) {
					if (entity instanceof PromotedElderGuardianEntity) {
						((PromotedElderGuardianEntity) entity).setAnimation("skill");
					}
					if (entity instanceof PromotedElderGuardianEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedElderGuardianEntity.DATA_LOCK_TICK, 0);
					if (entity instanceof PromotedElderGuardianEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedElderGuardianEntity.DATA_DURATION, 23);
					if (entity instanceof PromotedElderGuardianEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedElderGuardianEntity.DATA_SKILL_P, 723);
					CaerulaArborMod.queueServerWork(16, () -> {
						GuardianRangedBuffProcedure.execute(world, x, y, z, entity);
					});
				}
			}
		}
	}
}
