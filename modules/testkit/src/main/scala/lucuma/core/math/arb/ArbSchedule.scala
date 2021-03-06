// Copyright (c) 2016-2021 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package lucuma.core.math.arb

import cats.syntax.all._
import java.time.Instant
import lucuma.core.math.Interval
import lucuma.core.math.Schedule
import org.scalacheck._
import org.scalacheck.Arbitrary._
import lucuma.core.arb.ArbTime

trait ArbSchedule {
  import ArbTime._

  implicit val arbSchedule: Arbitrary[Schedule] =
    Arbitrary {
      arbitrary[List[Instant]]
        .suchThat(list => list.length === list.distinct.length) // No duplicates
        .map { list =>
          Schedule.unsafe(
            list.sorted
              .grouped(2)
              .collect {
                case List(a, b) => Interval.unsafe(a, b)
              }
              .toList
          )
        }
    }

  implicit val cogenSchedule: Cogen[Schedule] =
    Cogen[List[Instant]].contramap(_.intervals.flatMap(i => List(i.start, i.end)))
}

object ArbSchedule extends ArbSchedule
