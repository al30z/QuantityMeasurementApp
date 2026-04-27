// QuantityMeasurementApp.java
public class QuantityMeasurementApp {
    public static void main(String[] args) {
        // Weight examples
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight thousandGrams = new QuantityWeight(1000.0, WeightUnit.GRAM);

        // UC9: Equality
        System.out.println("1 Kilogram equals 1000 Grams? " + oneKg.equals(thousandGrams));

        // UC9: Conversion
        QuantityWeight fivePounds = new QuantityWeight(5.0, WeightUnit.POUND);
        System.out.println("5 Pounds = " + fivePounds.convertTo(WeightUnit.KILOGRAM) + " Kilograms");

        // UC9: Addition (default unit of first operand)
        QuantityWeight sum1 = oneKg.add(fivePounds);
        System.out.println("1 Kilogram + 5 Pounds = " + sum1.getValue() + " " + sum1.getUnit());

        // UC9: Addition with target unit specification
        QuantityWeight sum2 = oneKg.addWithTargetUnit(fivePounds, WeightUnit.GRAM);
        System.out.println("1 Kilogram + 5 Pounds in Grams = " + sum2.getValue() + " " + sum2.getUnit());
    }
}

// Standalone enum for weight units with conversion responsibility
enum WeightUnit {
    KILOGRAM(1.0),          // base unit: kilogram
    GRAM(0.001),            // 1 gram = 0.001 kg
    POUND(0.453592);        // 1 pound ≈ 0.453592 kg

    private final double conversionFactorToKg;

    WeightUnit(double conversionFactorToKg) {
        this.conversionFactorToKg = conversionFactorToKg;
    }

    public double getConversionFactor() {
        return conversionFactorToKg;
    }

    // Convert a value from this unit to kilograms (base unit)
    public double toBase(double value) {
        return value * conversionFactorToKg;
    }

    // Convert a value from kilograms (base unit) to this unit
    public double fromBase(double valueInKg) {
        return valueInKg / conversionFactorToKg;
    }
}

// QuantityWeight class delegates conversion logic to WeightUnit
class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
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

    public WeightUnit getUnit() {
        return unit;
    }

    // Equality check
    public boolean equals(QuantityWeight other) {
        if (other == null) return false;
        double thisInBase = unit.toBase(this.value);
        double otherInBase = other.unit.toBase(other.value);
        return Math.abs(thisInBase - otherInBase) < 0.0001;
    }

    // Conversion
    public double convertTo(WeightUnit targetUnit) {
        double valueInBase = unit.toBase(this.value);
        return targetUnit.fromBase(valueInBase);
    }

    // Addition (result in first operand's unit)
    public QuantityWeight add(QuantityWeight other) {
        double otherInThisUnit = other.convertTo(this.unit);
        return new QuantityWeight(this.value + otherInThisUnit, this.unit);
    }

    // Addition with target unit specification
    public QuantityWeight addWithTargetUnit(QuantityWeight other, WeightUnit targetUnit) {
        double thisInTarget = this.convertTo(targetUnit);
        double otherInTarget = other.convertTo(targetUnit);
        return new QuantityWeight(thisInTarget + otherInTarget, targetUnit);
    }
}
