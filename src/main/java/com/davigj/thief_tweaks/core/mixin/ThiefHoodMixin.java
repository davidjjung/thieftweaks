package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.davigj.thief_tweaks.core.ThiefTweaksMod;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThiefHoodItem.class)
public class ThiefHoodMixin {
    @Inject(method = "getLevelCaps", at = @At("HEAD"), cancellable = true, remap = false)
    private void modifyLevelCaps(CallbackInfoReturnable<int[]> info) {
        if (TTConfig.COMMON.customHoodXPThresholds.get()) {
            Logger LOGGER = LogManager.getLogger(ThiefTweaksMod.MOD_ID);
            int[] modifiedLevelCaps = new int[5];

            try {
                modifiedLevelCaps[0] = 0;
                modifiedLevelCaps[1] = TTConfig.COMMON.hoodXPThreshold1.get();
                modifiedLevelCaps[2] = TTConfig.COMMON.hoodXPThreshold2.get();
                modifiedLevelCaps[3] = TTConfig.COMMON.hoodXPThreshold3.get();
                modifiedLevelCaps[4] = TTConfig.COMMON.hoodXPThreshold4.get();

                if (modifiedLevelCaps[4] <= modifiedLevelCaps[3]
                        || modifiedLevelCaps[3] <= modifiedLevelCaps[2]
                        || modifiedLevelCaps[2] <= modifiedLevelCaps[1]) {
                    LOGGER.warn("Thief's hood thresholds should be configured in increasing order. Resetting to default");
                    modifiedLevelCaps = new int[]{0, 10, 50, 100, 500};
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("NumberFormatException: Improper configuration of XP thresholds. Resetting to default");
                modifiedLevelCaps = new int[]{0, 10, 50, 100, 500};
            }

            info.setReturnValue(modifiedLevelCaps);
        }
    }

    @Inject(method = "livingDeathEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void modifyLivingDeathEvent(LivingDeathEvent event, CallbackInfo ci) {
        if (TTConfig.COMMON.playerKillsOnly.get() && TTConfig.COMMON.doPlayerKillXP.get()) {
            ci.cancel();
        }
    }
}
