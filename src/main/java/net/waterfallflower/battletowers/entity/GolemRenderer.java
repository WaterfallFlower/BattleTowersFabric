package net.waterfallflower.battletowers.entity;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.Biped;
import net.minecraft.entity.Living;
import org.lwjgl.opengl.GL11;

public class GolemRenderer extends BipedEntityRenderer {
    public GolemRenderer() {
        super(new Biped(), 1.0f);
    }

    @Override
    protected void method_823(Living arg, float f) {
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
