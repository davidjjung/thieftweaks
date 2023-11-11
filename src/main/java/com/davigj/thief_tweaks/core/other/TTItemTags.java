package com.davigj.thief_tweaks.core.other;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TTItemTags {
    public static final TagKey<Item> TOOLS_KNIVES = forgeItemTag("tools/knives");
    private static TagKey<Item> forgeItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }
}
