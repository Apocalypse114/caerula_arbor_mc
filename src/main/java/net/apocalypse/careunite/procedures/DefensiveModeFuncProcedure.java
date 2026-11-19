package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class DefensiveModeFuncProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double minDist = 0;
		Entity enemy = null;
		Entity curEnemy = null;
		if (entity.tickCount % 30 == 15 && world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.DEFENSIVE_MODE)) {
			minDist = 999;
			curEnemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(curEnemy == null) && curEnemy.isAlive()) {
				return;
			}
			if (IsSeaFrientProcedure.execute(entity)) {
				return;
			}
			if (IsSeabornProcedure.execute(entity)) {
				return;
			}
			for (Entity entityiterator : world.getEntities(entity, new AABB((x + 32), (y + 12), (z + 32), (x - 32), (y - 9), (z - 32)))) {
				if (entity.isInWater() ^ entityiterator.isInWater()) {
					continue;
				}
				if (!(entityiterator instanceof Monster)) {
					continue;
				}
				if (IsSeabornProcedure.execute(entityiterator)) {
					if (IsSeabornPetProcedure.execute(entityiterator)) {
						continue;
					}
					if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < minDist) {
						minDist = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
						enemy = entityiterator;
					}
				}
			}
			if (!(enemy == null)) {
				if (entity instanceof Mob _entity && enemy instanceof LivingEntity _ent)
					_entity.setTarget(_ent);
			}
		}
	}
}
