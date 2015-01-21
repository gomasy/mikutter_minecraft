package jp.gomasy.mchook;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = McHook.MODID, version = McHook.VERSION)
public class McHook
{
    public static final String MODID = "jp.gomasy.mchook";
    public static final String VERSION = "1.03";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        new MultiHookServer().start();
    }
}
