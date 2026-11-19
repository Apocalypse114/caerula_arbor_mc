package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import javax.annotation.Nullable;

import java.util.Comparator;

@Mod.EventBusSubscriber
public class CorruptedBurdenDamageProcedure {
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
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		Entity skadiCorrupted = null;
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
			if (entity instanceof SkadiCorruptedEntity) {
				return;
			}
			if (amount > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
				return;
			}
			skadiCorrupted = (Entity) world.getEntitiesOfClass(SkadiCorruptedEntity.class, AABB.ofSize(new Vec3(x, y, z), 32, 32, 32), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (skadiCorrupted == null) {
				return;
			}
			if ((skadiCorrupted != null ? entity.distanceTo(skadiCorrupted) : -1) > 16) {
				return;
			}
			if (skadiCorrupted == entity) {
				return;
			}
			if (skadiCorrupted == sourceentity) {
				return;
			}
			if (entity == (skadiCorrupted instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) {
				return;
			}
			if ((skadiCorrupted instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0) < 0.5) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * 0.5));
				}
				skadiCorrupted.hurt(damagesource, (float) (amount * 0.5));
			}
		}
	}
}
