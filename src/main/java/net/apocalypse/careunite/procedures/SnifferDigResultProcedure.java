package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.Comparator;

public class SnifferDigResultProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double res = 0;
		double detail = 0;
		double atk = 0;
		ItemStack result = ItemStack.EMPTY;
		String tag = "";
		res = Mth.nextInt(RandomSource.create(), 0, 2);
		atk = entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:animal_seaborn")))) {
					detail = Mth.nextInt(RandomSource.create(), 0, 2);
					if (res == 0) {
						if (detail == 0) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REGENERATION_PERCLY.get(), 40, 0, false, false));
						} else if (detail == 1) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 40, 0, false, false));
						} else if (detail == 2) {
							AddLivingBarrierProcedure.execute(entityiterator, atk * 2);
						}
					} else if (res == 1) {
						if (detail == 0) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COUNTER_BACK.get(), 200, 1, false, true));
						} else if (detail == 1) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 300, 0, false, false));
						} else if (detail == 2) {
							if (entityiterator instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
								_livingEntity9.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
										.setBaseValue(((entityiterator instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
												? _livingEntity8.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
												: 0) + 5));
						}
					} else if (res == 2) {
						if (detail == 0) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 400, 3, false, true));
						} else if (detail == 1) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 400, 29, false, false));
						} else if (detail == 2) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_RESIS_TINY.get(), 400, 4, false, false));
						}
					}
				}
			}
		}
		if (res == 0) {
			tag = "caerula_arbor_reunite:sniffer_result_1";
		} else if (res == 1) {
			tag = "caerula_arbor_reunite:sniffer_result_2";
		} else if (res == 2) {
			tag = "caerula_arbor_reunite:sniffer_result_3";
		}
		result = new ItemStack((ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation((tag).toLowerCase(java.util.Locale.ENGLISH)))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR))).copy();
		if (world instanceof ServerLevel _level) {
			ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, result);
			entityToSpawn.setPickUpDelay(10);
			_level.addFreshEntity(entityToSpawn);
		}
	}
}
