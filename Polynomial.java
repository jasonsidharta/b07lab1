public class Polynomial {
    private double[] CC;
    public Polynomial() {
        CC = new double[1];
        CC[0] = 0;
    }
    public Polynomial(double[] coeffs) {
        CC = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            CC[i] = coeffs[i];
        }
    }
    public Polynomial add(Polynomial other) {
        int maxLen = Math.max(CC.length, other.CC.length);
        double[] rs = new double[maxLen];

        for (int i = 0; i < CC.length; i++) {
            rs[i] += CC[i];
        }

        for (int i = 0; i < other.CC.length; i++) {
            rs[i] += other.CC[i];
        }
        return new Polynomial(rs);
    }

    public double evaluate(double x) {
        double rs = 0;
        double xPower = 1;

        for (int i = 0; i < CC.length; i++) {
            rs += CC[i] * xPower;
            xPower *= x;
        }

        return rs;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-10;
    }
}
