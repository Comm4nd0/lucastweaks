package com.lucas.tweaks.entity;

import com.lucas.tweaks.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrapplingHookEntity extends ThrownItemEntity {

    private Vec3d hookTarget = null;
    private boolean pulling = false;
    private int pullTicks = 0;
    private static final int MAX_PULL_TICKS = 80; // 4 second safety timeout
    private static final double PULL_SPEED = 1.5;
    private static final double ARRIVE_DISTANCE = 1.5;

    public GrapplingHookEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public GrapplingHookEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.GRAPPLING_HOOK, owner, world, new ItemStack(ModItems.GRAPPLING_HOOK));
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GRAPPLING_HOOK;
    }

    @Override
    public void tick() {
        if (pulling) {
            if (getEntityWorld().isClient()) {
                return; // Client side: entity stays stationary at hook point
            }

            pullTicks++;
            Entity owner = getOwner();

            // Safety: stop if owner is gone, dead, or timeout exceeded
            if (!(owner instanceof ServerPlayerEntity player) || hookTarget == null
                    || !player.isAlive() || pullTicks > MAX_PULL_TICKS) {
                discard();
                return;
            }

            Vec3d playerPos = player.getEntityPos();
            double distance = playerPos.distanceTo(hookTarget);

            // Player arrived at hook target
            if (distance < ARRIVE_DISTANCE) {
                player.setVelocity(Vec3d.ZERO);
                player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));
                player.fallDistance = 0.0F;
                discard();
                return;
            }

            // Pull player toward hook each tick
            Vec3d direction = hookTarget.subtract(playerPos).normalize();
            player.setVelocity(
                    direction.x * PULL_SPEED,
                    direction.y * PULL_SPEED + 0.1,
                    direction.z * PULL_SPEED
            );
            player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));
            player.fallDistance = 0.0F;
            return;
        }
        super.tick(); // Normal projectile flight
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!getEntityWorld().isClient()) {
            hookTarget = blockHitResult.getPos();
            pulling = true;
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!getEntityWorld().isClient()) {
            hookTarget = entityHitResult.getEntity().getEntityPos();
            pulling = true;
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        // Don't discard — entity stays alive to pull player each tick
        // It gets discarded in tick() once the player arrives or times out
    }
}
