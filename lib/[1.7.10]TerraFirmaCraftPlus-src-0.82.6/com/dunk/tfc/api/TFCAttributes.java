package com.dunk.tfc.api;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class TFCAttributes 
{
	public static final UUID OVERBURDENED_UUID = UUID.fromString("772A6B8D-DA3E-4C1C-8813-96EA6097278D");
	public static final AttributeModifier OVERBURDENED = new AttributeModifier(OVERBURDENED_UUID, "Overburdened speed penalty", -1.0D, 2).setSaved(false);
	public static final UUID THIRSTY_UUID = UUID.fromString("772A6B8D-DA3E-4C1C-9999-96EA6097278D");
	public static final AttributeModifier THIRSTY = new AttributeModifier(THIRSTY_UUID, "Thirsty speed penalty", -0.3D, 2).setSaved(false);
	public static final UUID ROBE_NEGATIVE_3_UUID = UUID.fromString("c5290202-a36a-11e9-a2a3-2a2ae2dbcce4");
	public static final AttributeModifier ROBE_NEGATIVE_3 = new AttributeModifier(ROBE_NEGATIVE_3_UUID, "Robe Speed penalty 3", -0.03D, 2).setSaved(false);
	public static final UUID ROBE_NEGATIVE_5_UUID = UUID.fromString("c52906c6-a36a-11e9-a2a3-2a2ae2dbcce4");
	public static final AttributeModifier ROBE_NEGATIVE_5 = new AttributeModifier(ROBE_NEGATIVE_5_UUID, "Robe Speed penalty 5", -0.05D, 2).setSaved(false);
	public static final UUID ROBE_NEGATIVE_8_UUID = UUID.fromString("c5290856-a36a-11e9-a2a3-2a2ae2dbcce4");
	public static final AttributeModifier ROBE_NEGATIVE_8 = new AttributeModifier(ROBE_NEGATIVE_8_UUID, "Robe Speed penalty 8", -0.08D, 2).setSaved(false);
	public static final UUID ROBE_NEGATIVE_10_UUID = UUID.fromString("29555b7c-a36b-11e9-a2a3-2a2ae2dbcce4");
	public static final AttributeModifier ROBE_NEGATIVE_10 = new AttributeModifier(ROBE_NEGATIVE_10_UUID, "Robe Speed penalty 10", -0.1D, 2).setSaved(false);
	public static final UUID BOOTS_2_UUID = UUID.fromString("7dff850a-aa17-4f8f-913f-a4b1132e5923");
	public static final AttributeModifier BOOTS_2 = new AttributeModifier(BOOTS_2_UUID, "Boots Speed boost 2", 0.02D, 2).setSaved(false);
	public static final UUID SOCKS_2_UUID = UUID.fromString("fd4223ec-22a1-11ea-978f-2e728ce88125");
	public static final AttributeModifier SOCKS_2 = new AttributeModifier(SOCKS_2_UUID, "Socks Speed boost 2", 0.02D, 2).setSaved(false);
	public static final UUID BOOTS_5_UUID = UUID.fromString("13a774e3-963c-47e4-ad68-442c19c20895");
	public static final AttributeModifier BOOTS_5 = new AttributeModifier(BOOTS_5_UUID, "Boots Speed boost 5", 0.05D, 2).setSaved(false);
	public static final UUID BOOTS_7_UUID = UUID.fromString("7f436a2a-ffc7-4fed-bb9f-66dcfe7fea02");
	public static final AttributeModifier BOOTS_7 = new AttributeModifier(BOOTS_7_UUID, "Boots Speed boost 7", 0.07D, 2).setSaved(false);
	public static final UUID BOOTS_10_UUID = UUID.fromString("b0263297-23d7-4cfa-82c8-78a360087943");
	public static final AttributeModifier BOOTS_10 = new AttributeModifier(BOOTS_10_UUID, "Boots Speed boost 10", 0.1D, 2).setSaved(false);
	public static final UUID BOOTS_12_UUID = UUID.fromString("dd9bebc9-a779-476a-b050-f489d45e3b70");
	public static final AttributeModifier BOOTS_12 = new AttributeModifier(BOOTS_12_UUID, "Boots Speed boost 12", 0.12D, 2).setSaved(false);
	public static final UUID BOOTS_15_UUID = UUID.fromString("8b2c20d6-03d1-453b-a0e6-c117afdd483a");
	public static final AttributeModifier BOOTS_15 = new AttributeModifier(BOOTS_15_UUID, "Boots Speed boost 15", 0.15D, 2).setSaved(false);
	public static final UUID BOOTS_17_UUID = UUID.fromString("70cbc207-9ffc-4740-bfec-5b8f3d67bb57");
	public static final AttributeModifier BOOTS_17 = new AttributeModifier(BOOTS_17_UUID, "Boots Speed boost 17", 0.17D, 2).setSaved(false);
	public static final UUID BOOTS_20_UUID = UUID.fromString("b529b7f2-ee93-441d-b975-e4bc116e3f80");
	public static final AttributeModifier BOOTS_20 = new AttributeModifier(BOOTS_20_UUID, "Boots Speed boost 20", 0.2D, 2).setSaved(false);
	public static final UUID BOOTS_22_UUID = UUID.fromString("722ca179-d3c0-49e5-8097-fcfca0224ec7");
	public static final AttributeModifier BOOTS_22 = new AttributeModifier(BOOTS_22_UUID, "Boots Speed boost 22", 0.22D, 2).setSaved(false);
	public static final UUID BOOTS_25_UUID = UUID.fromString("0a869ca1-248f-4b62-838a-5f8c143bcde2");
	public static final AttributeModifier BOOTS_25 = new AttributeModifier(BOOTS_25_UUID, "Boots Speed boost 25", 0.25D, 2).setSaved(false);
	public static final UUID BOOTS_27_UUID = UUID.fromString("1d79bd8d-307c-4f07-bc12-463f9886e7dc");
	public static final AttributeModifier BOOTS_27 = new AttributeModifier(BOOTS_27_UUID, "Boots Speed boost 27", 0.27D, 2).setSaved(false);
	public static final UUID WET_10_UUID = UUID.fromString("6819345a-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_10 = new AttributeModifier(WET_10_UUID,"Wet equipment speed penalty 10",-0.1D,2).setSaved(false);
	public static final UUID WET_15_UUID = UUID.fromString("68193734-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_15 = new AttributeModifier(WET_15_UUID,"Wet equipment speed penalty 15",-0.15D,2).setSaved(false);
	public static final UUID WET_20_UUID = UUID.fromString("68193b3a-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_20 = new AttributeModifier(WET_20_UUID,"Wet equipment speed penalty 20",-0.20D,2).setSaved(false);
	public static final UUID WET_25_UUID = UUID.fromString("68193c8e-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_25 = new AttributeModifier(WET_25_UUID,"Wet equipment speed penalty 25",-0.25D,2).setSaved(false);
	public static final UUID WET_30_UUID = UUID.fromString("68193e8c-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_30 = new AttributeModifier(WET_30_UUID,"Wet equipment speed penalty 30",-0.3D,2).setSaved(false);
	public static final UUID WET_35_UUID = UUID.fromString("68193ff4-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_35 = new AttributeModifier(WET_35_UUID,"Wet equipment speed penalty 35",-0.35D,2).setSaved(false);
	public static final UUID WET_40_UUID = UUID.fromString("6819412a-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_40 = new AttributeModifier(WET_40_UUID,"Wet equipment speed penalty 40",-0.4D,2).setSaved(false);
	public static final UUID WET_45_UUID = UUID.fromString("6819426a-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_45 = new AttributeModifier(WET_45_UUID,"Wet equipment speed penalty 45",-0.45D,2).setSaved(false);
	public static final UUID WET_50_UUID = UUID.fromString("681944cc-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_50 = new AttributeModifier(WET_50_UUID,"Wet equipment speed penalty 50",-0.5D,2).setSaved(false);
	public static final UUID WET_55_UUID = UUID.fromString("68194620-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_55 = new AttributeModifier(WET_55_UUID,"Wet equipment speed penalty 55",-0.55D,2).setSaved(false);
	public static final UUID WET_60_UUID = UUID.fromString("6819488c-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_60 = new AttributeModifier(WET_60_UUID,"Wet equipment speed penalty 60",-0.6D,2).setSaved(false);
	public static final UUID WET_65_UUID = UUID.fromString("681949e0-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_65 = new AttributeModifier(WET_65_UUID,"Wet equipment speed penalty 65",-0.65D,2).setSaved(false);
	public static final UUID WET_70_UUID = UUID.fromString("68194b16-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_70 = new AttributeModifier(WET_70_UUID,"Wet equipment speed penalty 70",-0.7D,2).setSaved(false);
	public static final UUID WET_75_UUID = UUID.fromString("68194c4c-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_75 = new AttributeModifier(WET_75_UUID,"Wet equipment speed penalty 75",-0.75D,2).setSaved(false);
	public static final UUID WET_80_UUID = UUID.fromString("68194d82-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_80 = new AttributeModifier(WET_80_UUID,"Wet equipment speed penalty 80",-0.8D,2).setSaved(false);
	public static final UUID WET_85_UUID = UUID.fromString("68195020-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_85 = new AttributeModifier(WET_85_UUID,"Wet equipment speed penalty 85",-0.85D,2).setSaved(false);
	public static final UUID WET_90_UUID = UUID.fromString("68195192-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_90 = new AttributeModifier(WET_90_UUID,"Wet equipment speed penalty 90",-0.9D,2).setSaved(false);
	public static final UUID WET_95_UUID = UUID.fromString("681952d2-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_95 = new AttributeModifier(WET_95_UUID,"Wet equipment speed penalty 95",-0.95D,2).setSaved(false);
	public static final UUID WET_100_UUID = UUID.fromString("68195412-66b2-11e9-a923-1681be663d3e");
	public static final AttributeModifier WET_100 = new AttributeModifier(WET_100_UUID,"Wet equipment speed penalty 100",-1D,2).setSaved(false);
	public static final UUID WOUND_15_UUID = UUID.fromString("fb16e430-4912-11ea-b77f-2e728ce88125");
	public static final AttributeModifier WOUND_15 = new AttributeModifier(WOUND_15_UUID,"Wound Speed penalty 15",-0.45D,2).setSaved(false);
	public static final UUID WOUND_30_UUID = UUID.fromString("1a23e63e-4913-11ea-b77f-2e728ce88125");
	public static final AttributeModifier WOUND_30 = new AttributeModifier(WOUND_30_UUID,"Wound Speed penalty 30",-0.60D,2).setSaved(false);
	public static final UUID WOUND_45_UUID = UUID.fromString("1a23e90e-4913-11ea-b77f-2e728ce88125");
	public static final AttributeModifier WOUND_45 = new AttributeModifier(WOUND_45_UUID,"Wound Speed penalty 45",-0.75D,2).setSaved(false);
	public static final UUID WOUND_60_UUID = UUID.fromString("1a23eaa8-4913-11ea-b77f-2e728ce88125");
	public static final AttributeModifier WOUND_60 = new AttributeModifier(WOUND_60_UUID,"Wound Speed penalty 60",-0.90D,2).setSaved(false);
	public static final UUID WOUND_75_UUID = UUID.fromString("68d51032-4913-11ea-b77f-2e728ce88125");
	public static final AttributeModifier WOUND_75 = new AttributeModifier(WOUND_75_UUID,"Wound Speed penalty 75",-1.1D,2).setSaved(false);
	public static final UUID WOUND_90_UUID = UUID.fromString("68d512b2-4913-11ea-b77f-2e728ce88125");
	public static final AttributeModifier WOUND_90 = new AttributeModifier(WOUND_90_UUID,"Wound Speed penalty 90",-1.25D,2).setSaved(false);
}
