package com.valueunlocker.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {
    // the border still exists as a settings object, but every runtime check below gets told to piss off.
    @Inject(method = "isWithinBounds(Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$blockPosAlwaysInside(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isWithinBounds(Lnet/minecraft/world/phys/Vec3;)Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$vec3AlwaysInside(Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isWithinBounds(Lnet/minecraft/world/level/ChunkPos;)Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$chunkAlwaysInside(ChunkPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isWithinBounds(Lnet/minecraft/world/phys/AABB;)Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$aabbAlwaysInside(AABB box, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isWithinBounds(DD)Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$coordsAlwaysInside(double x, double z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isWithinBounds(DDD)Z", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$coordsMarginAlwaysInside(double x, double z, double margin, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "clampToBounds(DDD)Lnet/minecraft/core/BlockPos;", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$dontClampBlockPos(double x, double y, double z, CallbackInfoReturnable<BlockPos> cir) {
        // if the caller asked for cursed coordinates, give them back. no babysitter bullshit.
        cir.setReturnValue(BlockPos.containing(x, y, z));
    }

    @Inject(method = "clampVec3ToBound(DDD)Lnet/minecraft/world/phys/Vec3;", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$dontClampVec3(double x, double y, double z, CallbackInfoReturnable<Vec3> cir) {
        cir.setReturnValue(new Vec3(x, y, z));
    }

    @Inject(method = "getDistanceToBorder(Lnet/minecraft/world/entity/Entity;)D", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$entityNeverOutside(Entity entity, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(Double.MAX_VALUE);
    }

    @Inject(method = "getDistanceToBorder(DD)D", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$coordsNeverOutside(double x, double z, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(Double.MAX_VALUE);
    }

    @Inject(method = "isInsideCloseToBorder", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$neverCloseToBorder(Entity entity, AABB box, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void valueunlocker$noBorderCollision(CallbackInfoReturnable<VoxelShape> cir) {
        // invisible wall removed. congrats, now the void can be your problem instead.
        cir.setReturnValue(Shapes.empty());
    }
}
