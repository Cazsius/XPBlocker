package com.mrtrollnugnug.xpBlocker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(XPBlocker.MODID)
public class XPBlocker
{
    public static final String MODID = "xpblocker";

    public XPBlocker(){
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		}

    private void preInit(FMLCommonSetupEvent event){
    	MinecraftForge.EVENT_BUS.register(new EventManager());
    	MinecraftForge.EVENT_BUS.register(new ConfigManager());
    }
}
