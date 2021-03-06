package pt.it.av.atnog.ml.clustering.curvature;

import pt.it.av.atnog.ml.regression.UnivariateRegression;
import pt.it.av.atnog.utils.ArrayUtils;
import pt.it.av.atnog.utils.PrintUtils;

/**
 * R-method to detect knee/elbow points.
 * <p>
 * Regression method.
 * It uses a logarithm and power regression to fit the curve and detect knee and elbow points
 * respectively.
 * The root of the curvature (Kf(x)) is computed and used to find the maxium curvature point,
 * which represent the knee/elbow point.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 2.0
 */
public class Rmethod extends BaseCurvature {

  @Override
  public int find_knee(double[] x, double[] y) {
    int rv = -1;
    UnivariateRegression.LNR lnr = UnivariateRegression.lnr(x, y);

    if (lnr.r2() >= 0.8) {
      double rx = lnr.a() / Math.sqrt(2.0);
      rv = ArrayUtils.findClose(rx, x);
    }

    return rv;
  }

  @Override
  public int find_elbow(double[] x, double[] y) {
    int rv = -1;
    System.err.println(PrintUtils.array(x));
    System.err.println(PrintUtils.array(y));
    UnivariateRegression.PR pr = UnivariateRegression.pr(x, y);
    UnivariateRegression.ER er = UnivariateRegression.er(x, y);

    System.out.println("\t\t X=" + PrintUtils.array(x));
    System.out.println("\t\t Y=" + PrintUtils.array(y));


    System.out.println("\t\t"+pr);
    System.out.println("\t\t"+er);

    if (pr.r2() >= 0.8 && pr.r2() > er.r2()) {
      double a = pr.a(), b = pr.b();
      double u = a * b, v = u * (b - 1), w = v * (b - 2);
      //double z = w * u * u - 3 * v * v * u;
      double z = u * (w * u - 3 * v * v);
      double rx = Math.pow((-w / z), (1.0 / (2 * b - 2)));
      //System.out.println("FLN(" + rx + ") = " + fpr(rx, k, z, b));
      rv = ArrayUtils.findClose(rx, x);
    } else if (er.r2() > 0.8) {
      double a = er.a(), b = er.b();
      double rx = (-Math.log(2 * Math.pow(a, 2.0) * Math.pow(b, 2))) / (2 * b);
      rv = ArrayUtils.findClose(rx, x);
    }

    return rv;
  }
}
