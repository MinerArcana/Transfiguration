package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.item.TransfiguringItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TransfigurationItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Transfiguration.ID);

    public static final RegistryObject<TransfiguringItem> NETHERI_DUST = ITEMS.register("netheri_dust", () ->
            new TransfiguringItem(Transfiguration.NETHERI, new Item.Properties().group(ItemGroup.MISC)));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
