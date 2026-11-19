package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

import java.util.List;
import java.util.Comparator;

public class TrailriteArrowRangedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity, double amount, double range) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		Entity enemy = null;
		Entity target = null;
		double damage = 0;
		double r = 0;
		double h = 0;
		double d = 0;
		double angle = 0;
		double tx = 0;
		double tz = 0;
		double ty = 0;
		double h1 = 0;
		double d1 = 0;
		r = range;
		enemy = (sourceentity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
		enemy = (sourceentity instanceof LivingEntity _entity) ? _entity.getLastHurtMob() : null;
		damage = amount;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:crossbow_super_hit")), SoundSource.PLAYERS, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:crossbow_super_hit")), SoundSource.PLAYERS, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((4 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (!(entityiterator instanceof Monster)) {
					if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity)) {
						continue;
					}
				}
				if (entityiterator instanceof Player || (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
					continue;
				}
				if (entityiterator == sourceentity) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 2 * r) {
					if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= r) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_firework"))),
								immediatesourceentity, sourceentity), (float) damage);
						DeductPlayerSanityProcedure.execute(entityiterator, 2 * damage);
					} else {
						PullToMEProcedure.execute(entityiterator, entity);
					}
				}
			}
		}
		d = 2 * r;
		h = Math.toRadians(Mth.nextInt(RandomSource.create(), -10, 0));
		h1 = Math.toRadians(Mth.nextInt(RandomSource.create(), 0, 5));
		for (int index0 = 0; index0 < 120; index0++) {
			angle = Math.toRadians(index0 * 3);
			d1 = d * Math.sin(angle);
			tx = x + d1 * Math.cos(h);
			ty = y + 0.5 + d1 * Math.sin(h);
			tz = z + d * Math.cos(angle);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), tx, ty, tz, 2, 0, 0, 0, 0.1);
			d1 = r * Math.sin(angle);
			tx = x + d1 * Math.cos(h1);
			ty = y + 0.5 + d1 * Math.sin(h1);
			tz = z + r * Math.cos(angle);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), tx, ty, tz, 1, 0, 0, 0, 0.1);
		}
	}
}
