package mDimension.core;

import arc.graphics.*;
import arc.graphics.g2d.Bloom;
import arc.graphics.gl.*;
import arc.struct.*;

public class ExtendedBloom extends Bloom {

    public static class BloomRange {
        public float startLayer;
        public float endLayer;
        public float intensity = 1f;
        public float threshold = 0.5f;

        public BloomRange(float startLayer, float endLayer) {
            this.startLayer = startLayer;
            this.endLayer = endLayer;
        }

        public BloomRange(float startLayer, float endLayer, float intensity, float threshold) {
            this.startLayer = startLayer;
            this.endLayer = endLayer;
            this.intensity = intensity;
            this.threshold = threshold;
        }
    }

    public Seq<BloomRange> ranges = new Seq<>();
    public boolean captureAll = false;

    public ExtendedBloom(boolean useFloat){
        super(useFloat);
    }

    public ExtendedBloom(int width, int height, boolean useFloat, boolean depth){
        super(width, height, useFloat, depth);
    }

    public void addRange(float startLayer, float endLayer) {
        ranges.add(new BloomRange(startLayer, endLayer));
    }

    public void addRange(float startLayer, float endLayer, float intensity, float threshold) {
        ranges.add(new BloomRange(startLayer, endLayer, intensity, threshold));
    }

    public void clearRanges() {
        ranges.clear();
    }

    public boolean shouldCapture(float layer) {
        if (captureAll) return true;
        for (BloomRange range : ranges) {
            if (layer >= range.startLayer && layer <= range.endLayer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void capture() {
        //这里可以根据当前的渲染图层来决定是否应该捕获光效
        //由于我们在ExtendedRenderer中已经处理了区间的开始和结束，这里可以保持默认行为
        //如果需要更精细的控制，可以在这里添加逻辑
        super.capture();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}