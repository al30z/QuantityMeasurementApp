class Feet {
    private final double value;

    public Feet(double value) {
        this.value = value;
    }

    public boolean equals(Feet other) {
        return Double.compare(this.value, other.value) == 0;
    }
}

class Inches {
    private final double value;

    public Inches(double value) {
        this.value = value;
    }

    public boolean equals(Inches other) {
        return Double.compare(this.value, other.value) == 0;
    }
}

public class QuantityMeasurementApp {

    public static void main(String[] args) {
        Feet feet1 = new Feet(3.0);
        Feet feet2 = new Feet(3.0);
        System.out.println("Feet equality: " + feet1.equals(feet2));

        Inches inch1 = new Inches(12.0);
        Inches inch2 = new Inches(12.0);
        System.out.println("Inches equality: " + inch1.equals(inch2));
    }
}
