package com.minerarcana.transfiguration.util;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

import java.util.Random;

public class Vectors {
    public static Vector3d withRandomOffset(Vector3i center, Random random, int size) {
        double sizeHalved = size / 2D;
        return new Vector3d(
                center.getX() + 0.5D + (random.nextFloat() * size) - sizeHalved,
                center.getY() + 0.5D + (random.nextFloat() * size) - sizeHalved,
                center.getZ() + 0.5D + (random.nextFloat() * size) - sizeHalved
        );
    }

    public static Vector3d centered(Vector3i vector) {
        return new Vector3d(
                vector.getX() + 0.5,
                vector.getY() + 0.5,
                vector.getZ() + 0.5
        );
    }
}
