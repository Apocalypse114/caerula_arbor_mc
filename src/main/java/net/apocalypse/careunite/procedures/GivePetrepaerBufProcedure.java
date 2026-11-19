package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.ReaperPetEntity;

import java.util.List;
import java.util.Comparator;

public class GivePetrepaerBufProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity owner = null;
		Entity enemy = null;
		double angle = 0;
		double atk = 0;
		if ((entity instanceof ReaperPetEntity _datEntI ? _datEntI.getEntityData().get(ReaperPetEntity.DATA_state) : 0) == 2) {
			if (entity instanceof Mob _entity)
				_entity.setTarget(null);
		}
		owner = entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (enemy == owner || (enemy instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == owner) {
			if (entity instanceof Mob _entity)
				_entity.setTarget(null);
		}
		if (entity.tickCount % 15 == 0) {
			if (!(enemy == null) && enemy.isAlive() && entity.isAlive()) {
				if (!(owner == null)) {
					if (owner instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get(), 20, 0));
				}
				atk = entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
				for (int index0 = 0; index0 < 120; index0++) {
					angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), (x + 4 * Math.sin(angle)), y, (z + 4 * Math.cos(angle)), 8, 0.1, 0.1, 0.1, 0.2);
				}
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!(entityiterator instanceof LivingEntity)) {
							continue;
						}
						if (entityiterator == entity) {
							continue;
						}
						if (IsSameTeamProcedure.execute(entity, entityiterator)) {
							continue;
						}
						if (!(entityiterator instanceof Monster)) {
							if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == owner || enemy == entityiterator)) {
								continue;
							}
						}
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 4) {
							if (owner == entityiterator) {
								RevivesPlayerSanityProcedure.execute(owner, atk * 12);
								LivingHeal1Procedure.execute(owner, atk);
							} else {
								DeductPlayerSanityProcedure.execute(entityiterator, atk * 12);
							}
						}
					}
				}
			}
		}
	}
}
