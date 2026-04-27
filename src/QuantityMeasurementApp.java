enum LengthUnit {
    FEET(12.0),          // 1 foot = 12 inches
    INCH(1.0),           // base unit is inch
    YARD(36.0),          // 1 yard = 36 inches
    CM(0.393701);        // 1 cm = 0.393701 inches

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
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CM);
        QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println("1 Yard equals 3 Feet: " + yard.equals(feet));
        System.out.println("2.54 cm equals 1 Inch: " + cm.equals(inch));
    }
}