
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;

public class TrailriteNuggetItem extends Item {
	public TrailriteNuggetItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
