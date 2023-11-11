package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.davigj.thief_tweaks.core.ThiefTweaksMod;
import com.teamabnormals.environmental.common.item.explorer.WandererBootsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WandererBootsItem.class)
public class WandererBootsMixin {
    @Inject(method = "getLevelCaps", at = @At("HEAD"), cancellable = true, remap = false)
    private void modifyLevelCaps(CallbackInfoReturnable<int[]> info) {
        if (TTConfig.COMMON.customBootsXPThresholds.get()) {
            Logger LOGGER = LogManager.getLogger(ThiefTweaksMod.MOD_ID);
            int[] modifiedLevelCaps = new int[5];

            try {
                modifiedLevelCaps[0] = 0;
                modifiedLevelCaps[1] = TTConfig.COMMON.bootsXPThreshold1.get();
                modifiedLevelCaps[2] = TTConfig.COMMON.bootsXPThreshold2.get();
                modifiedLevelCaps[3] = TTConfig.COMMON.bootsXPThreshold3.get();
                modifiedLevelCaps[4] = TTConfig.COMMON.bootsXPThreshold4.get();

                if (modifiedLevelCaps[4] <= modifiedLevelCaps[3]
                        || modifiedLevelCaps[3] <= modifiedLevelCaps[2]
                        || modifiedLevelCaps[2] <= modifiedLevelCaps[1]) {
                    LOGGER.warn("Wanderer's boots thresholds should be configured in increasing order. Resetting to default");
                    modifiedLevelCaps = new int[]{0, 1000, 5000, 10000, 50000};
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("NumberFormatException: Improper configuration of wanderer's boots XP thresholds. Resetting to default");
                modifiedLevelCaps = new int[]{0, 1000, 5000, 10000, 50000};
            }

            info.setReturnValue(modifiedLevelCaps);
        }
    }
}
