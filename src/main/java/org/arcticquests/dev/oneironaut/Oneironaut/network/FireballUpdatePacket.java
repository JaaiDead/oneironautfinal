package org.arcticquests.dev.oneironaut.Oneironaut.network;

import at.petrak.hexcasting.common.msgs.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.Vec3;
import org.arcticquests.dev.oneironaut.Oneironaut.Oneironaut;

import java.util.ConcurrentModificationException;

public class FireballUpdatePacket implements IMessage {
    public final Vec3 targetVelocity;
    public final AbstractHurtingProjectile entity;

    public FireballUpdatePacket(Vec3 targetVelocity, AbstractHurtingProjectile entity) {
        this.targetVelocity = targetVelocity;
        this.entity = entity;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeDouble(this.targetVelocity.x);
        buf.writeDouble(this.targetVelocity.y);
        buf.writeDouble(this.targetVelocity.z);

        if (this.entity != null) {
            buf.writeDouble(this.entity.getX());
            buf.writeDouble(this.entity.getY());
            buf.writeDouble(this.entity.getZ());
            buf.writeInt(this.entity.getId());
        } else {
            buf.writeDouble(0.0);
            buf.writeDouble(0.0);
            buf.writeDouble(0.0);
            buf.writeInt(-1);
        }
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    public static final ResourceLocation ID =  ResourceLocation.fromNamespaceAndPath(Oneironaut.MODID, "fireballupdate");

    public static FireballUpdatePacket deserialise(ByteBuf buffer) {
        FriendlyByteBuf buf = new FriendlyByteBuf(buffer);

        Vec3 targetVelocity = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 entityPos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        int entityID = buf.readInt();

        if (entityID < 0) {
            return null;
        }

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) {
            return null;
        }

        Entity found = level.getEntity(entityID);
        if (!(found instanceof AbstractHurtingProjectile projectile)) {
            return null;
        }

        return new FireballUpdatePacket(targetVelocity, projectile);
    }

    public static void handle(FireballUpdatePacket self) {
        if (self == null || self.entity == null) {
            return;
        }

        Minecraft.getInstance().execute(() -> {
            Vec3 tv = self.targetVelocity;
            AbstractHurtingProjectile entityToUpdate = self.entity;
            try {
                entityToUpdate.xPower = tv.x;
                entityToUpdate.yPower = tv.y;
                entityToUpdate.zPower = tv.z;
            } catch (ConcurrentModificationException e) {
                Oneironaut.LOGGER.debug("oopsie, concurrent modification!\n" + e);
            }
        });
    }
}