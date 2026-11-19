package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.SkadiEntity;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import java.util.Comparator;

public class IsharmlaRemainActivateProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double stat = 0;
		Entity skadi = null;
		if (!world.getEntitiesOfClass(SkadiCorruptedEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).isEmpty()) {
			return InteractionResult.PASS;
		}
		stat = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip3 ? blockstate.getValue(_getip3) : -1;
		if (stat == 0) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.WHIRL_EYE.get()
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.CAERULA_HEART.get()) {
				skadi = (Entity) world.getEntitiesOfClass(SkadiEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
				if (skadi == null) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.isharmla_remain.fail").getString())), true);
					return InteractionResult.FAIL;
				}
				if (skadi instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ISHARMLA_CURSE.get(), 99999, 0));
				IsharmlaLinkPtcToEntityProcedure.execute(world, x, y, z, skadi);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_place")), SoundSource.BLOCKS, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_place")), SoundSource.BLOCKS, 3, 1, false);
					}
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:unlock_calamity"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
				for (int index0 = 0; index0 < 3; index0++) {
					for (int index1 = 0; index1 < 3; index1++) {
						for (int index2 = 0; index2 < 3; index2++) {
							{
								int _value = 1;
								BlockPos _pos = BlockPos.containing(x + index0 - 1, y + index1 - 1, z + index2 - 1);
								BlockState _bs = world.getBlockState(_pos);
								if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
									world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
							}
						}
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.isharmla_remain.notice").getString())), true);
				return InteractionResult.PASS;
			}
		} else {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.TEAR_ISHARMLA.get()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.BLOCKS, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.BLOCKS, 3, 1, false);
					}
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, (x + 0.5), (y + 0.5), (z + 0.5), 24, 1, 1, 1, 0.1);
				for (int index3 = 0; index3 < 3; index3++) {
					for (int index4 = 0; index4 < 3; index4++) {
						for (int index5 = 0; index5 < 3; index5++) {
							{
								int _value = 0;
								BlockPos _pos = BlockPos.containing(x + index3 - 1, y + index4 - 1, z + index5 - 1);
								BlockState _bs = world.getBlockState(_pos);
								if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
									world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
							}
						}
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.isharmla_remain.reset").getString())), true);
				return InteractionResult.PASS;
			}
		}
		return InteractionResult.PASS;
	}
}
