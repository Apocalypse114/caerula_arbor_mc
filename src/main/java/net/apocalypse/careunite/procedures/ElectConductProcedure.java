package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class ElectConductProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amplifier) {
		if (entity == null)
			return;
		Entity player = null;
		double ang = 0;
		double daama = 0;
		double t = 0;
		entity.invulnerableTime = 0;
		ang = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ELECT_SPARK.get()), (x + 2 * Math.sin(ang)), (y + 0.8), (z + 2 * Math.cos(ang)), (int) Math.ceil(Math.min(amplifier * 0.5, 2)), 0.75, 0.75, 0.75, 0.2);
		if (ElectFuncGapProcedure.execute(amplifier, entity.tickCount)) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 5, 3, false, false));
			player = (Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			daama = Math.max(10, (player instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.75);
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.INDIRECT_MAGIC), player), (float) daama);
			if (amplifier > 0) {
				t = amplifier + 3;
				if (!entity.isAlive()) {
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(CaerulaArborModMobEffects.ELECTRIC_CONDUCTION.get());
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator == entity) {
								continue;
							}
							if (!(entityiterator instanceof LivingEntity)) {
								continue;
							}
							if (!entityiterator.isAlive()) {
								continue;
							}
							if (entityiterator instanceof Player) {
								continue;
							}
							if (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
								continue;
							}
							if (entityiterator instanceof LivingEntity _livEnt16 && _livEnt16.hasEffect(CaerulaArborModMobEffects.ELECTRIC_CONDUCTION.get())) {
								continue;
							}
							if (!(entityiterator instanceof Monster)) {
								if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Player || IsSameTypeProcedure.execute(entityiterator, entity))) {
									continue;
								}
							}
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ELECTRIC_CONDUCTION.get(), 300, (int) (amplifier - 1), false, false));
							if (world instanceof ServerLevel _level) {
								LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
								entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ())));
								entityToSpawn.setVisualOnly(true);
								_level.addFreshEntity(entityToSpawn);
							}
							t = t - 1;
							if (t <= 0) {
								break;
							}
						}
					}
				}
			}
		}
	}
}
