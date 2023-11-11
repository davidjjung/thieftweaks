package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.davigj.thief_tweaks.core.ThiefTweaksMod;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerMixin {
    private final LivingEntity mob;

    public CombatTrackerMixin(LivingEntity p_19285_) {
        this.mob = p_19285_;
    }

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void modifyDeathMessage(CallbackInfoReturnable<Component> callbackInfo) {
        Logger LOGGER = LogManager.getLogger(ThiefTweaksMod.MOD_ID);
        CombatTracker combatTracker = (CombatTracker) (Object) this;
        if (TTConfig.COMMON.anonymousDeath.get()) {
            if (mob.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThiefHoodItem) {
                if (!mob.level.isClientSide) {
                    LOGGER.info("Entity that died was actually " + mob.getDisplayName().getString());
                }
                Component deathMessage = Component.translatable("death.anonymous");
                callbackInfo.setReturnValue(deathMessage);
            }
        }
        if (TTConfig.COMMON.anonymousKills.get()) {
            LivingEntity killer = combatTracker.getKiller();
            if (killer != null) {
                if (killer.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThiefHoodItem) {
                    if (!mob.level.isClientSide) {
                        LOGGER.info("Killer was actually " + killer.getDisplayName().getString());
                    }
                    Component modifiedMessage = Component.translatable("death.assassinated", combatTracker.getMob().getDisplayName());
                    callbackInfo.setReturnValue(modifiedMessage);
                }
            }
        }
    }
}
