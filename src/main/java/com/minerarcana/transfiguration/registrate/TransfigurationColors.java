package com.minerarcana.transfiguration.registrate;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.item.ITransfiguring;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.color.item.ItemColor;

import java.util.function.Supplier;

public class TransfigurationColors {
    public static NonNullSupplier<Supplier<ItemColor>> transfiguringTypeColors(int colorLayer) {
        return () -> () -> (itemStack, layer) -> {
            if (layer == colorLayer && itemStack.getItem() instanceof ITransfiguring transfiguring) {
                TransfigurationType type = transfiguring.getType(itemStack);
                if (type != null) {
                    return type.getPrimaryColor();
                }
            }
            return -1;
        };
    }
}
