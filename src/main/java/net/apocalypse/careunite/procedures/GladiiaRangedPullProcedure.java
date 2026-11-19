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

public class GladiiaRangedPullProcedure {
	public static void execute(LevelAccessor world, Entity ene, Entity entity) {
		if (ene == null || entity == null)
			return;
		Entity side = null;
		double damage = 0;
		double d = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(ene.getX(), ene.getY(), ene.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pull")), SoundSource.NEUTRAL, 3, 1);
			} else {
				_level.playLocalSound((ene.getX()), (ene.getY()), (ene.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pull")), SoundSource.NEUTRAL, 3, 1, false);
			}
		}
		damage = entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		{
			final Vec3 _center = new Vec3((ene.getX()), (ene.getY()), (ene.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (IsValidEnemyForIreneProcedure.execute(entityiterator, entity)) {
					d = entityiterator != null ? ene.distanceTo(entityiterator) : -1;
					if (d <= 4) {
						PullToGladiiaProcedure.execute(entityiterator, entity);
						GladiiaLinkPtcToEntityProcedure.execute(world, entity, entityiterator);
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity),
								(float) (damage * 1.8));
					}
				}
			}
		}
	}
}
