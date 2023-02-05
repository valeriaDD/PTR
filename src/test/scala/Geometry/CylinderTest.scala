package Geometry

import Geomery.Cylinder
import org.scalatest.funsuite.AnyFunSuite

class CylinderTest extends AnyFunSuite {
  test("get area of a cylinder") {
    val height = 3
    val radius = 4
    val expectedResult = 175.9292

    val actual = Cylinder.getArea(height, radius)

    assert(actual == expectedResult)
  }
}
