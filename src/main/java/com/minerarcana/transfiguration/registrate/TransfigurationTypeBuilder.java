package com.minerarcana.transfiguration.registrate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.TransfiguringKeyword;
import com.minerarcana.transfiguration.item.TransfiguringCatalystItem;
import com.minerarcana.transfiguration.item.TransfiguringDustItem;
import com.minerarcana.transfiguration.item.TransfiguringWandItem;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeBuilder;
import com.mojang.datafixers.util.Function4;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TransfigurationTypeBuilder<T extends TransfigurationType, P> extends AbstractBuilder<TransfigurationType, T, P, TransfigurationTypeBuilder<T, P>> {
    private final Function4<Integer, Integer, List<TransfiguringKeyword>, List<Supplier<TransfigurationType>>, T> creator;
    private int primaryColor = -1;
    private int secondaryColor = -1;
    private final List<TransfiguringKeyword> keywords;
    private final List<Supplier<TransfigurationType>> fallback;

    public TransfigurationTypeBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
                                      Class<? super TransfigurationType> registryType,
                                      Function4<Integer, Integer, List<TransfiguringKeyword>, List<Supplier<TransfigurationType>>, T> creator) {
        super(owner, parent, name, callback, registryType);
        this.creator = creator;
        this.keywords = Lists.newArrayList();
        this.fallback = Lists.newArrayList();
    }

    public TransfigurationTypeBuilder<T, P> primaryColor(DyeColor primaryColor) {
        return this.primaryColor(primaryColor.getColorValue());
    }

    public TransfigurationTypeBuilder<T, P> primaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public TransfigurationTypeBuilder<T, P> secondaryColor(DyeColor secondaryColor) {
        return this.secondaryColor(secondaryColor.getColorValue());
    }

    public TransfigurationTypeBuilder<T, P> secondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    public TransfigurationTypeBuilder<T, P> keyword(TransfiguringKeyword... keywords) {
        this.keywords.addAll(Arrays.asList(keywords));
        return this;
    }

    @SafeVarargs
    public final TransfigurationTypeBuilder<T, P> fallback(Supplier<TransfigurationType>... includes) {
        this.fallback.addAll(Arrays.asList(includes));
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
                .properties(properties -> properties.stacksTo(1))
                .properties(properties -> properties.durability(256))
                .model((context, provider) -> provider.generated(context, provider.modLoc("item/catalyst")))
                .color(TransfigurationColors.transfiguringTypeColors(0))
                .lang("%s Catalyst")
                .recipe((context, provider) -> DustRecipeBuilder.create(this.getEntry())
                        .withFluid(FluidTags.WATER)
                        .withOutput(context.get().getDefaultInstance())
                        .build(provider)
                );
    }

    public TransfigurationTypeBuilder<T, P> defaultCatalyst() {
        return catalyst().build();
    }

    public ItemBuilder<TransfiguringWandItem, TransfigurationTypeBuilder<T, P>> wand() {
        return this.item("wand", TransfiguringWandItem::new)
                .properties(properties -> properties.durability(256))
                .model((context, provider) -> provider.generated(context, provider.modLoc("item/wand"),
                        provider.modLoc("item/wand_overlay")))
                .color(TransfigurationColors.transfiguringTypeColors(1))
                .lang("%s Wand")
                .recipe((context, provider) -> {
                    ResourceLocation catalystName = new ResourceLocation(this.getOwner().getModid(), this.getName() + "_catalyst");
                    Item catalyst = ForgeRegistries.ITEMS.getValue(catalystName);
                    if (catalyst == null) {
                        throw new IllegalStateException("Failed to Find Catalyst for name: " + catalystName);
                    } else {
                        ShapedRecipeBuilder.shaped(context.get())
                                .pattern("C")
                                .pattern("I")
                                .pattern("R")
                                .define('C', catalyst)
                                .define('I', Tags.Items.INGOTS)
                                .define('R', Items.END_ROD)
                                .unlockedBy("has_item", RegistrateRecipeProvider.hasItem(catalyst))
                                .save(provider);
                    }
                });
    }

    public TransfigurationTypeBuilder<T, P> defaultWand() {
        return wand().build();
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

    public TransfigurationTypeBuilder<T, P> recipe(NonNullBiConsumer<DataGenContext<TransfigurationType, T>, RegistrateRecipeProvider> cons) {
        return setData(ProviderType.RECIPE, cons);
    }

    @Override
    @Nonnull
    protected T createEntry() {
        return creator.apply(primaryColor, secondaryColor, ImmutableList.copyOf(keywords), ImmutableList.copyOf(fallback));
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
