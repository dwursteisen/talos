package com.rockbite.tools.talos.runtime.modules;

import com.badlogic.gdx.math.Interpolation;
import com.rockbite.tools.talos.runtime.ScopePayload;

import java.util.Random;

public class DynamicRangeModule extends CurveModule {

    private float lowMin = 0, lowMax = 100;
    private float highMin = 0, highMax = 100;

    public static final int OUTPUT = 0;

    private Random random = new Random();

    @Override
    public void processValues() {
        processAlphaDefaults();

        // do the random thing first
        float low = calcRandomRange(lowMin, lowMax, 1);
        float high = calcRandomRange(highMin, highMax, 2);

        super.processValues();

        float mix = Interpolation.linear.apply(low, high, output.getFloat());

        output.set(mix);
    }

    private float calcRandomRange(float min, float max, int randomOffset) {
        random.setSeed((long) ((getScope().getFloat(ScopePayload.PARTICLE_SEED) * 10000 * (index + randomOffset) * 1000)));
        float startPos = random.nextFloat();
        float res = min + (max - min) * startPos;

        return res;
    }

    public void setMinMaxHigh(float highMin, float highMax) {
        this.highMin = highMin;
        this.highMax = highMax;
    }

    public void setMinMaxLow(float lowMin, float lowMax) {
        this.lowMin = lowMin;
        this.lowMax = lowMax;
    }

    public float getLowMin() {
        return lowMin;
    }

    public float getLowMax() {
        return lowMax;
    }

    public float getHightMin() {
        return highMin;
    }

    public float getHightMax() {
        return highMax;
    }
}
