
package net.apocalypse.careunite.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import net.apocalypse.careunite.procedures.IsFilledwithPersonnelProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import java.util.List;
import java.util.UUID;
import net.minecraft.world.entity.item.ItemEntity;

public class PersonnelTransporterItem extends Item {
	public PersonnelTransporterItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
	}

	public static final TagKey<EntityType<?>> HOMO_SAPIENS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:portable"));
    public static final String emptyNameHolder = "apocata";
    public static final String TAG_NAME = "name";
    public static final String TAG_PERC = "perc";
    public static final String TAG_OWNER = "owner";
    public static final String TAG_DISPLAY_NAME = "custom_name";
    //public static final String TAG_DATAS = "persistent_data";
    public static final String TAG_TRANSIENT = "transient";
    public static final String TAG_COUNT_DOWN = "count_down";
    public static final String TAG_COUNT_DOWN_FULL = "count_down_full";

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return IsFilledwithPersonnelProcedure.execute(itemstack);
	}

	@Override
    public int getBarWidth(ItemStack stack){
        if (isTransient(stack)) return Math.round((1 - getPercentage(stack)) * 13);
        return 0;
    }

    private static float getPercentage(ItemStack stack){
    	return ((float)getTimeLeft(stack)) / ((float)getTimeTotal(stack));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
    	return isTransient(pStack);
    }

    @Override
    public int getBarColor(ItemStack stack){
        return 0xffcccc;
    }

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.personnel_transporter.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.personnel_transporter.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.personnel_transporter.description_2"));
		list.add(getStoredEntityName(itemstack));
		if (isTransient(itemstack)){
			int t = Math.round(getTimeLeft(itemstack) * 0.05f);
			String desc = Component.translatable("item.caerula_arbor_reunite.personnel_transporter.trans").getString();
			desc = desc.replace("<perc>", ""+ t + "s "+ (int)(100 - getPercentage(itemstack) * 100));
			if (t > 0) desc = "§c"+desc;
			else desc = "§a"+desc;
			list.add(Component.literal(desc));
		}
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if(isTransient(itemstack) && getTimeLeft(itemstack) > 0){
			setTimeLeft(itemstack, getTimeLeft(itemstack) - 1);
		}
	}

	@Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand
    ) {
        if (storeEntity(pStack, pTarget, pPlayer, pHand)) return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext){
        Direction dire = pContext.getClickedFace();
        BlockPos pos = pContext.getClickedPos().offset(dire.getStepX(),dire.getStepY(),dire.getStepZ());
        ItemStack item = pContext.getItemInHand();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if (player != null && !player.isShiftKeyDown()) return InteractionResult.PASS;
        EntityType<?> type = getStoredEntityType(item);
        if(type==null) return InteractionResult.PASS;
        boolean t = isTransient(item);
        if (t && getTimeLeft(item) > 0) return InteractionResult.PASS;
        if(level instanceof ServerLevel sLevel){
        	CompoundTag tag = item.getOrCreateTag();
        	boolean shouldRelease = true;
        	UUID uuid = tag.contains(TAG_OWNER) ? tag.getUUID(TAG_OWNER) : null;
        	Entity owner = null;
        	if (uuid != null){
        		owner = sLevel.getEntity(uuid);
        		if (owner == null){
        			shouldRelease = false;
	        		if (!player.level().isClientSide())
	        			player.displayClientMessage(Component.translatable("item.caerula_arbor_reunite.personnel_transporter.note"),
	        				true);
        		}
        	}
            if (shouldRelease){
	            Entity toSpawn = type.spawn(sLevel, pos, MobSpawnType.MOB_SUMMONED);
	            double perc = t ? 0.1 : tag.getDouble(TAG_PERC);
	            if (toSpawn instanceof LivingEntity living) {
	                living.setHealth((float) (living.getMaxHealth() * perc));
	            }
	           	String displayName = tag.getString(TAG_DISPLAY_NAME);
	           	if (!displayName.isEmpty()) toSpawn.setCustomName(Component.literal(displayName));
	            if (toSpawn instanceof TamableAnimal tamable && owner instanceof Player p){
	            	tamable.tame(p);
	            }
	            tag.putString(TAG_NAME,emptyNameHolder);
	            tag.putString(TAG_DISPLAY_NAME, "");
	            tag.putDouble(TAG_PERC,1);
	            tag.remove(TAG_OWNER);
	            if (t) item.shrink(1);
	            else item.setTag(tag);
	            return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    public static EntityType<?> getStoredEntityType(ItemStack item){
        if(item == null) return null;
        CompoundTag tag = item.getOrCreateTag();
        if(!tag.contains(TAG_NAME)) return null;
        String name = tag.getString(TAG_NAME);
        if (name.isEmpty() || name.equals(emptyNameHolder))return null;
        ResourceLocation location = new ResourceLocation(name);
        return ForgeRegistries.ENTITY_TYPES.getValue(location);
    }
    public static boolean storeEntity(ItemStack pStack, LivingEntity pTarget, Player pPlayer, InteractionHand pHand){
    	EntityType<?> type = pTarget.getType();
        if(type.is(HOMO_SAPIENS) && !pTarget.level().isClientSide()){
            CompoundTag tag = pStack.getOrCreateTag();
            String name = tag.getString(TAG_NAME);
	        if (name.isEmpty() || name.equals(emptyNameHolder)){
	            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(type);
	            if (key == null) return false;
	            String id = key.toString();
	            if(id.equals("caerula_arbor_reunite:specter_doll")) id = "caerula_arbor_reunite:specter";
	            if(CaerulaConfigsConfiguration.DEBUG.get()) CaerulaArborMod.LOGGER.info("store {}", id);
                tag.putString(TAG_NAME, id);
                tag.putDouble(TAG_PERC, pTarget.getHealth() / pTarget.getMaxHealth());
                String displayName = pTarget.getDisplayName().getString();
                if (!displayName.equals(pTarget.getType().getDescription().getString())){
                	tag.putString(TAG_DISPLAY_NAME, displayName);
                }
                if (pTarget instanceof TamableAnimal ta){
                	UUID ownerUUID = ta.getOwnerUUID();
                	if (ownerUUID != null){
                		tag.putUUID(TAG_OWNER, ownerUUID);
                	}
                }
                dropAllSlots(pTarget);
                pTarget.discard();
                pStack.setTag(tag);
                if (pPlayer != null && pHand != null){
                	pPlayer.getItemInHand(pHand).setTag(tag);
                }
                return true;
	        }
        }
        return false;
    }

    public static Component getStoredEntityName(ItemStack itemStack){
        EntityType<?> type = getStoredEntityType(itemStack);
        if(type != null)
            return type.getDescription();
        return Component.literal("EMPTY").withStyle(ChatFormatting.AQUA);
    }

    public static void dropItem(ItemStack stack, Level level, double x, double y, double z){
    	if (level instanceof ServerLevel sLevel){
    		ItemEntity entityToSpawn = new ItemEntity(sLevel, x, y, z, stack);
			entityToSpawn.setPickUpDelay(10);
			sLevel.addFreshEntity(entityToSpawn);
    	}
    }

    public static void dropAllSlots(LivingEntity entity){
    	Level level = entity.level();
    	double x = entity.getX(), y = entity.getY(), z = entity.getZ();
    	dropItem(entity.getMainHandItem(), level, x, y, z);
    	dropItem(entity.getOffhandItem(), level, x, y, z);
    	for (ItemStack armor: entity.getArmorSlots()) dropItem(armor, level, x, y, z);
    }

    public static boolean isTransient(ItemStack stack){
    	return stack.getOrCreateTag().getBoolean(TAG_TRANSIENT);
    }
    public static void setTransient(ItemStack stack){
    	stack.getOrCreateTag().putBoolean(TAG_TRANSIENT, true);
    }
	public static int getTimeLeft(ItemStack stack){
		return stack.getOrCreateTag().getInt(TAG_COUNT_DOWN);
	}
	public static void setTimeLeft(ItemStack stack, int t){
		stack.getOrCreateTag().putInt(TAG_COUNT_DOWN, t);
	}
	public static int getTimeTotal(ItemStack stack){
		return stack.getOrCreateTag().getInt(TAG_COUNT_DOWN_FULL);
	}
	public static void setTimeTotal(ItemStack stack, int t){
		stack.getOrCreateTag().putInt(TAG_COUNT_DOWN_FULL, t);
	}
    public static ItemStack initTransient(LivingEntity entity, int time){
    	ItemStack stack = new ItemStack(CaerulaArborModItems.PERSONNEL_TRANSPORTER.get());
    	if (storeEntity(stack, entity, null, null)){
	    	setTimeLeft(stack, time);
	    	setTimeTotal(stack, time);
	    	setTransient(stack);
	    	return stack;
    	}
    	return ItemStack.EMPTY;
    }
}
