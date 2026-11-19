package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.IsharmlaTearEntity;
import net.apocalypse.careunite.entity.IsharmlaEntity;

import java.util.List;
import java.util.Comparator;

public class IsharmlaToMonsterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double mH = 0;
		double cH = 0;
		if (!(entity instanceof IsharmlaEntity _datEntL0 && _datEntL0.getEntityData().get(IsharmlaEntity.DATA_IS_MONSTER))) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_to_monsterr")), SoundSource.HOSTILE, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_to_monsterr")), SoundSource.HOSTILE, 3, 1, false);
				}
			}
			IsharmlaToMStyleProcedure.execute(world, x, y, z);
			cH = entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
			mH = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
			if (entity instanceof IsharmlaEntity) {
				((IsharmlaEntity) entity).setAnimation("animation.isharmla.to_monster");
			}
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 40);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_1, 200);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_2, 100);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_RECORDED_HEALTH, (int) ((cH / mH) * 1000));
			if (entity instanceof IsharmlaEntity _datEntSetL)
				_datEntSetL.getEntityData().set(IsharmlaEntity.DATA_IS_MONSTER, true);
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) mH);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 40, 9, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 2, false, false));
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof IsharmlaTearEntity) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))),
								114514);
					}
				}
			}
		}
	}
}
