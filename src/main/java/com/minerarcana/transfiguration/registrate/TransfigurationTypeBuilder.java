package com.minerarcana.transfiguration.registrate;

import com.minerarcana.transfiguration.item.TransfiguringCatalystItem;
import com.minerarcana.transfiguration.item.TransfiguringDustItem;
import com.minerarcana.transfiguration.item.TransfiguringWandItem;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class TransfigurationTypeBuilder<T extends TransfigurationType, P> extends AbstractBuilder<TransfigurationType, T, P, TransfigurationTypeBuilder<T, P>> {
    private final IntFunction<T> creator;
    private int primaryColor = -1;

    public TransfigurationTypeBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
                                      Class<? super TransfigurationType> registryType, IntFunction<T> creator) {
        super(owner, parent, name, callback, registryType);
        this.creator = creator;
    }

    public TransfigurationTypeBuilder<T, P> primaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public TransfigurationTypeBuilder<T, P> lang(String lang) {
        return this.lang(TransfigurationType::getTranslationKey, lang);
    }

    public ItemBuilder<TransfiguringDustItem, TransfigurationTypeBuilder<T, P>> dust() {
        return this.item("dust", TransfiguringDustItem::new)
                .model((context, provider) -> provider.generated(context, provider.modLoc("item/dust")))
                .color(TransfigurationColors.transfiguringTypeColors(0))
                .lang("%s Dust");
    }

    public ItemBuilder<TransfiguringCatalystItem, TransfigurationTypeBuilder<T, P>> catalyst() {
        return this.item("catalyst", TransfiguringCatalystItem::new)
                .properties(properties -> properties.maxStackSize(1))
                .properties(properties -> properties.maxDamage(256))
                .model((context, provider) -> provider.generated(context, provider.modLoc("item/catalyst")))
                .color(TransfigurationColors.transfiguringTypeColors(0))
                .lang("%s Catalyst");
    }

    public ItemBuilder<TransfiguringWandItem, TransfigurationTypeBuilder<T, P>> wand() {
        return this.item("wand", TransfiguringWandItem::new)
                .properties(properties -> properties.maxDamage(256))
                .model((context, provider) -> provider.generated(context, provider.modLoc("item/wand"),
                        provider.modLoc("item/wand_overlay")))
                .color(TransfigurationColors.transfiguringTypeColors(1))
                .lang("%s Wand");
    }

    public <I extends Item> ItemBuilder<I, TransfigurationTypeBuilder<T, P>> item(
            NonNullBiFunction<Supplier<TransfigurationType>, Item.Properties, I> factory) {
        return this.getOwner().item(this, properties -> factory.apply(this::getEntry, properties));
    }

    public <I extends Item> ItemBuilder<I, TransfigurationTypeBuilder<T, P>> item(
            String append, NonNullBiFunction<Supplier<TransfigurationType>, Item.Properties, I> factory) {
        return this.getOwner().item(this, this.getName() + "_" + append,
                properties -> factory.apply(this::getEntry, properties));
    }

    @Override
    @Nonnull
    protected T createEntry() {
        return creator.apply(primaryColor);
    }

    public static <P> TransfigurationTypeBuilder<TransfigurationType, P> create(AbstractRegistrate<?> owner, P parent,
                                                                                String name, BuilderCallback callback) {
        return new TransfigurationTypeBuilder<>(owner, parent, name, callback, TransfigurationType.class,
                TransfigurationType::new);
    }

    public static <P extends AbstractRegistrate<P>> NonNullBiFunction<String, BuilderCallback,
            TransfigurationTypeBuilder<TransfigurationType, AbstractRegistrate<?>>> entry(Supplier<P> owner) {
        return (name, builderCallback) -> create(owner.get(), owner.get(), name, builderCallback);
    }
}
