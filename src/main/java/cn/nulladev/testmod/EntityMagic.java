package cn.nulladev.testmod;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMagic extends EntityHasOwner {
    public EntityMagic(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void setOwner(Player player) {
        super.setOwner(player);
        this.setPos(player.position());
    }

    @Override
    public void tick() {
        super.tick();
        Entity owner = this.getOwner();
        if (this.getOwner() == null) {
            this.kill();
            return;
        }
        this.setPos(owner.position());
        this.calcRotation();
        if (this.level.isClientSide) {
            //System.out.println("client tick, pos:" + this.position().toString());
            return;
        }
        //System.out.println("server tick, pos:" + this.position().toString());
        if (owner instanceof Player player) {
            if (!player.isUsingItem()) {
                this.kill();
                return;
            }
        } else {
            this.kill();
            return;
        }
    }

    protected void calcRotation() {
        Vec3 look = getOwner().getLookAngle();
        float xz = (float) Math.sqrt(look.x * look.x + look.z * look.z);
        float yaw = (float)(Math.atan2(look.x, look.z) * 180.0D / Math.PI);
        float pitch = (float)(Math.atan2(look.y, xz) * 180.0D / Math.PI);

        this.setXRot(lerpRotation(this.xRotO, yaw));
        this.setYRot(lerpRotation(this.yRotO, pitch));
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while(p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while(p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }
}
