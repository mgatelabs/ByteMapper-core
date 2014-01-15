package com.mgatelabs.bytemapper.samples;

import com.mgatelabs.bytemapper.util.BFLTag;

/**
 * Created by Michael Fuller on 1/14/14.
 */
@BFLTag(name = "NoiseSample")
public class NoiseSample {
    private String sample;

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }
}
