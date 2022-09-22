package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateProvider;
import dan200.computercraft.api.turtle.TurtleUpgradeDataProvider;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import dan200.computercraft.api.upgrades.UpgradeDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RegistrateTurtleUpgradeDataProvider extends TurtleUpgradeDataProvider implements RegistrateProvider, Consumer<UpgradeDataProvider.Upgrade<TurtleUpgradeSerialiser<?>>> {
    public static ProviderType<RegistrateTurtleUpgradeDataProvider> TYPE = ProviderType.register(
            "turtle",
            (owner, event) -> new RegistrateTurtleUpgradeDataProvider(owner, event.getGenerator())
    );

    private final AbstractRegistrate<?> owner;
    private Consumer<UpgradeDataProvider.Upgrade<TurtleUpgradeSerialiser<?>>> callback;

    public RegistrateTurtleUpgradeDataProvider(AbstractRegistrate<?> owner, DataGenerator generator) {
        super(generator);
        this.owner = owner;
    }

    @Override
    protected void addUpgrades(@NotNull Consumer<Upgrade<TurtleUpgradeSerialiser<?>>> addUpgrade) {
        this.callback = addUpgrade;
        this.owner.genData(TYPE, this);
        this.callback = null;
    }

    @Override
    @NotNull
    public LogicalSide getSide() {
        return LogicalSide.SERVER;
    }

    @Override
    public void accept(Upgrade<TurtleUpgradeSerialiser<?>> turtleUpgradeSerializerUpgrade) {
        if (callback != null) {
            callback.accept(turtleUpgradeSerializerUpgrade);
        }
    }
}
