enum LengthUnit {
    FEET(12.0),   // 1 foot = 12 inches
    INCH(1.0);    // base unit is inch

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public boolean equals(QuantityLength other) {
        double thisValueInBase = this.unit.toBaseUnit(this.value);
        double otherValueInBase = other.unit.toBaseUnit(other.value);
        return Double.compare(thisValueInBase, otherValueInBase) == 0;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Feet equals Inches: " + feet.equals(inches));
    }
}