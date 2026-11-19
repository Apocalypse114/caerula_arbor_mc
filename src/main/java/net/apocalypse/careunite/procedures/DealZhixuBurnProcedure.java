package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import java.lang.reflect.Method;
import net.minecraftforge.fml.ModList;
import net.minecraft.world.entity.LivingEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class DealZhixuBurnProcedure {
	public static Method dealBurn = null;
    public static Class<?> burnClass = null;
    public static boolean isModLoaded = false;
    public static boolean modLoadingChecked = false;

    public static boolean checkModLoaded(){
        if (!modLoadingChecked){
            isModLoaded = ModList.get().isLoaded("sarkazs_furnace_side_fables");
            modLoadingChecked = true;
        }
        return isModLoaded;
    }
    public static void initMethod(){
        try {
            burnClass = Class.forName("com.zhixu.themassestravels.content.combustion.common.service.CombustionService");
            dealBurn = burnClass.getDeclaredMethod("applyCombustionDamage", LivingEntity.class, double.class);
        } catch (Exception e) {
            isModLoaded = false;
            CaerulaArborMod.LOGGER.warn("Error reflecting mtehod despite mod loaded:", e);
            if (burnClass != null){
                Method[] methods = burnClass.getMethods();
                StringBuilder a = new StringBuilder();
                for (Method method: methods){
                    a.append(method.toString());
                }
                CaerulaArborMod.LOGGER.warn("Known methods: {}", a);
            }
        }
    }
    public static boolean tryDealBurn(LivingEntity living, double amount){
        if (checkModLoaded()){
            if (burnClass == null || dealBurn == null)
                initMethod();
            if (dealBurn != null){
                try {
                    dealBurn.invoke(null, living, amount);
                    return true;
                } catch (Exception e){
                    isModLoaded = false;
                    CaerulaArborMod.LOGGER.warn("Error invoking reflected mtehod:", e);
                }
            }
        }
        return false;
    }
	public static boolean execute(Entity entity, double amount) {
		if (entity instanceof LivingEntity liv)
 return tryDealBurn(liv, amount);
		return false;
	}
}
