public class QuantityMeasurementApp {

    public static boolean compareFeet(double value1, double value2) {
        return Double.compare(value1, value2) == 0;
    }

    public static void main(String[] args) {
        double feet1 = 3.0;
        double feet2 = 3.0;

        boolean result = compareFeet(feet1, feet2);
        System.out.println("Feet equality result: " + result);
    }
}
