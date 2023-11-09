package com.davigj.thief_tweaks.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TTConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> doPlayerKillXP;
        public final ForgeConfigSpec.ConfigValue<Integer> playerKillXP;
        public final ForgeConfigSpec.ConfigValue<Boolean> doubleDips;
        public final ForgeConfigSpec.ConfigValue<Boolean> invisRegen;
        public final ForgeConfigSpec.ConfigValue<Boolean> anonymousKills;
        public final ForgeConfigSpec.ConfigValue<Boolean> anonymousDeath;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("tweaks");
            doPlayerKillXP = builder.comment("Whether killing other players offers hoods bonus levels").define("Do player kills level up XP", false);
            playerKillXP = builder.comment("Amount of extra levels provided to a thief's hood upon killing other players").define("Player kill hood XP", 10);
            doubleDips = builder.comment("Killing the same player repeatedly still gives XP").define("Double dip player kills", false);
            invisRegen = builder.comment("Obtaining the Invisibility effect also grants some amount of Regeneration").define("Regenerate health while invisible", false);
            anonymousKills = builder.comment("Getting killed by an entity wearing a hooded mob does not reveal the killer's identity").define("Anonymous kills", true);
            anonymousDeath = builder.comment("Dying while wearing a hood does not reveal the victim's identity").define("Anonymous death", true);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final TTConfig.Common COMMON;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TTConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
