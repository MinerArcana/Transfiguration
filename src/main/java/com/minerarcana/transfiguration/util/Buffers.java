package com.minerarcana.transfiguration.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class Buffers {
    public static void writeVector3d(Vec3 vector3d, FriendlyByteBuf buffer) {
        buffer.writeDouble(vector3d.x);
        buffer.writeDouble(vector3d.y);
        buffer.writeDouble(vector3d.z);
    }

    public static Vec3 readVector3d(FriendlyByteBuf buffer) {
        return new Vec3(
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble()
        );
    }
}
