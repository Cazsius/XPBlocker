package com.mrtrollnugnug.xpBlocker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = XPBlocker.MODID, version = XPBlocker.VERSION, name = XPBlocker.MODNAME)
public class XPBlocker
{
    public static final String MODID = "xpblocker";
    public static final String VERSION = "1.3";
    public static final String MODNAME = "XP Blocker";
    
    static ArrayList<ItemStack> oreItems = new ArrayList<ItemStack>();
    
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(new EventManager());
    	MinecraftForge.EVENT_BUS.register(new ConfigManager());
    	ConfigManager.init(event.getSuggestedConfigurationFile());
    }
    
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event){
    	for (int i = 0; i < GameData.getItemRegistry().getKeys().size(); i ++){
    		ItemStack stack = new ItemStack((Item)GameData.getItemRegistry().getObject((ResourceLocation) GameData.getItemRegistry().getKeys().toArray()[i]));
    		int[] ids = OreDictionary.getOreIDs(stack);
    		System.out.println("ids"+ids);
    		if (ids.length > 0){
    			for (int j = 0; j < ids.length; j ++){
    				if (OreDictionary.getOreName(ids[j]).length() > 3){
    					if (OreDictionary.getOreName(ids[j]).substring(0, 3).equals("ore")){
	    					oreItems.add(FurnaceRecipes.instance().getSmeltingResult(stack));
	    				}
    				}
    			}
    		}
    	}
    }
    
    public static boolean containsMatch(boolean strict, List<ItemStack> items, ItemStack stack){
    	for (int i = 0; i < items.size(); i ++){
    		if (OreDictionary.itemMatches(items.get(i), stack, strict)){
    			return true;
    		}
    	}
    	return false;
    }
}
