package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.TellerShotEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class FirstTellerAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double ayk = 0;
		Entity enemy = null;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + -2.5), y, z, 64, 0.1, 0.2, 2, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + 2.5), y, z, 64, 0.1, 0.2, 2, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, (z + 2.5), 64, 2, 0.2, 0.1, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, (z + -2.5), 64, 2, 0.2, 0.1, 0.1);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 5, 1);
		if (!(world.getDifficulty() == Difficulty.PEACEFUL)) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firetteller_skill_attack")), SoundSource.NEUTRAL, 3,
							(float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firetteller_skill_attack")), SoundSource.NEUTRAL, 3, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15), false);
				}
			}
			enemy = sourceentity;
			if (!enemy.isAlive()) {
				return;
			}
			ayk = enemy instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
			entity.invulnerableTime = 0;
			for (Entity entityiterator : world.getEntities(entity, new AABB((x + 2.5), (y + 4), (z + 2.5), (x + -2.5), y, (z + -2.5)))) {
				if (entityiterator instanceof Player && (entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
					continue;
				}
				if (IsValidEnemyForSeabornProcedure.execute(entityiterator, sourceentity)) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
							(float) (ayk * 0.6));
					DeductPlayerSanityProcedure.execute(entityiterator, ayk * 60);
				}
			}
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
						AbstractArrow entityToSpawn = new TellerShotEntity(CaerulaArborModEntities.TELLER_SHOT.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setPierceLevel(piercing);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, sourceentity, (float) (ayk * 0.6), 0, (byte) 1);
				_entityToSpawn.setPos(x, (y + 8), z);
				_entityToSpawn.shoot((Mth.nextDouble(RandomSource.create(), -0.125, 0.125)), (-1), (Mth.nextDouble(RandomSource.create(), -0.125, 0.125)), 1, 5);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))), (float) (ayk * 0.6));
			DeductPlayerSanityProcedure.execute(entity, ayk * 60);
		}
	}
}
