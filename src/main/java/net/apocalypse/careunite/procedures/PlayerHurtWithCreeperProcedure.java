package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.entity.CreeperPetEntity;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import net.minecraft.world.entity.player.Player;

@Mod.EventBusSubscriber
public class PlayerHurtWithCreeperProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null)
			return;
		int number = 0;
		if (sourceentity == entity) {
			return;
		}
		if (!(entity instanceof Player)) return;
		Vec3 _center = new Vec3(x, y, z);
		List<CreeperPetEntity> _entfound = world.getEntitiesOfClass(CreeperPetEntity.class, new AABB(_center, _center).inflate(48 / 2d), e -> e.isAlive());
		List<CreeperPetEntity> candidates = new ArrayList<>();
		for (CreeperPetEntity entityiterator : _entfound) {
			if (entityiterator.getOwner() == entity) {
				number++;
				candidates.add(entityiterator);
			}
		}
		if (number > 0) {
			double amountPerPet = (amount * 0.5) / number;
			double newAmount = amount;
			for (CreeperPetEntity entityiterator : candidates) {
				if (amountPerPet < entityiterator.getMaxHealth()) {
					entityiterator.hurt(damagesource, (float) amountPerPet);
					newAmount = newAmount - amountPerPet;
				}
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) newAmount);
			}
		}
	}
}
