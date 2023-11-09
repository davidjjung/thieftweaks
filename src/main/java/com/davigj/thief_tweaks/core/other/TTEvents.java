package com.davigj.thief_tweaks.core.other;

import com.davigj.thief_tweaks.core.TTConfig;
import com.davigj.thief_tweaks.core.ThiefTweaksMod;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ThiefTweaksMod.MOD_ID)
public class TTEvents {
    private static final String NBT_PLAYERS = "Players";

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        if (TTConfig.COMMON.doPlayerKillXP.get()) {
            LivingEntity entity = event.getEntity();
            // if this is a valid death case
            if (event.getSource().getEntity() instanceof LivingEntity attacker &&
                    entity instanceof Player victim && attacker != victim) {
                ItemStack stack = attacker.getItemBySlot(EquipmentSlot.HEAD);
                if (stack.getItem() instanceof ThiefHoodItem) {
                    // if double dips are valid, just level the item up and exit the method.
                    if (TTConfig.COMMON.doubleDips.get()) {
                        for (int i = 0; i < TTConfig.COMMON.playerKillXP.get(); i++) {
                            ((ThiefHoodItem) stack.getItem()).levelUp(stack, attacker);
                        }
                    } else {
                        CompoundTag tag = stack.getTag();
                        boolean repeatKill = false;
                        ListTag playerList = null;
                        // double dips are off. so if the item has has player UUIDs on it, check the list and see if the victim's uuid is there
                        if (tag != null && tag.contains(NBT_PLAYERS)) {
                            playerList = stack.getTag().getList(NBT_PLAYERS, 10);
                            for (int i = 0; i < playerList.size(); i++) {
                                if (playerList.getString(i).equals(victim.getStringUUID())) {
                                    repeatKill = true;
                                    break;
                                }
                            }
                        }
                        // if there was no repeat kill, level up and add the victim's uuid to the item's list
                        if (!repeatKill) {
                            if (playerList == null) {
                                playerList = new ListTag();
                            }
                            StringTag victimUUIDTag = StringTag.valueOf(victim.getStringUUID());
                            playerList.add(victimUUIDTag);
                            stack.getTag().put(NBT_PLAYERS, playerList);
                            for (int i = 0; i < TTConfig.COMMON.playerKillXP.get(); i++) {
                                ((ThiefHoodItem) stack.getItem()).levelUp(stack, attacker);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPotionEvent(MobEffectEvent.Added event) {
        if (TTConfig.COMMON.invisRegen.get()) {
            LivingEntity entity = event.getEntity();
            ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
            if (helmet.getItem() instanceof ThiefHoodItem && event.getEffectInstance().getEffect().equals(MobEffects.INVISIBILITY)
                    && !event.isCanceled()) {
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
            }
        }
    }
}