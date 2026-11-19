package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class LaydownRunFishProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_fish")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_fish")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
		if (world instanceof ServerLevel _level) {
		    EntityType<?> toBeSummoned = null;
		    Item item = itemstack.getItem();
			if (item == CaerulaArborModItems.BUCKET_RUNFISH.get()){
				toBeSummoned = CaerulaArborModEntities.RUN_FISH.get();
			} else if (item == CaerulaArborModItems.BUCKET_CHISELER.get()){
				toBeSummoned = CaerulaArborModEntities.CHISELER_FISH.get();
			} else if (item == CaerulaArborModItems.BUCKET_FLOATER.get()){
				toBeSummoned = CaerulaArborModEntities.FLOATER_PROKARYOTE.get();
			}
 else if (item == CaerulaArborModItems.BUCKET_BONEFISH.get()){
				toBeSummoned = CaerulaArborModEntities.BONE_FISH.get();
			} else if (item == CaerulaArborModItems.BUCKET_COLLECTOR.get()){
				toBeSummoned = CaerulaArborModEntities.COLLECTOR_PROKARYOTE.get();
			} else if (item == CaerulaArborModItems.BUCKET_SLIDER.get()) {
				toBeSummoned = CaerulaArborModEntities.SLIDER_FISH.get();
			}
			Entity entityToSpawn = toBeSummoned.spawn(_level, 
				BlockPos.containing(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ()),
				MobSpawnType.BUCKET);
		}
		itemstack.shrink(1);
		if (entity instanceof Player _player) {
			ItemStack _setstack = new ItemStack(Items.BUCKET).copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
		return InteractionResult.SUCCESS;
	}
}
