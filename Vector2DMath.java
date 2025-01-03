public class Vector2DMath {
  public static double dot(double[] u, double[] v) {
    double prod = 0.0;
    for (int i = 0; i < u.length; i++) {prod += u[i] * v[i];}
    return prod;
  }

  public static double magnitude(double[] u) {
    return Math.sqrt(dot(u, u));
  }

  public static double[] projection(double[] v, double[] u) {
    double[] proj = new double[2];
    double scalar = dot(u, v) / dot(u, u);
    proj[0] = scalar * u[0];
    proj[1] = scalar * u[1];
    return proj;
  }

  public static double[] normal(double[] u) {
    double length = magnitude(u);
    double x = u[0] * 1.0 / length;
    double y = u[1] * 1.0 / length;
    double[] norm = {x, y};
    return norm;
  }
}
