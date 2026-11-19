package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class SyncEchoFightBuffProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		final Vec3 _center = entity.position();
		List<Player> _entfound = world.getEntitiesOfClass(Player.class, new AABB(_center, _center).inflate(12), e -> e != entity);
		for (Player entityiterator : _entfound) {
			entityiterator.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ECHO_FIGHT.get(), 40, 0, false, false));
		}
	}
}
