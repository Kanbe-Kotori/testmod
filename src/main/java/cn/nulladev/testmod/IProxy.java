package cn.nulladev.testmod;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IProxy {
    void setup();
}

class ClientProxy implements IProxy {
    public Level getLevel() {
        return Minecraft.getInstance().level;
    }

    public Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public void setup() {

    }
}

class ServerProxy implements IProxy {
    @Override
    public void setup() {
    }
}
