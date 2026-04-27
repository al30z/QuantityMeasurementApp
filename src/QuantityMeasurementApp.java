// QuantityMeasurementApp.java
// Single consolidated codebase for evolving across UC1–UCn

enum LengthUnit {
    FEET(12.0),        // 1 foot = 12 inches
    INCHES(1.0),       // base unit chosen: inches
    YARDS(36.0),       // 1 yard = 36 inches
    CENTIMETERS(0.393701); // 1 cm = 0.393701 inches

    private final double conversionFactorToInches;

    LengthUnit(double conversionFactorToInches) {
        this.conversionFactorToInches = conversionFactorToInches;
    }

    public double getConversionFactor() {
        return conversionFactorToInches;
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
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
        double thisInInches = this.value * this.unit.getConversionFactor();
        double otherInInches = other.value * other.unit.getConversionFactor();
        return Math.abs(thisInInches - otherInInches) < 0.0001;
    }

    // UC5: Conversion logic
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }
        if (sourceUnit == null || targetUnit == null) {
            throw new IllegalArgumentException("Units cannot be null.");
        }

        // Step 1: Convert source value to base unit (inches)
        double valueInInches = value * sourceUnit.getConversionFactor();

        // Step 2: Convert from base unit to target unit
        return valueInInches / targetUnit.getConversionFactor();
    }

    public double convertTo(LengthUnit targetUnit) {
        return convert(this.value, this.unit, targetUnit);
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        // UC4: Equality check
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println("1 Foot equals 12 Inches? " + oneFoot.equals(twelveInches));

        // UC5: Conversion examples
        double feetToInches = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        System.out.println("1 Foot = " + feetToInches + " Inches");

        double yardsToInches = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES);
        System.out.println("1 Yard = " + yardsToInches + " Inches");

        double cmToFeet = QuantityLength.convert(100.0, LengthUnit.CENTIMETERS, LengthUnit.FEET);
        System.out.println("100 cm = " + cmToFeet + " Feet");

        QuantityLength length = new QuantityLength(3.0, LengthUnit.FEET);
        double converted = length.convertTo(LengthUnit.CENTIMETERS);
        System.out.println("3 Feet = " + converted + " Centimeters");
    }
}
