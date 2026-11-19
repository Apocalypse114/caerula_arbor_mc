
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import java.util.List;
import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Rarity;
import net.minecraft.client.gui.screens.Screen;
import java.util.function.Consumer;

public class TrailriteShieldItem extends ShieldItem {
	public TrailriteShieldItem() {
		super(new Item.Properties().durability(16384).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemstack, ItemStack repairitem) {
		return Ingredient.of(new ItemStack(CaerulaArborModItems.TRAILRITE.get())).test(repairitem);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		ItemStack retval = new ItemStack(this);
		retval.setDamageValue(itemstack.getDamageValue() + 1);
		if (retval.getDamageValue() >= retval.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return retval;
	}

	@Override
	public boolean isRepairable(ItemStack itemstack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_armor.description"));
		 list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_pre"));
		if(Screen.hasShiftDown()){
            list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_1"));
            list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_2"));
            list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_3"));
            list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_4"));
            list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_shield.description_5"));
        } else {
            list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_armor.description_1"));
        }
	}

	@Override
    public void setDamage(ItemStack stack, int damage){
        int max = this.getMaxDamage(stack);
        int v = Math.min(damage,this.getDamage(stack) + 1);
        if (v >= max-1) super.setDamage(stack, max - 1);
        else super.setDamage(stack, v);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
    	if (stack.getDamageValue() >= stack.getMaxDamage() -1) return 0;
        return 1;
    }

    private boolean shouldFunc(ItemStack stack){
        return this.getDamage(stack) < this.getMaxDamage(stack)-1;
    }

    UUID ADD_ARMOR_UUID = UUID.fromString("d8a06f80-7b2c-4e8a-9b8c-1234567890ab");
    UUID ADD_DEFENSE_UUID = UUID.fromString("e9b17991-8c3d-5f9b-0c9d-0987654321ba");
    AttributeModifier addArmor = new AttributeModifier(ADD_ARMOR_UUID, "trailrite_shield", 5, AttributeModifier.Operation.ADDITION);
    AttributeModifier addDefense = new AttributeModifier(ADD_DEFENSE_UUID,"trailrite_shield", 0.75, AttributeModifier.Operation.MULTIPLY_BASE);

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected){
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (!shouldFunc(itemstack)) return;
        if (entity instanceof LivingEntity living) {
            AttributeInstance defense_instance = living.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get());
            AttributeInstance armor_instance = living.getAttribute(Attributes.ARMOR);
            if (defense_instance != null && armor_instance != null){
                defense_instance.removeModifier(addDefense);
                armor_instance.removeModifier(addArmor);
                if (selected)
                    armor_instance.addTransientModifier(addArmor);
                if (living.getUseItem().is(this))
                    defense_instance.addTransientModifier(addDefense);
            }
        }
    }

    @Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
