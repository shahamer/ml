package pt.it.av.atnog.ml.clustering;

import pt.it.av.atnog.utils.structures.Distance;

import java.util.ArrayList;
import java.util.List;

public class Cluster<D extends Distance> extends ArrayList<D> {

  public Cluster() {
  }

  public Cluster(D dp) {
    add(dp);
  }

  /**
   * @param dps
   * @param <D>
   * @return
   */
  private static <D extends Distance> D center(List<D> dps) {
    D rv = null;

    switch (dps.size()) {
      case 0:
        rv = null;
        break;
      case 1:
        rv = dps.get(0);
        break;
      case 2:
        rv = dps.get(0);
        break;
      default: {
        D bestCenter = dps.get(0);
        double bestDistCenter = 0.0;

        for (int j = 0; j < dps.size(); j++) {
          bestDistCenter += bestCenter.distanceTo(dps.get(j));
        }

        for (int i = 1; i < dps.size(); i++) {
          D currentCenter = dps.get(i);
          double avgDistCenter = 0.0;
          for (int j = 0; j < dps.size(); j++) {
            if (i != j) {
              avgDistCenter += currentCenter.distanceTo(dps.get(j));
            }
          }
          if (avgDistCenter < bestDistCenter) {
            bestCenter = currentCenter;
            bestDistCenter = avgDistCenter;
          }
        }
        rv = bestCenter;
        break;
      }
    }
    return rv;
  }

  /**
   * @param dps
   * @param center
   * @param <D>
   * @return
   */
  private static <D extends Distance> double distortion(List<D> dps, D center) {
    double rv = 0.0;
    for (int i = 0; i < dps.size(); i++) {
      rv += Math.pow(center.distanceTo(dps.get(i)), 2.0);
    }
    return rv;
  }

  /**
   *
   * @param dps
   * @param center
   * @param <D>
   * @return
   */
  private static <D extends Distance> double radius(List<D> dps, D center) {
    double rv = center.distanceTo(dps.get(0));
    for(int i = 1; i < dps.size(); i++) {
      double t = center.distanceTo(dps.get(i));
      if( t > rv) {
        rv = t;
      }
    }
    return rv;
  }

  /**
   * @return
   */
  public D center() {
    return center(this);
  }

  /**
   * Returns the distiortion of the cluster.
   *
   * @return the distortion of the cluster.
   */
  public double distortion() {
    D center = center();
    return distortion(this, center);
  }

  /**
   * @param dp
   * @return
   */
  public double distortion(D dp) {
    List<D> tmp = new ArrayList<>(this);
    tmp.add(dp);
    D center = center(tmp);
    return distortion(tmp, center);
  }

  /**
   * @param cdp
   * @return
   */
  public double distortion(Cluster<D> cdp) {
    List<D> tmp = new ArrayList<>(this);
    tmp.addAll(cdp);
    D center = center(tmp);
    return distortion(tmp, center);
  }

  /**
   *
   * @return
   */
  public double radius() {
    D center = center();
    return radius(this, center);
  }

  /**
   *
   * @param dp
   * @return
   */
  public double radius(D dp) {
    List<D> tmp = new ArrayList<>(this);
    tmp.add(dp);
    D center = center(tmp);
    return radius(tmp, center);
  }

  /**
   * @param cdp
   * @return
   */
  public double radius(Cluster<D> cdp) {
    List<D> tmp = new ArrayList<>(this);
    tmp.addAll(cdp);
    D center = center(tmp);
    return radius(tmp, center);
  }
}
