package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SkeletonSlashEntity;
import net.apocalypse.careunite.entity.RunvolutionWaveEntity;
import net.apocalypse.careunite.entity.RaidSignEntity;
import net.apocalypse.careunite.entity.OceanizedEndermanEntity;
import net.apocalypse.careunite.entity.MartusEntity;
import net.apocalypse.careunite.entity.IzumikOffspringEntity;

public class AngerOfTidesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double minDist = 0;
		Entity enemy = null;
		Entity curEnemy = null;
		minDist = 999;
		if (entity instanceof IzumikOffspringEntity) {
			return;
		}
		if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.SEABORN_DECREASE.get())) {
			return;
		}
		curEnemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(curEnemy == null) && curEnemy.isAlive()) {
			return;
		}
		if (!(entity instanceof MartusEntity)) {
			for (Entity entityiterator : world.getEntities(entity, new AABB((x + 32), (y + 12), (z + 32), (x - 32), (y - 9), (z - 32)))) {
				if (entity.isInWater() ^ entityiterator.isInWater()) {
					continue;
				}
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if (entityiterator instanceof RaidSignEntity) {
					continue;
				}
				if (entityiterator instanceof RunvolutionWaveEntity) {
					continue;
				}
				if (entityiterator instanceof SkeletonSlashEntity) {
					continue;
				}
				if ((entity.level().dimension()) == Level.END && entityiterator instanceof OceanizedEndermanEntity) {
					continue;
				}
				if (IsSeabornProcedure.execute(entityiterator)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:sea_friend")))) {
					continue;
				}
				if (entityiterator instanceof Player) {
					continue;
				}
				if (entityiterator instanceof LivingEntity _livEnt18 && _livEnt18.isBaby()) {
					continue;
				}
				if (entity instanceof LivingEntity _livEnt19 && _livEnt19.getMobType() == MobType.UNDEAD && entityiterator instanceof LivingEntity _livEnt20 && _livEnt20.getMobType() == MobType.UNDEAD) {
					continue;
				}
				if (entityiterator.getPersistentData().getBoolean("seabornForgive")) {
					continue;
				}
				if (IsSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < minDist) {
					minDist = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
					enemy = entityiterator;
				}
			}
			if (!(enemy == null)) {
				if (entity instanceof Mob _entity && enemy instanceof LivingEntity _ent)
					_entity.setTarget(_ent);
			}
		}
	}
}
