package io.aif.language.common;

public class FuzzyBoolean implements IFuzzyBoolean{

    private final double DEFAULT_TRUTH_THRESHOLD = .5;

    private final double threshold;

    private final double value;

    public FuzzyBoolean(final double value) {
        this.value = value;
        threshold = DEFAULT_TRUTH_THRESHOLD;
    }

    public FuzzyBoolean(final double value, final double threshold) {
        this.value = value;
        this.threshold = threshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FuzzyBoolean)) return false;

        FuzzyBoolean that = (FuzzyBoolean) o;

        if (Double.compare(that.getValue(), this.getValue()) != 0
            || Double.compare(that.getThreshold(), this.getThreshold()) != 0)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(threshold);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public double getThreshold() {
        return threshold;
    }

    @Override
    public boolean isTrue() {
        return (value >= threshold) ? true : false;
    }
}
