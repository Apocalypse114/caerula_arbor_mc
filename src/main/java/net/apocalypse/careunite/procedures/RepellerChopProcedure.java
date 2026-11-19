package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Comparator;

public class RepellerChopProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double rate) {
		if (entity == null)
			return;
		Entity enemy = null;
		double num = 0;
		double sklp = 0;
		double dura = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.attack")), SoundSource.HOSTILE, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.attack")), SoundSource.HOSTILE, 2, 1, false);
			}
		}
		{
			final Vec3 _center = new Vec3((x + 1.8 * entity.getLookAngle().x), (y + 1.5), (z + 1.8 * entity.getLookAngle().z));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:repeller_attack"))), entity),
							(float) ((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate));
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 3));
					for (int index0 = 0; index0 < 2; index0++) {
						GiveLessArmorProcedure.execute(entityiterator, 18);
					}
				}
			}
		}
	}
}
