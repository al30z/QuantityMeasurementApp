// QuantityMeasurementApp.java
public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);

        // UC4: Equality
        System.out.println("1 Foot equals 12 Inches? " + oneFoot.equals(twelveInches));

        // UC5: Conversion
        QuantityLength hundredCm = new QuantityLength(100.0, LengthUnit.CENTIMETERS);
        System.out.println("100 cm = " + hundredCm.convertTo(LengthUnit.FEET) + " Feet");

        // UC6: Addition (default unit of first operand)
        QuantityLength result1 = oneFoot.add(twelveInches);
        System.out.println("1 Foot + 12 Inches = " + result1.getValue() + " " + result1.getUnit());

        // UC7: Addition with target unit specification
        QuantityLength result2 = oneFoot.addWithTargetUnit(twelveInches, LengthUnit.YARDS);
        System.out.println("1 Foot + 12 Inches in Yards = " + result2.getValue() + " " + result2.getUnit());
    }
}

// Standalone enum with conversion responsibility
enum LengthUnit {
    FEET(12.0),          // 1 foot = 12 inches
    INCHES(1.0),         // base unit chosen: inches
    YARDS(36.0),         // 1 yard = 36 inches
    CENTIMETERS(0.393701); // 1 cm = 0.393701 inches

    private final double conversionFactorToInches;

    LengthUnit(double conversionFactorToInches) {
        this.conversionFactorToInches = conversionFactorToInches;
    }

    public double getConversionFactor() {
        return conversionFactorToInches;
    }

    // Convert a value from this unit to inches (base unit)
    public double toBase(double value) {
        return value * conversionFactorToInches;
    }

    // Convert a value from inches (base unit) to this unit
    public double fromBase(double valueInInches) {
        return valueInInches / conversionFactorToInches;
    }
}

// QuantityLength class delegates conversion logic to LengthUnit
class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    // UC4: Equality check
    public boolean equals(QuantityLength other) {
        if (other == null) return false;
        double thisInBase = unit.toBase(this.value);
        double otherInBase = other.unit.toBase(other.value);
        return Math.abs(thisInBase - otherInBase) < 0.0001;
    }

    // UC5: Conversion
    public double convertTo(LengthUnit targetUnit) {
        double valueInBase = unit.toBase(this.value);
        return targetUnit.fromBase(valueInBase);
    }

    // UC6: Addition (result in first operand's unit)
    public QuantityLength add(QuantityLength other) {
        double otherInThisUnit = other.convertTo(this.unit);
        return new QuantityLength(this.value + otherInThisUnit, this.unit);
    }

    // UC7: Addition with target unit specification
    public QuantityLength addWithTargetUnit(QuantityLength other, LengthUnit targetUnit) {
        double thisInTarget = this.convertTo(targetUnit);
        double otherInTarget = other.convertTo(targetUnit);
        return new QuantityLength(thisInTarget + otherInTarget, targetUnit);
    }
}
