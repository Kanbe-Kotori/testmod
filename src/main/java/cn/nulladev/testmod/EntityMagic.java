package cn.nulladev.testmod;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class EntityMagic extends EntityHasOwner {
    public EntityMagic(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void setOwner(Player player) {
        super.setOwner(player);
        this.setPos(player.position());
        this.calcRotation();
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
            return;
        }
        if (owner instanceof Player player) {
            if (!player.isUsingItem()) {
                this.kill();
                return;
            }
        } else {
            this.kill();
            return;
        }

        shoot();
        shoot();

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

    protected void shoot() {
        if (this.getOwner() instanceof Player player) {
            Vec3 xAxis = player.getLookAngle().normalize();
            Vec3 yAxis = new Vec3(-xAxis.y, xAxis.x, 0).normalize();
            Vec3 zAxis = xAxis.cross(yAxis).normalize();
            float theta = new Random().nextFloat() * 2 * Mth.PI;
            float r = new Random().nextFloat() * 2 * Mth.cos(3 * Mth.PI / 7);
            Vec3 shootPos = this.getOwner().position()
                    .add(0, player.getEyeHeight(), 0)
                    .add(xAxis.scale(2))
                    .add(yAxis.scale(r * Mth.sin(theta)))
                    .add(zAxis.scale(r * Mth.cos(theta)));
            EntityMagicBall ball = new EntityMagicBall(Registry.MAGIC_BALL.get(), level);
            ball.setOwner(player);
            ball.setPos(shootPos);
            float v = 1F + new Random().nextFloat() * 2;
            ball.shoot(xAxis.x,xAxis.y,xAxis.z,v,1);
            level.addFreshEntity(ball);
        }
    }

}
