package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModPotions;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class ThrowRandomPotionProcedure {
	public static void execute(LevelAccessor world, Entity obj, Entity tgt, double xx, double yy, double zz) {
		if (obj == null || tgt == null)
			return;
		double potion = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(xx, yy, zz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.witch.throw")), SoundSource.HOSTILE, 1, 1);
			} else {
				_level.playLocalSound(xx, yy, zz, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.witch.throw")), SoundSource.HOSTILE, 1, 1, false);
			}
		}
		potion = Mth.nextInt(RandomSource.create(), 0, 4);
		if (potion == 0) {
			if (tgt instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SANITY_HEAL.get(), 1, 2));
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getPotion(Level level, Entity shooter) {
						ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
						entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), CaerulaArborModPotions.SANITY_CURE.get()));
						entityToSpawn.setOwner(shooter);
						return entityToSpawn;
					}
				}.getPotion(projectileLevel, obj);
				_entityToSpawn.setPos(xx, yy, zz);
				_entityToSpawn.shoot(0, (-1), 0, 1, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		} else if (potion == 1) {
			if (tgt instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 2));
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getPotion(Level level, Entity shooter) {
						ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
						entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.HEALING));
						entityToSpawn.setOwner(shooter);
						return entityToSpawn;
					}
				}.getPotion(projectileLevel, obj);
				_entityToSpawn.setPos(xx, yy, zz);
				_entityToSpawn.shoot(0, (-1), 0, 1, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		} else if (potion == 2) {
			if (tgt instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 2));
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getPotion(Level level, Entity shooter) {
						ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
						entityToSpawn.setItem(PotionUtils.setPotion(Items.LINGERING_POTION.getDefaultInstance(), Potions.REGENERATION));
						entityToSpawn.setOwner(shooter);
						return entityToSpawn;
					}
				}.getPotion(projectileLevel, obj);
				_entityToSpawn.setPos(xx, yy, zz);
				_entityToSpawn.shoot(0, (-1), 0, 1, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		} else if (potion == 3) {
			if (tgt instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0));
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getPotion(Level level, Entity shooter) {
						ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
						entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.FIRE_RESISTANCE));
						entityToSpawn.setOwner(shooter);
						return entityToSpawn;
					}
				}.getPotion(projectileLevel, obj);
				_entityToSpawn.setPos(xx, yy, zz);
				_entityToSpawn.shoot(0, (-1), 0, 1, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		} else if (potion == 4) {
			if (tgt instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getPotion(Level level, Entity shooter) {
						ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
						entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.STRENGTH));
						entityToSpawn.setOwner(shooter);
						return entityToSpawn;
					}
				}.getPotion(projectileLevel, obj);
				_entityToSpawn.setPos(xx, yy, zz);
				_entityToSpawn.shoot(0, (-1), 0, 1, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
	}
}
