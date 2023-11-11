package com.davigj.thief_tweaks.core.other;

import com.davigj.thief_tweaks.core.TTConfig;
import com.davigj.thief_tweaks.core.ThiefTweaksMod;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

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
            if (helmet.getItem() instanceof ThiefHoodItem hoodItem && event.getEffectInstance().getEffect().equals(MobEffects.INVISIBILITY)
                    && !event.isCanceled() && !entity.hasEffect(MobEffects.INVISIBILITY)) {
                int uses = Math.round(helmet.getOrCreateTag().getFloat("ThiefHoodUses"));
                int level = hoodItem.getIncreaseForUses(uses);
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60 * level, 0));
            }
        }
    }

    @SubscribeEvent
    public static void onHurtEvent(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        Entity directSource = source.getDirectEntity();
        if (attacker instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThiefHoodItem hoodItem) {
            int uses = Math.round(living.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTag().getFloat("ThiefHoodUses"));
            int level = hoodItem.getIncreaseForUses(uses);
            if (!source.isMagic() && !source.isProjectile()) {
                if (living.getMainHandItem().is(TTItemTags.TOOLS_KNIVES)) {
                    if (TTConfig.COMMON.knifeBuff.get()) {
                        event.setAmount(event.getAmount() * (float) (1 + (.05 + (0.05 * level))));
                    }
                    if (ModList.get().isLoaded("farmersdelight")) {
                        if (TTConfig.COMMON.backstabbingBonus.get() && BackstabbingEnchantment.isLookingBehindTarget(event.getEntity(), source.getSourcePosition())) {
                            if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.BACKSTABBING.get(), living.getMainHandItem()) > 0) {
                                event.setAmount(event.getAmount() * (float) (1 + (.05 + (0.05 * level))));
                            }
                        }
                    }
                }
            } else if (TTConfig.COMMON.knifeBuff.get() && directSource != null && directSource.getType().toString().equals("caverns_and_chasms:kunai")) {
                event.setAmount(event.getAmount() * (float) (1 + (.05 + (0.05 * level))));
            }
        }
    }

}