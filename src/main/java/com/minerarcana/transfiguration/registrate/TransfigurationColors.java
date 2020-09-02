package com.minerarcana.transfiguration.registrate;

import com.minerarcana.transfiguration.item.ITransfiguring;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.color.IItemColor;

import java.util.function.Supplier;

public class TransfigurationColors {
    public static NonNullSupplier<Supplier<IItemColor>> transfiguringTypeColors(int colorLayer) {
        return () -> () -> (itemStack, layer) -> {
            if (layer == colorLayer && itemStack.getItem() instanceof ITransfiguring) {
                return ((ITransfiguring) itemStack.getItem()).getType().getPrimaryColor();
            } else {
                return -1;
            }
        };
    }
}
