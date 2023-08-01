package uj.wmii.pwj.introduction;

public class QuadraticEquation {

    public double[] findRoots(double a, double b, double c) {
        double[] roots;
        double discriminant = (b * b - 4 * a * c);
        double first = (-b + Math.sqrt(discriminant)) / (2 * a);
        double second = (-b - Math.sqrt(discriminant)) / (2 * a);
        if (discriminant < 0) {
            roots = new double[0];
        }
        else {
            if (discriminant == 0) {
                roots = new double[1];
                roots[0] = first;
            }
            else {
                roots = new double[2];
                roots[0] = first;
                roots[1] = second;
            }
        }
        return roots;
    }

}

