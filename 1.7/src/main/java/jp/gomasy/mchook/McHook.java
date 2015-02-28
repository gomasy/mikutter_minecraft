package jp.gomasy.mchook;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = McHook.MODID, version = McHook.VERSION)
public class McHook
{
    public static final String MODID = "mchook";
    public static final String VERSION = "1.07";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        new MultiHookServer().start();
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        new HookClient(event).start();
    }
}
