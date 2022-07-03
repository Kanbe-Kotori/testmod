package cn.nulladev.testmod;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Testmod.MODID)
public class Testmod {

    public static final String MODID = "testmod";

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Testmod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Registry.INSTANCE.registerEvents();
    }

    public void setup(final FMLClientSetupEvent event) {
        proxy.setup();
    }
}
