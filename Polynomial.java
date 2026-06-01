import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    private double[] coeffs;
    private int[] exps;

    public Polynomial() {
        coeffs = new double[1];
        exps = new int[1];
        coeffs[0] = 0;
        exps[0] = 0;
    }

    public Polynomial(double[] coeff) {
        java.util.List<Double> coeffList = new java.util.ArrayList<>();
        java.util.List<Integer> expList = new java.util.ArrayList<>();
        for (int i = 0; i < coeff.length; i++) {
            if (coeff[i] != 0) {
                coeffList.add(coeff[i]);
                expList.add(i);
            }
        }
        if (coeffList.isEmpty()) {
            coeffs = new double[1];
            exps = new int[1];
            coeffs[0] = 0;
            exps[0] = 0;
        } else {
            coeffs = new double[coeffList.size()];
            exps = new int[expList.size()];
            for (int i = 0; i < coeffList.size(); i++) {
                coeffs[i] = coeffList.get(i);
                exps[i] = expList.get(i);
            }
        }
    }

    public Polynomial(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        sc.close();
        parsePolynomial(line);
    }

    private void parsePolynomial(String line) {
        java.util.List<Double> coeffList = new java.util.ArrayList<>();
        java.util.List<Integer> expList = new java.util.ArrayList<>();

        line = line.replaceAll(" ", "");
        String[] terms = line.split("(?=[-+])");

        for (String term : terms) {
            if (term.isEmpty()) continue;

            double coeff = 0;
            int exp = 0;

            if (term.contains("x")) {
                String[] parts = term.split("x");
                coeff = parts[0].isEmpty() || parts[0].equals("+") ? 1 :
                        parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                exp = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) : 1;
            } else {
                coeff = Double.parseDouble(term);
                exp = 0;
            }

            if (coeff != 0) {
                coeffList.add(coeff);
                expList.add(exp);
            }
        }

        if (coeffList.isEmpty()) {
            coeffs = new double[1];
            exps = new int[1];
            coeffs[0] = 0;
            exps[0] = 0;
        } else {
            coeffs = new double[coeffList.size()];
            exps = new int[expList.size()];
            for (int i = 0; i < coeffList.size(); i++) {
                coeffs[i] = coeffList.get(i);
                exps[i] = expList.get(i);
            }
        }
    }

    public Polynomial add(Polynomial other) {
        java.util.Map<Integer, Double> map = new java.util.HashMap<>();

        for (int i = 0; i < coeffs.length; i++) {
            map.put(exps[i], map.getOrDefault(exps[i], 0.0) + coeffs[i]);
        }

        for (int i = 0; i < other.coeffs.length; i++) {
            map.put(other.exps[i], map.getOrDefault(other.exps[i], 0.0) + other.coeffs[i]);
        }

        java.util.List<Double> coeffList = new java.util.ArrayList<>();
        java.util.List<Integer> expList = new java.util.ArrayList<>();

        for (int exp : map.keySet()) {
            double coeff = map.get(exp);
            if (coeff != 0) {
                coeffList.add(coeff);
                expList.add(exp);
            }
        }

        Polynomial result = new Polynomial();
        result.coeffs = new double[coeffList.size()];
        result.exps = new int[expList.size()];
        for (int i = 0; i < coeffList.size(); i++) {
            result.coeffs[i] = coeffList.get(i);
            result.exps[i] = expList.get(i);
        }
        return result;
    }

    public Polynomial multiply(Polynomial other) {
        java.util.Map<Integer, Double> map = new java.util.HashMap<>();

        for (int i = 0; i < coeffs.length; i++) {
            for (int j = 0; j < other.coeffs.length; j++) {
                int newExp = exps[i] + other.exps[j];
                double newCoeff = coeffs[i] * other.coeffs[j];
                map.put(newExp, map.getOrDefault(newExp, 0.0) + newCoeff);
            }
        }

        java.util.List<Double> coeffList = new java.util.ArrayList<>();
        java.util.List<Integer> expList = new java.util.ArrayList<>();

        for (int exp : map.keySet()) {
            double coeff = map.get(exp);
            if (coeff != 0) {
                coeffList.add(coeff);
                expList.add(exp);
            }
        }

        Polynomial result = new Polynomial();
        result.coeffs = new double[coeffList.size()];
        result.exps = new int[expList.size()];
        for (int i = 0; i < coeffList.size(); i++) {
            result.coeffs[i] = coeffList.get(i);
            result.exps[i] = expList.get(i);
        }
        return result;
    }

    public double evaluate(double x) {
        double rs = 0;
        for (int i = 0; i < coeffs.length; i++) {
            rs += coeffs[i] * Math.pow(x, exps[i]);
        }
        return rs;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-10;
    }

    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < coeffs.length; i++) {
            if (i > 0 && coeffs[i] >= 0) {
                sb.append("+");
            }
            if (exps[i] == 0) {
                sb.append(coeffs[i]);
            } else if (exps[i] == 1) {
                sb.append(coeffs[i]).append("x");
            } else {
                sb.append(coeffs[i]).append("x").append(exps[i]);
            }
        }

        writer.write(sb.toString());
        writer.close();
    }
}
