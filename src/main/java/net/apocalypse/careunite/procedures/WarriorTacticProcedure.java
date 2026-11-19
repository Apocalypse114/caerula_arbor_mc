package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.WarriorPriestEntity;
import net.apocalypse.careunite.entity.TyphoeusEntity;
import net.apocalypse.careunite.entity.JuniorWarriorPriestEntity;
import net.apocalypse.careunite.entity.IshmaelEntity;
import net.apocalypse.careunite.entity.IreneEntity;
import net.apocalypse.careunite.entity.CorrectionalPhalanxyInfantryEntity;
import net.apocalypse.careunite.entity.CorrectinalPhalaxVanguardEntity;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class WarriorTacticProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		double cosine = 0;
		double less = 0;
		double rate = 0;
		double llll = 0;
		if (IsSeabornProcedure.execute(entity) && sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:warriors")))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 100, 0, false, false));
		}
		if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.SANITY_COUNTER_BACK.get())) {
			DeductPlayerSanityProcedure.execute(sourceentity, amount * 20);
		}
		if (entity instanceof JuniorWarriorPriestEntity) {
			if (GetCosineProcedure.execute(sourceentity.getX() - entity.getX(), entity.getLookAngle().x, sourceentity.getZ() - entity.getZ(), entity.getLookAngle().z) >= 0.5) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1, false);
					}
				}
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * 0.6));
				}
			}
		} else if (entity instanceof WarriorPriestEntity) {
			if (GetCosineProcedure.execute(sourceentity.getX() - entity.getX(), entity.getLookAngle().x, sourceentity.getZ() - entity.getZ(), entity.getLookAngle().z) >= 0.5) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1, false);
					}
				}
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * 0.5));
				}
			}
		} else if (entity instanceof CorrectionalPhalanxyInfantryEntity) {
			rate = 1;
			less = 1;
			if (GetCosineProcedure.execute(sourceentity.getX() - entity.getX(), entity.getLookAngle().x, sourceentity.getZ() - entity.getZ(), entity.getLookAngle().z) >= 0.5) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1, false);
					}
				}
				rate = 0.5;
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator == entity) {
						continue;
					}
					if (IsBatalionSuitProcedure.execute(entityiterator)) {
						less = less - 0.06;
					}
					if (less <= 0.4) {
						break;
					}
				}
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * rate * less));
			}
		} else if (entity instanceof IreneEntity || entity instanceof IshmaelEntity || entity instanceof TyphoeusEntity) {
			less = 1;
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator == entity) {
						continue;
					}
					if (IsBatalionSuitProcedure.execute(entityiterator)) {
						less = less - 0.06;
					}
					if (less <= 0.4) {
						break;
					}
				}
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * less));
			}
		} else if (entity instanceof CorrectinalPhalaxVanguardEntity) {
			if (GetCosineProcedure.execute(sourceentity.getX() - entity.getX(), entity.getLookAngle().x, sourceentity.getZ() - entity.getZ(), entity.getLookAngle().z) <= -0.5) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.block")), SoundSource.HOSTILE, (float) 0.75, 1, false);
					}
				}
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * 0.5));
				}
			}
		} else if (entity instanceof Player) {
			cosine = IsInquisitionSuitProcedure.execute(entity);
			rate = 1;
			if (cosine > 1.5) {
				llll = 0.4;
				if (IsSeabornProcedure.execute(sourceentity)) {
					rate = 0.65;
				}
			} else if (cosine > 0.5) {
				llll = 0.64;
				if (IsSeabornProcedure.execute(sourceentity)) {
					rate = 0.8;
				}
			}
			if (cosine > 0.5) {
				less = 1;
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator == entity) {
							continue;
						}
						if (IsBatalionSuitProcedure.execute(entityiterator)) {
							less = less - 0.06;
						}
						if (less <= llll) {
							less = llll;
							break;
						}
					}
				}
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * rate * less));
				}
			}
		}
	}
}
