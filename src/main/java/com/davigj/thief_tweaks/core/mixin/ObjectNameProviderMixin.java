package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Mixin(targets = "snownee.jade.addon.core.ObjectNameProvider")
public class ObjectNameProviderMixin {
    @Inject(method = "appendTooltip(Lsnownee/jade/api/ITooltip;Lsnownee/jade/api/EntityAccessor;Lsnownee/jade/api/config/IPluginConfig;)V",
            at = @At("HEAD"), cancellable = true, remap = false)
    public void noName(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config, CallbackInfo ci) {
        if (TTConfig.COMMON.jadeCompat.get() && accessor.getEntity() instanceof LivingEntity living &&
                living.getItemBySlot(EquipmentSlot.HEAD).getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
            ci.cancel();
        }
    }
}
