// Copyright (c) 2016-2020 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package lucuma.core.math.skycalc

import munit.FunSuite
import lucuma.core.math.Place
import java.time.LocalDate
import org.scalactic.Tolerance

final class TwilightCalcSpec extends FunSuite with Tolerance {
  import TwilightBoundType._

  private val Date = LocalDate.of(2000, 1, 1)

  // Known results with OCS
  private val expected: Map[(Place, TwilightBoundType, LocalDate), (Long, Long)] =
    Map(
      (GN, Official, Date)     -> ((946785833352L, 946831636357L)),
      (GN, Civil, Date)        -> ((946786687500L, 946830782669L)),
      (GN, Nautical, Date)     -> ((946788331171L, 946829139780L)),
      (GN, Astronomical, Date) -> ((946789955319L, 946827516290L)),
      (GS, Official, Date)     -> ((946771013083L, 946805782320L)),
      (GS, Civil, Date)        -> ((946772131479L, 946804663970L)),
      (GS, Nautical, Date)     -> ((946774129507L, 946802666017L)),
      (GS, Astronomical, Date) -> ((946776266779L, 946800528816L))
    )

  test("TwilightCalcSpec: Sunrise and sunset on 2000-01-01") {
    expected.foreach {
      case ((place, tbType, date), (s, e)) =>
        val (start, end) = TwilightCalc.calculate(tbType, date, place).get
        // The use of a different JulianDate throughout the calculations produces a very slight difference,
        // therefore we allow a couple of milliseconds of tolerance.
        assert((start.toEpochMilli +- 2).isWithin(s))
        assert((end.toEpochMilli +- 2).isWithin(e))
    }
  }
}
