package cn.nulladev.testmod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class Registry {

    public static final Registry INSTANCE = new Registry();

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Testmod.MODID);

    public static final RegistryObject<EntityType<EntityMagic>> MAGIC = ENTITY_TYPES.register("magic", () -> EntityType.Builder.of(EntityMagic::new, MobCategory.MISC).sized(0, 0).fireImmune().build("magic"));

    public void registerEvents() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEM.register(modBus);
        ENTITY_TYPES.register(modBus);
    }
}

class ItemRegistry {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Testmod.MODID);

    public static RegistryObject<Item> WAND = register("wand", ItemWand::new);

    public static <V extends Item> RegistryObject<V> register(String name, Function<Item.Properties, V> sup) {
        return ITEM.register(name, () -> sup.apply(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    }
}


