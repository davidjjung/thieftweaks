package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.davigj.thief_tweaks.core.ThiefTweaksMod;
import com.teamabnormals.environmental.common.item.explorer.HealerPouchItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HealerPouchItem.class)
public class HealerPouchMixin {
    @Inject(method = "getLevelCaps", at = @At("HEAD"), cancellable = true, remap = false)
    private void modifyLevelCaps(CallbackInfoReturnable<int[]> info) {
        if (TTConfig.COMMON.customPouchXPThresholds.get()) {
            Logger LOGGER = LogManager.getLogger(ThiefTweaksMod.MOD_ID);
            int[] modifiedLevelCaps = new int[5];

            try {
                modifiedLevelCaps[0] = 0;
                modifiedLevelCaps[1] = TTConfig.COMMON.pouchXPThreshold1.get();
                modifiedLevelCaps[2] = TTConfig.COMMON.pouchXPThreshold2.get();
                modifiedLevelCaps[3] = TTConfig.COMMON.pouchXPThreshold3.get();
                modifiedLevelCaps[4] = TTConfig.COMMON.pouchXPThreshold4.get();

                if (modifiedLevelCaps[4] <= modifiedLevelCaps[3]
                        || modifiedLevelCaps[3] <= modifiedLevelCaps[2]
                        || modifiedLevelCaps[2] <= modifiedLevelCaps[1]) {
                    LOGGER.warn("Healer's pouch thresholds should be configured in increasing order. Resetting to default");
                    modifiedLevelCaps = new int[]{0, 10, 50, 100, 250};
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("NumberFormatException: Improper configuration of XP thresholds. Resetting to default");
                modifiedLevelCaps = new int[]{0, 10, 50, 100, 250};
            }

            info.setReturnValue(modifiedLevelCaps);
        }
    }
}
