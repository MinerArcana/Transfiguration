package com.minerarcana.transfiguration.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public enum TransfigurationPlacement {
    ENTITY(null) {
        @Override
        public AABB getBoundingBox(BlockPos blockPos) {
            return AABB.unitCubeFromLowerCorner(Vec3.atBottomCenterOf(blockPos));
        }
    },
    DOWN(Direction.DOWN) {
        @Override
        public AABB getBoundingBox(BlockPos blockPos) {
            return new AABB(
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
        public AABB getBoundingBox(BlockPos blockPos) {
            return new AABB(
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
        public AABB getBoundingBox(BlockPos blockPos) {
            return AABB.unitCubeFromLowerCorner(Vec3.atBottomCenterOf(blockPos));
        }
    },
    EAST(Direction.EAST) {
        @Override
        public AABB getBoundingBox(BlockPos blockPos) {
            return AABB.unitCubeFromLowerCorner(Vec3.atBottomCenterOf(blockPos));
        }
    },
    SOUTH(Direction.SOUTH) {
        @Override
        public AABB getBoundingBox(BlockPos blockPos) {
            return AABB.unitCubeFromLowerCorner(Vec3.atBottomCenterOf(blockPos));
        }
    },
    WEST(Direction.WEST) {
        @Override
        public AABB getBoundingBox(BlockPos blockPos) {
            return AABB.unitCubeFromLowerCorner(Vec3.atBottomCenterOf(blockPos));
        }
    };

    private final Direction direction;

    TransfigurationPlacement(Direction direction) {
        this.direction = direction;
    }

    public abstract AABB getBoundingBox(BlockPos blockPos);

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
