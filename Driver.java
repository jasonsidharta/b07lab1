import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));

        double[] c1 = {6, 0, 0, 5};
        Polynomial p1 = new Polynomial(c1);
        double[] c2 = {0, -2, 0, 0, -9};
        Polynomial p2 = new Polynomial(c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        Polynomial product = p1.multiply(p2);
        System.out.println("p1 * p2 evaluated at x=2: " + product.evaluate(2));

        p1.saveToFile("test_polynomial.txt");
        Polynomial p3 = new Polynomial(new File("test_polynomial.txt"));
        System.out.println("Loaded from file, p3(2) = " + p3.evaluate(2));
    }
}
