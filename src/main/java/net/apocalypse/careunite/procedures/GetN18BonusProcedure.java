package net.apocalypse.careunite.procedures;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import java.util.ArrayList;
import java.util.List;


public class GetN18BonusProcedure {
	public static final List<? extends String> N18 = CaerulaConfigsConfiguration.N18_ENTRY.get();
    public static final List<ResourceLocation> FALLBACK = List.of(
            new ResourceLocation("minecraft:story/iron_tools"),
            new ResourceLocation("minecraft:story/enter_the_nether"),
            new ResourceLocation("minecraft:story/enter_the_end"),
            new ResourceLocation("minecraft:end/find_end_city")
    );
    public static final List<ResourceLocation> ADVS = new ArrayList<>();
    
    private static ResourceLocation getAdvancementInit(int index){
        if(index >= N18.size()) return FALLBACK.get(index);
        String rName = N18.get(index);
        if(rName.isBlank() || rName.equals("empty")) return FALLBACK.get(index);
        return new ResourceLocation(rName);
    }

    private static Advancement getAdvancementWithFallback(ServerAdvancementManager manager, int index){
        Advancement AdvGotten = manager.getAdvancement(ADVS.get(index));
        return AdvGotten != null ? AdvGotten : manager.getAdvancement(FALLBACK.get(index));
    }

    private static boolean isAdvancementDone(ServerAdvancementManager manager, Player player, int index){
        if(player.level() instanceof ServerLevel) {
            Advancement advancement = getAdvancementWithFallback(manager, index);
            if (advancement == null) return false;
            return player instanceof ServerPlayer sPlr && sPlr.getAdvancements().getOrStartProgress(advancement).isDone();
        }
        return false;
    }
    public static double execute(LevelAccessor world, double sw) {
        int coef = 1;
        int coef_cur;
        double n = sw;
        if (n > 0) {
        	if (ADVS.isEmpty()){
        		ADVS.add(getAdvancementInit(0));
            	ADVS.add(getAdvancementInit(1));
            	ADVS.add(getAdvancementInit(2));
            	ADVS.add(getAdvancementInit(3));
        	}
            n = 1 + 0.01 * n * 2;
            MinecraftServer server = world.getServer();
            if(server == null) return 1;
            ServerAdvancementManager manager = server.getAdvancements();
            for (Player player : world.players()) {
                coef_cur = 1;
                for (int i = 0; i<4; i++){
                    if(isAdvancementDone(manager, player, i)) coef_cur = i+2;
                    else break;
                }
                if (coef_cur > coef) {
                    coef = coef_cur;
                }
            }
            n = Math.pow(n, coef);
            return n;
        }
        return 1;
    }
}
