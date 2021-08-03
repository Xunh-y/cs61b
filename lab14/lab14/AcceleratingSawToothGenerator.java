package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private int state;
    private double v;

    public AcceleratingSawToothGenerator(int period, double v) {
        state = 0;
        this.v = v;
        this.period = period;
    }

    public double next() {
        state++;
        if (state == period) {
            state = 0;
            period *= v;
        }
        return normalize(state % period);
    }

    private double normalize(int state) {
        return (double) (state * 2 - period - 1)/ (period - 1);
    }
}
