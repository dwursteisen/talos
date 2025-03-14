package com.rockbite.tools.talos.runtime;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.rockbite.tools.talos.runtime.modules.*;
import com.rockbite.tools.talos.runtime.modules.Module;

public class ModuleGraph {

    private ParticleSystem system;

    Array<Module> modules = new Array<>();

    ParticleModule particleModule;
    EmitterModule emitterModule;

    private int moduleIndex = 0;

    public static ObjectSet<Class> registeredModules;

    public ModuleGraph(ParticleSystem system) {
        this.system = system;
    }

    public static ObjectSet<Class> getRegisteredModules() {
        registerModules();
        return registeredModules;
    }

    public static void registerModules() {
        if(registeredModules == null) {
            registeredModules = new ObjectSet<>();
            registeredModules.add(EmitterModule.class);
            registeredModules.add(InterpolationModule.class);
            registeredModules.add(InputModule.class);
            registeredModules.add(ParticleModule.class);
            registeredModules.add(StaticValueModule.class);
            registeredModules.add(RandomRangeModule.class);
            registeredModules.add(MixModule.class);
            registeredModules.add(MathModule.class);
            registeredModules.add(CurveModule.class);
            registeredModules.add(Vector2Module.class);
            registeredModules.add(ColorModule.class);
            registeredModules.add(DynamicRangeModule.class);
            registeredModules.add(ScriptModule.class);
            registeredModules.add(GradientColorModule.class);
            registeredModules.add(TextureModule.class);
            registeredModules.add(EmConfigModule.class);
        }
    }

    public Module createModule(Class clazz) {
        Module module = null;

        if(Module.class.isAssignableFrom(clazz)) {
            try {
                module = (Module) ClassReflection.newInstance(clazz);
                module.init(system);
                module.setIndex(moduleIndex++);
            } catch (ReflectionException e) {
                e.printStackTrace();
            }
        }

        boolean cancel = false;

        if(module != null) {
            if(module instanceof ParticleModule) {
                if(particleModule == null) {
                    particleModule = (ParticleModule) module;
                } else {
                    cancel = true;
                }
            }
            if(module instanceof EmitterModule) {
                if(emitterModule == null) {
                    emitterModule = (EmitterModule) module;
                } else {
                    cancel = true;
                }
            }

            if(!cancel) {
                modules.add(module);
            } else {
                module = null;
            }
        }

        return module;
    }


    public void removeModule(Module module) {
        // was this module connected to someone?
        for(Module toModule: modules) {
            if(toModule.isConnectedTo(module)) {
                toModule.detach(module);
            }
        }

        modules.removeValue(module, true);

        if(module instanceof ParticleModule) {
            particleModule = null;
        }
        if(module instanceof EmitterModule) {
            emitterModule = null;
        }
    }

    public void connectNode(Module from, Module to, int slotFrom, int slotTo) {
        // slotTo is the input of module to
        // slotFrom is the output of slot from
        from.attachModuleToMyOutput(to, slotFrom, slotTo);
        to.attachModuleToMyInput(from, slotTo, slotFrom);
    }

    public void removeNode(Module module, int slot) {
        module.detach(slot);
    }

    public ParticleModule getParticleModule() {
        return particleModule;
    }

    public void resetRequesters() {
        for(Module module: modules) {
            module.resetLastRequester();
        }
    }

    public EmitterModule getEmitterModule() {
        return emitterModule;
    }

    public Array<Module> getModules() {
        return modules;
    }
}
