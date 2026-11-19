
package net.apocalypse.careunite.item;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.ShotOceanArrowEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ComplexChitinCrossbowItem extends CrossbowItem {
	public ComplexChitinCrossbowItem() {
		super(new Item.Properties().durability(4101).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
    public int getBarWidth(ItemStack stack){
        if (isCharged(stack)) return getAmmo(stack) * 3 + 1;
        return super.getBarWidth(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        if (isCharged(pStack)) return true;
        return super.isBarVisible(pStack);
    }

    @Override
    public int getBarColor(ItemStack stack){
        if (isCharged(stack)) return 0xddddff;
        return super.getBarColor(stack);
    }

    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

	public static final Predicate<ItemStack> ALSO_OCEAN_ARROW = (itemStack) -> {
        return ARROW_ONLY.test(itemStack) || itemStack.getItem() == CaerulaArborModItems.OCEAN_ARROW.get();
    };
    public static final Predicate<ItemStack> ALSO_CARRY_BOMB = ARROW_OR_FIREWORK.or(ALSO_OCEAN_ARROW)
        	.or((itemStack)->itemStack.getItem() == CaerulaArborModBlocks.BOMB_TRAILER.get().asItem());
    
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ALSO_CARRY_BOMB;
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ALSO_OCEAN_ARROW;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int ammo = getAmmo(itemstack);
        if (ammo > 0) {
            int toShoot = nextSlotToShoot(itemstack);
            performShooting(pLevel, pPlayer, pHand, itemstack, toShoot, getShootingPower(itemstack, toShoot), 0.1F);
            return InteractionResultHolder.consume(itemstack);
        } else if (!pPlayer.getProjectile(itemstack).isEmpty()) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    private static float getShootingPower(ItemStack pCrossbowStack, int slot) {
        return containsChargedProjectile(pCrossbowStack, Items.FIREWORK_ROCKET, slot) ? 2F : 3.15F;
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        int i = this.getUseDuration(pStack) - pTimeLeft;
        float f = getPowerForTime(i, pStack);
        if (f >= 1.0F && !isCharged(pStack)) {
            boolean flag0 = tryLoadProjectiles(pEntityLiving, pStack, 0);
            boolean flag1 = tryLoadProjectiles(pEntityLiving, pStack, 1);
            boolean flag2 = tryLoadProjectiles(pEntityLiving, pStack, 2);
            boolean flag3 = tryLoadProjectiles(pEntityLiving, pStack, 3);
            updateStyle(pStack, pStack.getOrCreateTag(), nextSlotToShoot(pStack));
            if (flag0 || flag1 || flag2 || flag3) {
                setCharged(pStack, true);
                SoundSource soundsource = pEntityLiving instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
                pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(),
                        SoundEvents.CROSSBOW_LOADING_END, soundsource,
                        1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
            }
        }
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pCount) {
        if (!pLevel.isClientSide) {
            int i = pStack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(pStack.getUseDuration() - pCount) / (float)getChargeDuration(pStack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }
            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(),
                        soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(),
                        soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    private static boolean tryLoadProjectiles(LivingEntity pShooter, ItemStack pCrossbowStack, int slot) {

        int enchantmentLevel = pCrossbowStack.getEnchantmentLevel(Enchantments.MULTISHOT);
        int ammoNumber = enchantmentLevel == 0 ? 1 : 3;
        boolean flag = pShooter instanceof Player && ((Player)pShooter).getAbilities().instabuild;
        ItemStack itemstack = pShooter.getProjectile(pCrossbowStack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < ammoNumber; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(pShooter, pCrossbowStack, itemstack, k > 0, flag, slot)) {
                return false;
            }
        }

        return true;
    }

    private static boolean loadProjectile(
            LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack,
            boolean pHasAmmo, boolean pIsCreative, int slot) {
        if (pAmmoStack.isEmpty()) {
            return false;
        } else {
            boolean flag = pIsCreative && pAmmoStack.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !pIsCreative && !pHasAmmo) {
                itemstack = pAmmoStack.split(1);
                if (pAmmoStack.isEmpty() && pShooter instanceof Player) {
                    ((Player)pShooter).getInventory().removeItem(pAmmoStack);
                }
            } else {
                itemstack = pAmmoStack.copy();
            }

            addChargedProjectile(pCrossbowStack, itemstack, slot);
            return true;
        }
    }

    private static void addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack, int slot) {
        CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
        ListTag listtag = new ListTag();
        String name = TAG_NAME[slot];
        if (compoundtag.contains(name, 9)) {
            listtag = compoundtag.getList(name, 10);
        }

        CompoundTag compoundtag1 = new CompoundTag();
        pAmmoStack.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put(name, listtag);
    }

    public static String[] TAG_NAME = {"ChargedProjectiles", "ChargedProjectilesA", "ChargedProjectilesB","ChargedProjectilesC"};

    public static int getAmmo(ItemStack stack){
        return (isFree(stack, 0) ? 0: 1)
                + (isFree(stack, 1) ? 0: 1)
                + (isFree(stack, 2) ? 0: 1)
                + (isFree(stack, 3) ? 0: 1);
    }

    public static int nextSlotToShoot(ItemStack stack){
    	if (!isFree(stack, 3)) return 3;
        if (!isFree(stack, 2)) return 2;
        if (!isFree(stack, 1)) return 1;
        if (!isFree(stack, 0)) return 0;
        return -1;
    }

    public static boolean isFree(ItemStack stack, int slot){
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getList(TAG_NAME[slot], 10).isEmpty();
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        List<List<ItemStack>> listList = List.of(
                getChargedProjectiles(pStack, 0),
                getChargedProjectiles(pStack, 1),
                getChargedProjectiles(pStack, 2),
                getChargedProjectiles(pStack, 3));
        int i = 0;
        pTooltip.add(Component.translatable("item.caerula_arbor_reunite.chitin_crossbow.desc").withStyle(ChatFormatting.GRAY));
        if (isCharged(pStack)) {
            pTooltip.add(Component.translatable("item.minecraft.crossbow.projectile"));
            for (List<ItemStack> list: listList) {
            	if (list.isEmpty()) continue;
                ItemStack itemstack =  list.get(0);
                i++;
                pTooltip.add(Component.literal(""+i).append(CommonComponents.SPACE).append(itemstack.getDisplayName()));
                if (pFlag.isAdvanced() && itemstack.is(Items.FIREWORK_ROCKET)) {
                    List<Component> list1 = Lists.newArrayList();
                    Items.FIREWORK_ROCKET.appendHoverText(itemstack, pLevel, list1, pFlag);
                    if (!list1.isEmpty()) {
                        list1.replaceAll(
                                pSibling -> Component.literal("  ").append(pSibling).withStyle(ChatFormatting.GRAY));
                        pTooltip.addAll(list1);
                    }
                }
            }
        }
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack pCrossbowStack, int slot) {
        List<ItemStack> list = Lists.newArrayList();
        if (slot < 0) return list;
        CompoundTag compoundtag = pCrossbowStack.getTag();
        String name = TAG_NAME[slot];
        if (compoundtag != null && compoundtag.contains(name, 9)) {
            ListTag listtag = compoundtag.getList(name, 10);
            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                list.add(ItemStack.of(compoundtag1));
            }
        }

        return list;
    }

    private static void clearChargedProjectiles(ItemStack pCrossbowStack, int slot) {
		if (slot < 0) return;
    	
        CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
        String name = TAG_NAME[slot];

        ListTag listtag = compoundtag.getList(name, 9);
        listtag.clear();
        compoundtag.put(name, listtag);

        int next = nextSlotToShoot(pCrossbowStack);
        updateStyle(pCrossbowStack, compoundtag, next);

        if (getAmmo(pCrossbowStack) <= 0) setCharged(pCrossbowStack, false);
    }

    public static void updateStyle(ItemStack pCrossbowStack, CompoundTag compoundtag, int slot){
    	List<ItemStack> items = getChargedProjectiles(pCrossbowStack, slot);
        if (items.isEmpty()) compoundtag.putInt("style", 0);
        else{
            ItemStack itemStack = items.get(0);
            if (itemStack.is(CaerulaArborModBlocks.BOMB_TRAILER.get().asItem())) compoundtag.putInt("style", 4);
            else if (itemStack.is(CaerulaArborModItems.OCEAN_ARROW.get())) compoundtag.putInt("style", 3);
            else if (itemStack.is(Items.FIREWORK_ROCKET)) compoundtag.putInt("style", 2);
            else if (itemStack.is(ItemTags.ARROWS)) compoundtag.putInt("style", 1);
        }
    }

    public static boolean containsChargedProjectile(ItemStack pCrossbowStack, Item pAmmoItem, int slot) {
        return getChargedProjectiles(pCrossbowStack, slot).stream().anyMatch((p_40870_) -> p_40870_.is(pAmmoItem)
        );
    }

    public static Projectile throwBomb(LivingEntity pShooter, Level projectileLevel){
		AbstractArrow entityToSpawn = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), projectileLevel);
		entityToSpawn.setBaseDamage(10);
		entityToSpawn.setKnockback(0);
		entityToSpawn.setPos(pShooter.getX(), pShooter.getEyeY() - 0.15, pShooter.getZ());
		entityToSpawn.shoot(pShooter.getLookAngle().x, pShooter.getLookAngle().y, pShooter.getLookAngle().z, 2.5f, 0);
		entityToSpawn.getPersistentData().putBoolean("ComplexBomb", true);
		return entityToSpawn;
    }

    private static void shootProjectile(
            Level pLevel, LivingEntity pShooter, InteractionHand pHand, ItemStack pCrossbowStack, ItemStack pAmmoStack,
            float pSoundPitch, boolean pIsCreativeMode, float pVelocity, float pInaccuracy, float pProjectileAngle) {
        if (!pLevel.isClientSide) {
        	int s = 0;
            boolean flag = pAmmoStack.is(Items.FIREWORK_ROCKET);
            Projectile projectile;
            if (pAmmoStack.is(CaerulaArborModBlocks.BOMB_TRAILER.get().asItem())){
            	if (pProjectileAngle != 0) return;
		    	projectile = throwBomb(pShooter, pLevel);
		    } else if (flag) {
                projectile = new FireworkRocketEntity(
                	pLevel, pAmmoStack, pShooter, pShooter.getX(), 
                	pShooter.getEyeY() - (double)0.15F, pShooter.getZ(), true);
            } else {
                projectile = getArrow(pLevel, pShooter, pCrossbowStack, pAmmoStack);
                if (pIsCreativeMode || pProjectileAngle != 0.0F) {
                    ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }
			if (projectile instanceof AbstractArrow arrow){
				arrow.setBaseDamage(arrow.getBaseDamage() * 2.35 * getRate(pShooter));
			}

            if (pShooter instanceof CrossbowAttackMob crossbowattackmob) {
                crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), pCrossbowStack, projectile, pProjectileAngle);
            } else {
                Vec3 vec31 = pShooter.getUpVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf())
                        .setAngleAxis((double)(pProjectileAngle * ((float)Math.PI / 180F)), vec31.x, vec31.y, vec31.z);
                Vec3 vec3 = pShooter.getViewVector(1.0F);
                Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
                projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), pVelocity, pInaccuracy);
            }

            pCrossbowStack.hurtAndBreak(flag ? 3 : 1, pShooter, (p_40858_) -> {
                p_40858_.broadcastBreakEvent(pHand);
                if(p_40858_ instanceof Player player){
                    player.drop(getProjectileStack(pCrossbowStack, 0), false, true);
                    player.drop(getProjectileStack(pCrossbowStack, 1), false, true);
                    player.drop(getProjectileStack(pCrossbowStack, 2), false, true);
                    player.drop(getProjectileStack(pCrossbowStack, 3), false, true);
                }
            });
            projectile.getPersistentData().putBoolean("ComplexChitinCB", true);

            if (pAmmoStack.is(CaerulaArborModBlocks.BOMB_TRAILER.get().asItem())) s = 1;
            
            pLevel.addFreshEntity(projectile);
            if (s == 0){
	            pLevel.playSound(null, pShooter.getX(), pShooter.getY(), pShooter.getZ(),
	                    SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, pSoundPitch);
            } else if (s==1){
	            pLevel.playSound(null, pShooter.getX(), pShooter.getY(), pShooter.getZ(),
	                    getSound(SPECIAL), SoundSource.PLAYERS, 1.0F, pSoundPitch);
            }
        }
    }

    public static SoundEvent getSound(ResourceLocation res){
    	return ForgeRegistries.SOUND_EVENTS.getValue(res);
    }
    public static final ResourceLocation SPECIAL = new ResourceLocation("caerula_arbor_reunite:crossbow_special_shot");

    private static AbstractArrow getArrow(Level pLevel, LivingEntity pLivingEntity, ItemStack pCrossbowStack, ItemStack pAmmoStack) {
        ArrowItem arrowitem = (ArrowItem)(pAmmoStack.getItem() instanceof ArrowItem ? pAmmoStack.getItem() : Items.ARROW);
        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, pAmmoStack, pLivingEntity);
        if (pLivingEntity instanceof Player) {
            abstractarrow.setCritArrow(true);
        }
        if (pAmmoStack.is(CaerulaArborModItems.OCEAN_ARROW.get())){
        	abstractarrow = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), pLivingEntity, pLevel);
        	abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() * 1.25);
        	abstractarrow.getPersistentData().putBoolean("ComplexChitinCB_ocean", true);
        	abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrow.setShotFromCrossbow(true);
        int i = pCrossbowStack.getEnchantmentLevel(Enchantments.PIERCING);
        if (i > 0) {
            abstractarrow.setPierceLevel((byte)i);
        }

        return abstractarrow;
    }

    private static ItemStack getProjectileStack(ItemStack stack, int slot){
        List<ItemStack> itemStacks = getChargedProjectiles(stack, slot);
        if (itemStacks.isEmpty()) return ItemStack.EMPTY;
        return itemStacks.get(0);
    }

    public static void performShooting(
            Level pLevel, LivingEntity pShooter, InteractionHand pUsedHand, ItemStack pCrossbowStack,
            int slot, float pVelocity, float pInaccuracy) {
        if (pShooter instanceof Player player && net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pCrossbowStack, pShooter.level(), player, 1, true) < 0) return;
        List<ItemStack> list = getChargedProjectiles(pCrossbowStack, slot);
        float[] afloat = getShotPitches(pShooter.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = pShooter instanceof Player && ((Player)pShooter).getAbilities().instabuild;
            if (!itemstack.isEmpty()) {
                if (i == 0) {
                    shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack,
                            afloat[i], flag, pVelocity, pInaccuracy, 0.0F);
                } else if (i == 1) {
                    shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack,
                            afloat[i], flag, pVelocity, pInaccuracy, -10.0F);
                } else if (i == 2) {
                    shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack,
                            afloat[i], flag, pVelocity, pInaccuracy, 10.0F);
                }
            }
        }

        onCrossbowShot(pLevel, pShooter, pCrossbowStack, slot);
    }

    private static float[] getShotPitches(RandomSource pRandom) {
        boolean flag = pRandom.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag, pRandom), getRandomShotPitch(!flag, pRandom)};
    }

    private static float getRandomShotPitch(boolean pIsHighPitched, RandomSource pRandom) {
        float f = pIsHighPitched ? 0.63F : 0.43F;
        return 1.0F / (pRandom.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void onCrossbowShot(Level pLevel, LivingEntity pShooter, ItemStack pCrossbowStack, int slot) {
        if (pShooter instanceof ServerPlayer serverplayer) {
            if (!pLevel.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayer, pCrossbowStack);
            }

            serverplayer.awardStat(Stats.ITEM_USED.get(pCrossbowStack.getItem()));
        }

        clearChargedProjectiles(pCrossbowStack, slot);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return getChargeDuration(pStack) + 3;
    }

    public static int getChargeDuration(ItemStack pCrossbowStack) {
        int i = pCrossbowStack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        return i == 0 ? 35 : 35 - 5 * i;
    }

    private static float getPowerForTime(int pUseTime, ItemStack pCrossbowStack) {
        float f = (float)pUseTime / (float)getChargeDuration(pCrossbowStack);
        if (f > 1) {
            f = 1;
        }

        return f;
    }

    private SoundEvent getStartSound(int pEnchantmentLevel) {
        return switch (pEnchantmentLevel) {
            case 1 -> SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2 -> SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3 -> SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default -> SoundEvents.CROSSBOW_LOADING_START;
        };
    }

    @Override
    public int getDefaultProjectileRange() {
        return 14;
    }

    private static double getRate(LivingEntity entity){
        AttributeInstance atk = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if(atk == null) return 0;
        Set<AttributeModifier> BASE = atk.getModifiers(AttributeModifier.Operation.MULTIPLY_BASE);
        Set<AttributeModifier> TOTAL = atk.getModifiers(AttributeModifier.Operation.MULTIPLY_TOTAL);
        double expo_base = 1 + BASE.stream().mapToDouble(AttributeModifier::getAmount).sum();
        double expo_total = TOTAL.stream().mapToDouble(m -> {return 1+m.getAmount();}).reduce(1, (a,b)->(a*b));
        return Math.max(1, expo_base * expo_total);
    }
}
