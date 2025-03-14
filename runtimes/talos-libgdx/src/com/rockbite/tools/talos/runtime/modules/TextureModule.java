package com.rockbite.tools.talos.runtime.modules;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rockbite.tools.talos.runtime.render.TextureRegionDrawable;
import com.rockbite.tools.talos.runtime.values.DrawableValue;

public class TextureModule extends Module {

    public static final int OUTPUT = 0;

    private DrawableValue userDrawable = new DrawableValue();
    private DrawableValue outputValue;

    @Override
    protected void defineSlots() {
        outputValue = (DrawableValue) createOutputSlot(OUTPUT, new DrawableValue());
        userDrawable.setEmpty(true);
    }

    @Override
    public void processValues() {
        outputValue.set(userDrawable);
    }

    public void setRegion(TextureRegion region) {
        userDrawable.setDrawable(new TextureRegionDrawable(region));
    }
}
