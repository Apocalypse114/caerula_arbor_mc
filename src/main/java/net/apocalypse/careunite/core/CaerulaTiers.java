
package net.apocalypse.careunite.core;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.item.Tier;
import com.google.common.base.Supplier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;
import org.jetbrains.annotations.NotNull;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.item.Items;

public enum CaerulaTiers implements Tier{
	CHITIN(3, 999, 6, 4, 11, () -> Ingredient.of(CaerulaArborModItems.OCEAN_CHITIN.get())),
	COMPLEX_CHITIN(3, 3374, 15, 8, 18, () -> Ingredient.of(CaerulaArborModItems.COMPLEX_CHITIN.get())),
	TRAILRITE(4, 7999, 19, 10, 22, () -> Ingredient.of(CaerulaArborModItems.TRAILRITE.get())),
	CRYSTAL(2, 225, 4, 3.5f, 18, () -> Ingredient.of(CaerulaArborModItems.OCEAN_CRYSTAL.get())),
	AEGIR(3, 0, 9, 5, 12, () -> Ingredient.of(Items.COPPER_INGOT, Items.IRON_INGOT));

	private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    CaerulaTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }


    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
