// Copyright (c) 2016-2020 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package gsp

/** Mathematical data types for general use, not specific to the Gem model. */
package object math {

  type RA = RightAscension
  val RA: RightAscension.type = RightAscension

  type Dec = Declination
  val Dec: Declination.type = Declination

  type Lat = Declination
  val Lat: Declination.type = Declination

  type Lon = HourAngle
  val Lon: HourAngle.type = HourAngle

}
