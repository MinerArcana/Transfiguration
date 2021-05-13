package com.minerarcana.transfiguration.entity;

import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public enum TransfigurationPlacement {
    ENTITY(null) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return AxisAlignedBB.fromVector(Vector3d.copyCenteredHorizontally(blockPos));
        }
    },
    DOWN(Direction.DOWN) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return new AxisAlignedBB(
                    blockPos.getX() + 0.25D,
                    blockPos.getY(),
                    blockPos.getZ() + 0.25D,
                    blockPos.getX() + 0.75D,
                    blockPos.getY() + 0.1D,
                    blockPos.getZ() + 0.75D
            );
        }
    },
    UP(Direction.UP) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return new AxisAlignedBB(
                    blockPos.getX() + 0.25D,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 0.25D,
                    blockPos.getX() + 0.75D,
                    blockPos.getY() + 0.9D,
                    blockPos.getZ() + 0.75D
            );
        }
    },
    NORTH(Direction.NORTH) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return AxisAlignedBB.fromVector(Vector3d.copyCenteredHorizontally(blockPos));
        }
    },
    EAST(Direction.EAST) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return AxisAlignedBB.fromVector(Vector3d.copyCenteredHorizontally(blockPos));
        }
    },
    SOUTH(Direction.SOUTH) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return AxisAlignedBB.fromVector(Vector3d.copyCenteredHorizontally(blockPos));
        }
    },
    WEST(Direction.WEST) {
        @Override
        public AxisAlignedBB getBoundingBox(BlockPos blockPos) {
            return AxisAlignedBB.fromVector(Vector3d.copyCenteredHorizontally(blockPos));
        }
    };

    private final Direction direction;

    TransfigurationPlacement(Direction direction) {
        this.direction = direction;
    }

    public abstract AxisAlignedBB getBoundingBox(BlockPos blockPos);

    public Direction getDirection() {
        return direction != null ? direction : Direction.DOWN;
    }

    public static TransfigurationPlacement fromDirection(Direction direction) {
        if (direction != null) {
            for (TransfigurationPlacement placement : values()) {
                if (placement.direction == direction) {
                    return placement;
                }
            }
        }
        return null;
    }

    public static TransfigurationPlacement fromString(String name) {
        for (TransfigurationPlacement placement : values()) {
            if (placement.name().equals(name)) {
                return placement;
            }
        }
        throw new IllegalArgumentException("Failed to find Placement for name: " + name);
    }
}
