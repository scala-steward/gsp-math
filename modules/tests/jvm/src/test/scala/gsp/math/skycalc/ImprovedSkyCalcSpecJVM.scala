// Copyright (c) 2016-2020 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package gsp.math.skycalc

import cats.implicits._
import weaver._
import weaver.scalacheck._

import edu.gemini.skycalc.ImprovedSkyCalcTest
import cats.Show
import java.time.Instant
import gsp.math.Coordinates
import gsp.math.arb.ArbCoordinates._
import gsp.math.arb.ArbLocation._
import com.fortysevendeg.scalacheck.datetime.instances.jdk8._
import com.fortysevendeg.scalacheck.datetime.GenDateTime.genDateTimeWithinRange
import java.time._
import gsp.math.Location
import jsky.coords.WorldCoords
import java.{ util => ju }

object ImprovedSkyCalcSpecJVM extends SimpleIOSuite with IOCheckers {

  implicit val showInstant: Show[Instant]   = Show.fromToString
  implicit val showZDT: Show[ZonedDateTime] = Show.fromToString

  private val zdtFrom  = ZonedDateTime.of(
    LocalDate.of(1901, 1, 1),
    LocalTime.MIDNIGHT,
    ZoneOffset.UTC
  )
  private val zdtRange = Duration.ofDays(Period.ofYears(1000).getDays.toLong)

  simpleTest("ImprovedSkyCalcSpec: Arbitrary sky calculations") {
    // This generator already provides ZDTs with millisecond precision, not nano.
    forall(genDateTimeWithinRange(zdtFrom, zdtRange)) { zdt =>
      forall { (location: Location, coords: Coordinates) =>
        val calc = ImprovedSkyCalc(location)
        val javaCalc = new ImprovedSkyCalcTest(location.latitude.toAngle.toSignedDoubleDegrees,
                                               location.longitude.toSignedDoubleDegrees,
                                               location.altitude
        )

        val instant = zdt.toInstant

        val results = calc.calculate(coords, instant, false)
        javaCalc.calculate(new WorldCoords(coords.ra.toAngle.toSignedDoubleDegrees,
                                           coords.dec.toAngle.toSignedDoubleDegrees
                           ),
                           ju.Date.from(instant),
                           false
        )

        expect(results.altitudeRaw === javaCalc.getAltitude)
          .and(expect(results.azimuthRaw === javaCalc.getAzimuth))
          .and(expect(results.parallacticAngleRaw === javaCalc.getParallacticAngle))
          .and(expect(results.hourAngleRaw === javaCalc.getHourAngle))
          .and(expect(results.lunarIlluminatedFraction === javaCalc.getLunarIlluminatedFraction))
          .and(expect(results.lunarSkyBrightness === javaCalc.getLunarSkyBrightness))
          .and(expect(results.totalSkyBrightness === javaCalc.getTotalSkyBrightness))
          .and(expect(results.lunarPhaseAngleRaw === javaCalc.getLunarPhaseAngle))
          .and(expect(results.sunAltitudeRaw === javaCalc.getSunAltitude))
          .and(expect(results.lunarDistance === javaCalc.getLunarDistance))
          .and(expect(results.lunarElevationRaw === javaCalc.getLunarElevation))
      }
    }
  }
}
