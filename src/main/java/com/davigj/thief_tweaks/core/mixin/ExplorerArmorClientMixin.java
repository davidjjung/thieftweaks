package com.davigj.thief_tweaks.core.mixin;

import com.davigj.thief_tweaks.core.TTConfig;
import com.teamabnormals.environmental.common.item.explorer.ExplorerArmorItem;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ExplorerArmorItem.class)
public class ExplorerArmorClientMixin {
    @OnlyIn(Dist.CLIENT)
    @Inject(method = "appendHoverText", at = @At(value = "RETURN"), remap = false)
    private void modifyAppendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
        if (TTConfig.COMMON.invisRegen.get() && stack.getItem() instanceof ThiefHoodItem hoodItem) {
            tooltip.add(Component.translatable("item.environmental.thief_hood.on_invisible").withStyle(ChatFormatting.GRAY));
            MutableComponent component = Component.translatable(MobEffects.REGENERATION.getDescriptionId());
            int uses = Math.round(stack.getOrCreateTag().getFloat("ThiefHoodUses"));
            int level = hoodItem.getIncreaseForUses(uses);
            component = Component.translatable("potion.withDuration", component, MobEffectUtil.formatDuration(
                    new MobEffectInstance(MobEffects.REGENERATION, 60 * level), 1.0F));
            tooltip.add(component.withStyle(MobEffects.REGENERATION.getCategory().getTooltipFormatting()));
        }
    }
}
