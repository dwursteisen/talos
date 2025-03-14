package com.rockbite.tools.talos.editor.wrappers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.rockbite.tools.talos.runtime.modules.Vector2Module;

public class Vector2ModuleWrapper extends ModuleWrapper<Vector2Module> {

    private VisTextField xField;
    private VisTextField yField;

    @Override
    protected void configureSlots() {

        xField = addInputSlotWithTextField("X: ", 0);
        yField = addInputSlotWithTextField("Y: ", 1);

        xField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float x = floatFromText(xField);
                module.setX(x);
            }
        });

        yField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float y = floatFromText(yField);
                module.setY(y);
            }
        });

        addOutputSlot("position", 0);
    }

    @Override
    protected float reportPrefWidth() {
        return 210;
    }

    @Override
    public void write(JsonValue value) {
        value.addChild("x", new JsonValue(module.getDefaultX()));
        value.addChild("y", new JsonValue(module.getDefaultY()));
    }

    @Override
    public void read(JsonValue value) {
        float x = value.getFloat("x");
        float y = value.getFloat("y");
        xField.setText(x+"");
        yField.setText(y+"");
        module.setX(x);
        module.setY(y);
    }
}
