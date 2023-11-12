package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.teamabnormals.environmental.common.item.explorer.ExplorerArmorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExplorerArmorItem.class)
public class ExplorerArmorItemMixin {

    @Inject(method = "getDescription*", at = @At("HEAD"), cancellable = true, remap = false)
    private void modifyGetDescription(CallbackInfoReturnable<MutableComponent> info) {
        ExplorerArmorItem explorerArmorItem = (ExplorerArmorItem) (Object) this;
        if (TTConfig.COMMON.doPlayerKillXP.get() && explorerArmorItem.getDescriptionId().equals("item.environmental.thief_hood")) {
            // players do grant xp. it is unknown if monsters *also* grant xp
            if (TTConfig.COMMON.playerKillsOnly.get()) {
                info.setReturnValue(Component.translatable("item.environmental.thief_hood.alt_desc"));
            } else {
                // players grant xp, but now it is certainly not limited to player kills
                info.setReturnValue(Component.translatable("item.environmental.thief_hood.alt_desc2"));
            }
        }
    }
}
