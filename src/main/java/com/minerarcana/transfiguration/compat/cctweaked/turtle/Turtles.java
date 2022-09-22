package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;

public class Turtles {
    public static final RegistryEntry<TransfiguringTurtleSerializer> TRANSFIGURING_TURTLE = Transfiguration.getRegistrate()
            .object("transfiguring")
            .simple(TurtleUpgradeSerialiser.class, TransfiguringTurtleSerializer::new);

    public static void setup() {
        Transfiguration.getRegistrate()
                .addDataGenerator(RegistrateTurtleUpgradeDataProvider.TYPE, Turtles::genData);
    }

    private static void genData(RegistrateTurtleUpgradeDataProvider provider) {
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.ACCURSED));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.BLESSED));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.DESTABILIZING));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.DISSOLUTION));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.FUNGAL));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.MUTANDI));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.NETHERI));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.OVERNI));
        provider.accept(TransfiguringTurtleUpgrade.getUpgradeData(TransfigurationTypes.ANIMATION));
    }
}
