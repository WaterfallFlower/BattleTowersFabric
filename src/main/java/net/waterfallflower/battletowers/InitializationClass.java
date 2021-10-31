package net.waterfallflower.battletowers;

import lombok.SneakyThrows;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.level.gen.LevelGenEvent;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.waterfallflower.battletowers.entity.GolemMob;
import net.waterfallflower.battletowers.entity.GolemRenderer;
import net.waterfallflower.battletowers.level.TowerGenerator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class InitializationClass {
    public static int CURRENT = 3;
    private int tower1 = 200;

    @SneakyThrows
    @EventListener
    public void initConfig(InitEvent event) {
        File configFile = new File(FabricLoader.getInstance().getGameDirectory(), "battletowers.properties");
        Properties properties = new Properties();
        if(configFile.exists()) {
            properties.load(new FileReader(configFile));
            CURRENT = Integer.parseInt(String.valueOf(properties.getProperty("rarity")));
        }else {
            properties.put("rarity", String.valueOf(CURRENT));
            properties.store(new FileWriter(configFile), "Config for BattleTowersFabric mod.");
        }
    }

    @EventListener
    public void registerEntity(EntityRegister entityRegister) {
        entityRegister.register(GolemMob.class, "battletowers:golem");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {
        event.renderers.put(GolemMob.class, new GolemRenderer());
    }

    @EventListener
    public void generateTower(LevelGenEvent.ChunkDecoration event) {
        if(event.level.dimension.id == 0) {
            if (this.tower1 >= CURRENT * 100) {
                if (event.random.nextInt(2) == 0) {
                    int ix = event.x + event.random.nextInt(16) + 8;
                    int iy = event.random.nextInt(16) + 64;
                    int iz = event.z + event.random.nextInt(16) + 8;
                    if (new TowerGenerator().generate(event.level, event.random, ix, iy, iz))
                        this.tower1 = 0;
                }
            }
            else this.tower1++;
        }
    }

}
