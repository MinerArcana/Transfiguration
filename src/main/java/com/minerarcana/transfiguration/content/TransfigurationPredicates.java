package com.minerarcana.transfiguration.content;

import com.google.common.base.Suppliers;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.predicate.*;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"UnstableApiUsage"})
public class TransfigurationPredicates {
    public static final ResourceKey<Registry<PredicateType>> PREDICATE_TYPE_REGISTRY_KEY = Transfiguration.getRegistrate()
            .makeRegistry("predicate", RegistryBuilder::new);

    private static final Supplier<IForgeRegistry<PredicateType>> PREDICATE_TYPE_REGISTRY = Suppliers.memoize(() ->
            RegistryManager.ACTIVE.getRegistry(PREDICATE_TYPE_REGISTRY_KEY)
    );

    public static final Supplier<Codec<TransfigurationPredicate>> CODEC = Suppliers.memoize(
            () -> PREDICATE_TYPE_REGISTRY.get()
                    .getCodec()
                    .dispatch(TransfigurationPredicate::getType, PredicateType::codec)
    );

    public static final Supplier<Codec<List<TransfigurationPredicate>>> LIST_CODEC = Suppliers.memoize(
            () -> CODEC.get().listOf()
    );

    public static final RegistryEntry<PredicateType> LEVEL = Transfiguration.getRegistrate()
            .object("level")
            .simple(PREDICATE_TYPE_REGISTRY_KEY, () -> PredicateType.of(LevelPredicate.CODEC));

    public static final RegistryEntry<PredicateType> POSITION = Transfiguration.getRegistrate()
            .object("position")
            .simple(PREDICATE_TYPE_REGISTRY_KEY, () -> PredicateType.of(PositionPredicate.CODEC));

    public static final RegistryEntry<PredicateType> FLUIDSTATE = Transfiguration.getRegistrate()
            .object("fluidstate")
            .simple(PREDICATE_TYPE_REGISTRY_KEY, () -> PredicateType.of(FluidStatePredicate.CODEC));

    public static final RegistryEntry<PredicateType> IN_STRUCTURE = Transfiguration.getRegistrate()
            .object("in_structure")
            .simple(PREDICATE_TYPE_REGISTRY_KEY, () -> PredicateType.of(InStructurePredicate.CODEC));

    public static final RegistryEntry<PredicateType> ON_END_PODIUM = Transfiguration.getRegistrate()
            .object("on_end_podium")
            .simple(PREDICATE_TYPE_REGISTRY_KEY, () -> PredicateType.of(OnEndExitPortalPredicate.CODEC));

    public static IForgeRegistry<PredicateType> getRegistry() {
        return PREDICATE_TYPE_REGISTRY.get();
    }

    public static void setup() {

    }
}
