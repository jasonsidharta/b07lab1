public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] coeffs) {
        coefficients = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            coefficients[i] = coeffs[i];
        }
    }

    public Polynomial add(Polynomial other) {
        int maxLen = Math.max(coefficients.length, other.coefficients.length);
        double[] result = new double[maxLen];

        for (int i = 0; i < coefficients.length; i++) {
            result[i] += coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            result[i] += other.coefficients[i];
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0;
        double xPower = 1;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * xPower;
            xPower *= x;
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-10;
    }
}
