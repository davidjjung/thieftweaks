package com.davigj.thief_tweaks.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TTConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> anonymousKills;
        public final ForgeConfigSpec.ConfigValue<Boolean> anonymousDeath;
        public final ForgeConfigSpec.ConfigValue<Boolean> doPlayerKillXP;
        public final ForgeConfigSpec.ConfigValue<Integer> playerKillXP;
        public final ForgeConfigSpec.ConfigValue<Boolean> doubleDips;
        public final ForgeConfigSpec.ConfigValue<Boolean> playerKillsOnly;
        public final ForgeConfigSpec.ConfigValue<Boolean> invisRegen;
        public final ForgeConfigSpec.ConfigValue<Boolean> knifeBuff;
        public final ForgeConfigSpec.ConfigValue<Boolean> backstabbingBonus;

        public final ForgeConfigSpec.ConfigValue<Boolean> customHoodXPThresholds;
        public final ForgeConfigSpec.ConfigValue<Integer> hoodXPThreshold1;
        public final ForgeConfigSpec.ConfigValue<Integer> hoodXPThreshold2;
        public final ForgeConfigSpec.ConfigValue<Integer> hoodXPThreshold3;
        public final ForgeConfigSpec.ConfigValue<Integer> hoodXPThreshold4;
        public final ForgeConfigSpec.ConfigValue<Boolean> customPouchXPThresholds;
        public final ForgeConfigSpec.ConfigValue<Integer> pouchXPThreshold1;
        public final ForgeConfigSpec.ConfigValue<Integer> pouchXPThreshold2;
        public final ForgeConfigSpec.ConfigValue<Integer> pouchXPThreshold3;
        public final ForgeConfigSpec.ConfigValue<Integer> pouchXPThreshold4;
        public final ForgeConfigSpec.ConfigValue<Boolean> customBeltXPThresholds;
        public final ForgeConfigSpec.ConfigValue<Integer> beltXPThreshold1;
        public final ForgeConfigSpec.ConfigValue<Integer> beltXPThreshold2;
        public final ForgeConfigSpec.ConfigValue<Integer> beltXPThreshold3;
        public final ForgeConfigSpec.ConfigValue<Integer> beltXPThreshold4;
        public final ForgeConfigSpec.ConfigValue<Boolean> customBootsXPThresholds;
        public final ForgeConfigSpec.ConfigValue<Integer> bootsXPThreshold1;
        public final ForgeConfigSpec.ConfigValue<Integer> bootsXPThreshold2;
        public final ForgeConfigSpec.ConfigValue<Integer> bootsXPThreshold3;
        public final ForgeConfigSpec.ConfigValue<Integer> bootsXPThreshold4;

        // /summon zombie ~ ~ ~ {ArmorItems:[{},{},{},{id:"environmental:thief_hood",Count:1b}]}

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("hood_tweaks");
            builder.push("anonymity");
            anonymousKills = builder.comment("Getting killed by an entity wearing a hooded mob does not reveal the killer's identity").define("Anonymous kills", true);
            anonymousDeath = builder.comment("Dying while wearing a hood does not reveal the victim's identity").define("Anonymous death", true);
            builder.pop();
            builder.push("player_kills");
            doPlayerKillXP = builder.comment("Whether killing other players offers hoods bonus levels").define("Do player kills level up XP", false);
            playerKillXP = builder.comment("Amount of extra levels provided to a thief's hood upon killing other players").define("Player kill hood XP", 1);
            doubleDips = builder.comment("Killing the same player repeatedly still gives XP").define("Double dip player kills", false);
            playerKillsOnly = builder.comment("The hood obtains XP from ONLY killing players").define("Player kills only", false);
            builder.pop();
            builder.push("misc");
            invisRegen = builder.comment("Obtaining the Invisibility effect also grants some amount of Regeneration")
                    .define("Regenerate health upon turning invisible", false);
            knifeBuff = builder.comment("Hood wearers deal extra damage with knives").define("fun with knives", false);
            backstabbingBonus = builder.comment("Hood wearers deal extra damage with knives with the Backstabbing enchantment from Farmer's Delight, when the victim's back is turned").define("backstab bonus", false);
            builder.pop();
            builder.push("explorer_armor_level_caps");
            customHoodXPThresholds = builder.comment("Custom XP level caps are enabled for the thief's hood").define("Custom hood level caps", false);
            hoodXPThreshold1 = builder.comment("Amount of points needed to obtain the hood's first upgrade").define("Hood XP threshold 1", 10);
            hoodXPThreshold2 = builder.comment("Amount of points needed to obtain the hood's second upgrade").define("Hood XP threshold 2", 50);
            hoodXPThreshold3 = builder.comment("Amount of points needed to obtain the hood's third upgrade").define("Hood XP threshold 3", 100);
            hoodXPThreshold4 = builder.comment("Amount of points needed to obtain the hood's final upgrade").define("Hood XP threshold 4", 500);
            customPouchXPThresholds = builder.comment("Custom XP level caps are enabled for healer's pouch").define("Custom pouch level caps", false);
            pouchXPThreshold1 = builder.comment("Amount of points needed to obtain the pouch's first upgrade").define("Pouch XP threshold 1", 10);
            pouchXPThreshold2 = builder.comment("Amount of points needed to obtain the pouch's second upgrade").define("Pouch XP threshold 2", 50);
            pouchXPThreshold3 = builder.comment("Amount of points needed to obtain the pouch's third upgrade").define("Pouch XP threshold 3", 100);
            pouchXPThreshold4 = builder.comment("Amount of points needed to obtain the pouch's final upgrade").define("Pouch XP threshold 4", 250);
            customBeltXPThresholds = builder.comment("Custom XP level caps are enabled for architect's belt").define("Custom belt level caps", false);
            beltXPThreshold1 = builder.comment("Amount of points needed to obtain the belt's first upgrade").define("Belt XP threshold 1", 100);
            beltXPThreshold2 = builder.comment("Amount of points needed to obtain the belt's second upgrade").define("Belt XP threshold 2", 500);
            beltXPThreshold3 = builder.comment("Amount of points needed to obtain the belt's third upgrade").define("Belt XP threshold 3", 1000);
            beltXPThreshold4 = builder.comment("Amount of points needed to obtain the belt's final upgrade").define("Belt XP threshold 4", 2500);
            customBootsXPThresholds = builder.comment("Custom XP level caps are enabled for wanderer's boots").define("Custom boot level caps", false);
            bootsXPThreshold1 = builder.comment("Amount of points needed to obtain the boots' first upgrade").define("Boots XP threshold 1", 1000);
            bootsXPThreshold2 = builder.comment("Amount of points needed to obtain the boots' second upgrade").define("Boots XP threshold 2", 5000);
            bootsXPThreshold3 = builder.comment("Amount of points needed to obtain the boots' third upgrade").define("Boots XP threshold 3", 10000);
            bootsXPThreshold4 = builder.comment("Amount of points needed to obtain the boots' final upgrade").define("Boots XP threshold 4", 50000);
            builder.pop();
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
