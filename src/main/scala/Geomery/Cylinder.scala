package Geomery

import scala.math.BigDecimal.RoundingMode

object Cylinder {
  def getArea(height: Double, radius: Double): Double = {
    val area = 2 * math.Pi * radius * height + 2 * math.Pi * radius * radius

    BigDecimal(area).setScale(4, RoundingMode.HALF_UP).toDouble
  }
}
