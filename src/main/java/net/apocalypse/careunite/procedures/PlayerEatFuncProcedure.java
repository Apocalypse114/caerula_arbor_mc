package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;

@Mod.EventBusSubscriber
public class PlayerEatFuncProcedure {
	@SubscribeEvent
	public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getItem());
		}
	}

	public static void execute(Entity entity, ItemStack itemstack) {
		execute(null, entity, itemstack);
	}

	public static double convert(String s) {
		try {
			return Double.parseDouble(s.trim());
		} catch (Exception e) {
		}
		return 0;
	}


	private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		String str = "";
		double least = 0;
		double most = 0;
		double index = 0;
		double life = 0;
		double maxLife = 0;
		double curLife = 0;
		if (!entity.level().isClientSide() && itemstack.getItem().isEdible() 
			&& entity instanceof Player _plr 
			&& _plr.getHealth() >= _plr.getMaxHealth() * 0.6f){
			FoodData fooddata = _plr.getFoodData();
			if (fooddata != null && fooddata.getFoodLevel() < 20 && fooddata.getSaturationLevel() < 20) {
				FoodProperties foodp = itemstack.getItem().getFoodProperties();
				if (foodp == null) return;
				index = foodp.getNutrition() * 0.01;
				if (fooddata.getFoodLevel() > 16) {
					index = index * 1.5;
				}
				if (foodp.getSaturationModifier() > 0.5) {
					index = index * 1.25;
				}
				if (_plr.getRandom().nextFloat() < index) {
					most = 1;
					str = Component.translatable("gameplay.life_point.revive.2").getString();
					if (foodp.getSaturationModifier() > 0.1 && _plr.getRandom().nextFloat() < 0.33) {
						most = 2;
						str = Component.translatable("gameplay.life_point.revive.1").getString();
					}
					if (fooddata.getFoodLevel() > 16 && _plr.getRandom().nextFloat() < 0.33) {
						str = Component.translatable("gameplay.life_point.revive.0").getString();
					}
					life = Mth.nextInt(_plr.getRandom(), 1, (int) most);
					CaerulaArborModVariables.PlayerVariables vars = entity
						.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElseGet(CaerulaArborModVariables.PlayerVariables::new);
					maxLife = vars.player_maxlive;
					curLife = vars.player_lives;
					if (curLife < maxLife) {
						ChangePlayerLPOrShieldProcedure.execute(entity.level(), entity, true, life, "food.saturation");
						_plr.displayClientMessage(Component.literal(("\u00A7a" + str.replace("{num}", "" + Math.round(life)))), true);
					}
				}
			}
			for (String stringiterator : CaerulaConfigsConfiguration.LIGHTS_FOOD.get()) {
				index = stringiterator.indexOf(", ", 0);
				if (index < 0) {
					index = stringiterator.indexOf(",", 0);
				}
				if (index < 0) {
					index = stringiterator.indexOf("\uFF0C ", 0);
					if (index >= 0) CaerulaArborMod.LOGGER.warn("Fullwidth Comma found in {}, try the halfwidth one?", stringiterator);
				}
				if (index < 0) {
					index = stringiterator.indexOf("\uFF0C", 0);
					if (index >= 0) CaerulaArborMod.LOGGER.warn("Fullwidth Comma found in {}, try the halfwidth one?", stringiterator);
				}
				if (index < 0) {
					CaerulaArborMod.LOGGER.warn("Wrong lights food config for item {}: missing comma." , stringiterator);
					return;
				}
				str = stringiterator.substring(0, (int) index);
				if ((ForgeRegistries.ITEMS.getKey(itemstack.getItem()).toString()).equals(str)) {
					str = stringiterator.substring((int) (index + 2));
					index = str.indexOf("/", 0);
					if (index < 0) {
						CaerulaArborMod.LOGGER.warn("Wrong lights food config for item {}: missing '/' to split numbers" , stringiterator);
						return;
					}
					least = convert(str.substring(0, (int) index));
					most = convert(str.substring((int) (index + 1)));
					RevivesPlayerLightsProcedure.execute(entity, Mth.nextInt(_plr.getRandom(), (int) least, (int) most));
				}
			}
		}
	}
}
