package com.minerarcana.transfiguration.util;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;

public class Buffers {
    public static void writeVector3d(Vector3d vector3d, PacketBuffer buffer) {
        buffer.writeDouble(vector3d.x);
        buffer.writeDouble(vector3d.y);
        buffer.writeDouble(vector3d.z);
    }

    public static Vector3d readVector3d(PacketBuffer buffer) {
        return new Vector3d(
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble()
        );
    }
}
