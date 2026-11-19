package net.apocalypse.careunite.core;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.procedures.ChangePlayerLPOrShieldProcedure;
import net.apocalypse.careunite.procedures.DeductPlayerSanityProcedure;
import net.apocalypse.careunite.procedures.GiveLessArmorProcedure;
import net.apocalypse.careunite.procedures.GrantRandomExistingRejectionProcedure;
import net.apocalypse.careunite.procedures.IsRejectionOnProcedure;
import net.apocalypse.careunite.procedures.RevivesPlayerLightsProcedure;
import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.procedures.ReducePlayerLightsProcedure;
import net.apocalypse.careunite.procedures.RemoveRandomOnRejectionProcedure;
import net.apocalypse.careunite.procedures.SetRejectionOffProcedure;
import net.apocalypse.careunite.procedures.SetRejectionOnProcedure;
import net.apocalypse.careunite.procedures.TransformIndexProcedure;
import net.minecraft.world.entity.player.Player;
import net.apocalypse.careunite.network.CaerulaArborModVariables;

import net.apocalypse.careunite.procedures.DeductPlayerSanityProcedure;
import net.apocalypse.careunite.procedures.RevivesPlayerSanityProcedure;
import net.apocalypse.careunite.procedures.RevivesPlayerLightsProcedure;
import net.apocalypse.careunite.procedures.GiveLessArmorProcedure;
import net.apocalypse.careunite.procedures.IsRejectionOnProcedure;
import net.apocalypse.careunite.procedures.SetRejectionOnProcedure;
import net.apocalypse.careunite.procedures.SetRejectionOffProcedure;
import net.apocalypse.careunite.procedures.GrantRandomExistingRejectionProcedure;
import net.apocalypse.careunite.procedures.RemoveRandomOnRejectionProcedure;
import net.apocalypse.careunite.procedures.ChangePlayerLPOrShieldProcedure;
import net.apocalypse.careunite.procedures.ReducePlayerLightsProcedure;
import net.apocalypse.careunite.procedures.TransformIndexProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.tags.TagKey;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Rarity;
import net.minecraft.ChatFormatting;

import java.util.function.Predicate;

public class CaerulaUtil {
	
	public static class PlayerData{
		public static boolean giveLifePoint(Player player, int amount, String reason){
			return ChangePlayerLPOrShieldProcedure.execute(player.level(), player, true, (double)amount, reason);
		}
		public static boolean giveShieldPoint(Player player, int amount, String reason){
			return ChangePlayerLPOrShieldProcedure.execute(player.level(), player, false, (double)amount, reason);
		}
		public static int getLifePoint(Player player){
			return (int) player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.orElse(new CaerulaArborModVariables.PlayerVariables()).player_lives;
		}
		public static void setLifePoint(Player player, int value){
			if(value < 1) return;
			int maxPoint = Math.min(getMaxLifePoint(player), CaerulaConfigsConfiguration.LP_LIMIT.get().intValue());
			player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.ifPresent(c -> {
				c.player_lives = (double) Math.min(value,maxPoint);
				c.syncPlayerVariables(player);
			});
		}
		public static int getMaxLifePoint(Player player){
			return (int) player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.orElse(new CaerulaArborModVariables.PlayerVariables()).player_maxlive;
		}
		public static void setMaxLifePoint(Player player, int value){
			if(value < 1) return;
			final int fValue = Math.min(value, CaerulaConfigsConfiguration.LP_LIMIT.get().intValue());
			player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.ifPresent(c -> {
				c.player_maxlive = (double) fValue;
				c.syncPlayerVariables(player);
			});
			if (value < getLifePoint(player)) setLifePoint(player, value);
		}
		public static int getShieldPoint(Player player){
			return (int) player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.orElse(new CaerulaArborModVariables.PlayerVariables()).player_shield;
		}
		public static void setShieldPoint(Player player, int value){
			if(value < 0) return;
			final int fvalue = Math.min(value, CaerulaConfigsConfiguration.SHIELD_LIMIT.get().intValue());
			player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.ifPresent(c -> {
				c.player_shield = (double) fvalue;
				c.syncPlayerVariables(player);
			});
		}

		public static double getLights(Player player){
			return player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.orElse(new CaerulaArborModVariables.PlayerVariables()).player_light;
		}
		public static void setLights(Player player, double value){
			player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.ifPresent(c -> {
				c.player_light = Mth.clamp(value, 0, 100);
				c.syncPlayerVariables(player);
			});
		}
		public static void reviveLights(Player player, double value){
			RevivesPlayerLightsProcedure.execute(player, value);
		}
		public static void reduceLights(Player player, double value){
			ReducePlayerLightsProcedure.execute(player.level(), player, value);
		}
	}
	public static void dealSanityInjury(LivingEntity living, double amount){
		DeductPlayerSanityProcedure.execute(living, amount);
	}
	public static void healSanityInjury(LivingEntity living, double amount){
		net.apocalypse.careunite.procedures.RevivesPlayerSanityProcedure.execute(living, amount);
	}
	public static void armorErrosion(Entity entity, int amount, int limit){
		for (int i=0;i<amount;i++){
			GiveLessArmorProcedure.execute(entity, limit);
		}
	}

	public static boolean hasRejection(Player player, Rejections rejection){
		return IsRejectionOnProcedure.execute(player, rejection.index());
	}

	public static void setRejection(Player player, Rejections rejection, boolean on){
		if(on){
			SetRejectionOnProcedure.execute(player, rejection.index());
		} else {
			SetRejectionOffProcedure.execute(player, rejection.index());
		}
	}

	public static void giveRejectionWithoutRepetition(Player player){
		GrantRandomExistingRejectionProcedure.execute(player);
	}

	public static void removeRejectionWithoutRepetition(Player player){
		RemoveRandomOnRejectionProcedure.execute(player);
	}

	public static void addCustomOceanizationEntry(Predicate<LivingEntity> predicate, EntityType<?> result, float percentage){
		TransformIndexProcedure.addTransformIndex(predicate, result, percentage);
	}

	public static class Tags{
		public static final TagKey<EntityType<?>> SEABORNS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring"));
		public static final TagKey<Block> NETHERSEA_BRAND = BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail"));
	}

	public enum Rejections{
        DISCONCENTRATION(0),
        HAEMOPHILIA(1),
        NEURODEGRESSION(2),
        FLESHDEFORMITY(3);

        public final int index;
        private Rejections(int i){
            this.index = i;
        }
        public int index(){
            return this.index;
        }
    }

	public static class RelicRarity{
    	public static final Rarity CURSED = Rarity.create("CURSED", ChatFormatting.DARK_RED);
	}
}
