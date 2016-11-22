package elucent.xpBlocker;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class EventManager {
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event){
		if (event.getEntity() instanceof EntityXPOrb){
			if (!event.getEntity().getEntityData().hasKey("XPBLOCKER_protected")){
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerUseBlock(PlayerInteractEvent.RightClickBlock event){
		if (event.getItemStack() != null){
			if (event.getItemStack().getItem() == Items.EXPERIENCE_BOTTLE){
				event.getItemStack().stackSize --;
				int xp = 5;
				for (int i = 0; i < xp; i ++){
					if (!event.getWorld().isRemote){
						EntityXPOrb orb = new EntityXPOrb(event.getWorld(), event.getPos().getX()+0.5, event.getPos().getY()+0.5, event.getPos().getZ()+0.5, 2);
						orb.getEntityData().setBoolean("XPBLOCKER_protected", true);
						event.getWorld().spawnEntityInWorld(orb);
					}
				}
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerUse(PlayerInteractEvent.RightClickItem event){
		if (event.getItemStack() != null){
			if (event.getItemStack().getItem() == Items.EXPERIENCE_BOTTLE){
				int xp = 5;
				for (int i = 0; i < xp; i ++){
					if (!event.getWorld().isRemote){
						EntityXPOrb orb = new EntityXPOrb(event.getWorld(), event.getPos().getX()+0.5, event.getPos().getY()+0.5, event.getPos().getZ()+0.5, 2);
						orb.getEntityData().setBoolean("XPBLOCKER_protected", true);
						event.getWorld().spawnEntityInWorld(orb);
					}
				}
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event){
		if (event.getExpToDrop() > 0){
			int xp = event.getExpToDrop();
			for (int i = 0; i < xp; i ++){
				if (!event.getWorld().isRemote){
					EntityXPOrb orb = new EntityXPOrb(event.getWorld(), event.getPos().getX()+0.5, event.getPos().getY()+0.5, event.getPos().getZ()+0.5, 2);
					orb.getEntityData().setBoolean("XPBLOCKER_protected", true);
					event.getWorld().spawnEntityInWorld(orb);
				}
			}
			event.setExpToDrop(0);
		}
	}
	
	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event){
		if (isOre(event.smelting)){
			int xp = (int) (event.smelting.getItem().getSmeltingExperience(event.smelting)*event.smelting.stackSize);
			event.player.addExperience(xp);
		}
	}
	
	public boolean isOre(ItemStack stack){
		if (Main.containsMatch(true, Main.oreItems, stack)){
			return true;
		}
		return false;
	}
}
