
package net.apocalypse.careunite.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.procedures.PlayerEatFuncProcedure;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.apocalypse.careunite.AlchemyCreation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import java.util.Map;
import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ReinforcedInjectorItem extends AlchemistInjectorItem {
	public ReinforcedInjectorItem() {
		super(new Item.Properties().rarity(Rarity.UNCOMMON), true);
	}

	public static final ResourceLocation adv = new ResourceLocation("caerula_arbor_reunite:manual_livelihood");

	public static final Map<Block, Pair<Integer, EntityType<?>>> extraInjections = new HashMap<>();

	@Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand
    ) {
        if (pTarget.isAlive() && pTarget instanceof AlchemyCreation.InjectableEntity cc){
            if (pPlayer instanceof ServerPlayer serverPlayer && pStack.getDamageValue() < pStack.getMaxDamage()){
            	int type = cc.injectionType();
            	if (type < 0) return InteractionResult.PASS;
            	int n = getLoadedBaseCount(pStack, type);
            	if (n >= cc.injectionCost() && cc.onInjection(pPlayer)){
            		setLoadedBaseCount(pPlayer.getItemInHand(pHand), type, n - cc.injectionCost());
            		pPlayer.level().playSound(null, pPlayer.blockPosition(), CaerulaArborModSounds.ALCHEMIST_TRIGGER.get(), SoundSource.HOSTILE, 
        				1, Mth.nextFloat(pPlayer.getRandom(), 0.8f, 1.2f));
            		if (pStack.hurt(type + 1, pPlayer.getRandom(), serverPlayer)){
                    	pStack.setDamageValue(pStack.getMaxDamage()-1);
	                }
	                return InteractionResult.SUCCESS;
            	}
            }
        }
        return super.interactLivingEntity(pStack, pPlayer, pTarget, pHand);
    }

	public void giveAdv(Player entity){
    	if (entity instanceof ServerPlayer _player) {
			Advancement _adv = _player.server.getAdvancements().getAdvancement(adv);
			AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
			if (!_ap.isDone()) {
				for (String criteria : _ap.getRemainingCriteria())
					_player.getAdvancements().award(_adv, criteria);
			}
		}
	}

	@Override
    public InteractionResult useOn(UseOnContext context) {
    	if (extraInjections.isEmpty()) putEntry();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(pos);
        Player player = context.getPlayer();
        if (player != null){
        	int t = 0;
            EntityType<?> type = null;
            if (blockState.getBlock() instanceof AlchemyCreation.Furnace furnace){
            	t = furnace.injectionType();
            	type = furnace.injectionCreation();
            } else {
            	Pair<Integer, EntityType<?>> res = extraInjections.get(blockState.getBlock());
            	if (res == null) return super.useOn(context);
            	t = res.getFirst();
            	type = res.getSecond();
            }
            int c = getLoadedBaseCount(stack, t);
            if (level instanceof ServerLevel serverLevel && t >= 0
                    && c > 0 && type != null){
                Entity entity = type.spawn(serverLevel, pos, MobSpawnType.MOB_SUMMONED);
                if (entity instanceof AlchemyCreation mob){
                    mob.getLookControl().setLookAt(player);
                    mob.spawnedFromInjection(blockState, player);
                    level.destroyBlock(pos, false, player, 1);
                    setLoadedBaseCount(stack, t, c - 1);
                    giveAdv(player);
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }
        return super.useOn(context);
    }

    private static void putEntry(){
    	put(Blocks.CARVED_PUMPKIN, 0, CaerulaArborModEntities.PUMPKIN_CARRIAGE.get());
    	put(CaerulaArborModBlocks.TRAIL_PUMPKING.get(), 0, CaerulaArborModEntities.PUMPKIN_CARRIAGE.get());
    	put(Blocks.BELL, 0, CaerulaArborModEntities.SHAKING_BELL.get());
    	put(Blocks.BEACON, 2, CaerulaArborModEntities.YIRAHEL_BEACON.get());
    }

    private static void put(Block block, int type, EntityType<?> result){
    	extraInjections.put(block, Pair.of(type, result));
    }
}
