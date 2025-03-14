package com.rockbite.tools.talos.editor.wrappers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.rockbite.tools.talos.runtime.modules.EmConfigModule;

public class EmConfigModuleWrapper extends ModuleWrapper<EmConfigModule> {

    VisCheckBox additiveBox;
    VisCheckBox attachedBox;
    VisCheckBox continuousBox;
    VisCheckBox alignedBox;

    @Override
    protected void configureSlots() {
        addOutputSlot("config", EmConfigModule.OUTPUT);

        additiveBox = new VisCheckBox("additive");
        attachedBox = new VisCheckBox("attached");
        continuousBox = new VisCheckBox("continuous");
        alignedBox = new VisCheckBox("aligned");

        Table form = new Table();

        form.add(additiveBox).left().padLeft(3);
        form.row();
        form.add(attachedBox).left().padLeft(3);
        form.row();
        form.add(continuousBox).left().padLeft(3);
        form.row();
        form.add(alignedBox).left().padLeft(3);

        contentWrapper.add(form).left();
        contentWrapper.add().expandX();

        rightWrapper.add().expandY();

        attachedBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fromUIToData();
            }
        });
        attachedBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fromUIToData();
            }
        });
        continuousBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fromUIToData();
            }
        });
        alignedBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fromUIToData();
            }
        });
    }

    @Override
    public void setModule(EmConfigModule module) {
        super.setModule(module);
        fromDataToUI();
    }

    public void fromUIToData() {
        module.getUserValue().additive = additiveBox.isChecked();
        module.getUserValue().attached = attachedBox.isChecked();
        module.getUserValue().continuous = continuousBox.isChecked();
        module.getUserValue().aligned = alignedBox.isChecked();
    }

    public void fromDataToUI() {
        additiveBox.setChecked(module.getUserValue().additive);
        attachedBox.setChecked(module.getUserValue().attached);
        continuousBox.setChecked(module.getUserValue().continuous);
        alignedBox.setChecked(module.getUserValue().aligned);
    }

    @Override
    protected float reportPrefWidth() {
        return 170;
    }

    @Override
    public void write(JsonValue value) {
        value.addChild("additive", new JsonValue(module.getUserValue().additive));
        value.addChild("attached", new JsonValue(module.getUserValue().attached));
        value.addChild("continuous", new JsonValue(module.getUserValue().continuous));
        value.addChild("aligned", new JsonValue(module.getUserValue().aligned));
    }

    @Override
    public void read(JsonValue value) {
        module.getUserValue().additive = value.getBoolean("additive");
        module.getUserValue().attached = value.getBoolean("attached");
        module.getUserValue().continuous = value.getBoolean("continuous");
        module.getUserValue().aligned = value.getBoolean("aligned");

        fromDataToUI();
    }

}
