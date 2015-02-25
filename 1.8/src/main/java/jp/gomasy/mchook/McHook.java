package jp.gomasy.mchook;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = McHook.MODID, version = McHook.VERSION)
public class McHook
{
    public static final String MODID = "jp.gomasy.mchook";
    public static final String VERSION = "1.05";

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
