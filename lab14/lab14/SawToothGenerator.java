package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;
    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state++;
        return normalize(state % period);
    }

    private double normalize(int state) {
        return (double) (state * 2 - period - 1)/ (period - 1);
    }
}
