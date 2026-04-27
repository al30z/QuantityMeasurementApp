// QuantityMeasurementApp.java
public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);

        // UC7: Addition with target unit specification
        QuantityLength resultInYards = oneFoot.addWithTargetUnit(twelveInches, LengthUnit.YARDS);
        System.out.println("1 Foot + 12 Inches in Yards = " 
                           + resultInYards.getValue() + " " + resultInYards.getUnit());

        QuantityLength fiftyCm = new QuantityLength(50.0, LengthUnit.CENTIMETERS);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength resultInInches = threeFeet.addWithTargetUnit(fiftyCm, LengthUnit.INCHES);
        System.out.println("3 Feet + 50 cm in Inches = " 
                           + resultInInches.getValue() + " " + resultInInches.getUnit());
    }
}

// Class for length quantities
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

    // Conversion logic (from UC5)
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        double valueInInches = value * sourceUnit.getConversionFactor();
        return valueInInches / targetUnit.getConversionFactor();
    }

    // UC6: Addition (default result in first operand's unit)
    public QuantityLength add(QuantityLength other) {
        double otherValueInThisUnit = convert(other.value, other.unit, this.unit);
        return new QuantityLength(this.value + otherValueInThisUnit, this.unit);
    }

    // UC7: Addition with target unit specification
    public QuantityLength addWithTargetUnit(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Other quantity and target unit cannot be null.");
        }

        // Convert both operands into target unit
        double thisInTarget = convert(this.value, this.unit, targetUnit);
        double otherInTarget = convert(other.value, other.unit, targetUnit);

        // Add and return result in target unit
        return new QuantityLength(thisInTarget + otherInTarget, targetUnit);
    }
}

// Enum for length units
enum LengthUnit {
    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(0.393701);

    private final double conversionFactorToInches;

    LengthUnit(double conversionFactorToInches) {
        this.conversionFactorToInches = conversionFactorToInches;
    }

    public double getConversionFactor() {
        return conversionFactorToInches;
    }
}
