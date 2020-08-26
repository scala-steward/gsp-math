// Copyright (c) 2016-2020 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package gsp.sql
package enum

import doobie._, doobie.implicits._
import gsp.sql.FiniteDuration
import shapeless.record._

object F2Enums {

  val enums: List[ConnectionIO[EnumDef]] =
    List(

      EnumDef.fromQuery("F2Disperser", "Flamingos2 dispersers") {
        type R = Record.`Symbol("tag")-> String, Symbol("shortName")-> String, Symbol("longName")-> String, Symbol("wavelength")-> Wavelength.Um`.T
        sql"SELECT id, id tag, short_name, long_name, wavelength FROM e_f2_disperser".query[(String, R)]
      },

      EnumDef.fromQuery("F2Filter", "Flamingos2 filters") {
        type R = Record.`Symbol("tag")-> String, Symbol("shortName")-> String, Symbol("longName")-> String, Symbol("wavelength")-> Option[Wavelength.Um], Symbol("obsolete")-> Boolean`.T
        sql"SELECT id, id tag, short_name, long_name, wavelength, obsolete FROM e_f2_filter".query[(String, R)]
      },

      EnumDef.fromQuery("F2Fpu", "Flamingos2 focal plane units") {
        type R = Record.`Symbol("tag")-> String, Symbol("shortName")-> String, Symbol("longName")-> String, Symbol("slitWidth")-> Int, Symbol("decker")-> String, Symbol("obsolete")-> Boolean`.T
        sql"SELECT id, id tag, short_name, long_name, slit_width, decker, obsolete FROM e_f2_fpu".query[(String, R)]
      },

      EnumDef.fromQuery("F2LyotWheel", "Flamingos2 Lyot wheel") {
        type R = Record.`Symbol("tag")-> String, Symbol("shortName")-> String, Symbol("longName")-> String, Symbol("plateScale")-> Double, Symbol("pixelScale")-> Double, Symbol("obsolete")-> Boolean`.T
        sql"SELECT id, id tag, short_name, long_name, plate_scale, pixel_scale, obsolete FROM e_f2_lyot_wheel".query[(String, R)]
      },

      EnumDef.fromQuery("F2ReadMode", "Flamingos2 read modes") {
        type R = Record.`Symbol("tag")-> String, Symbol("shortName")-> String, Symbol("longName")-> String, Symbol("description")-> String, Symbol("minimumExposureTime")-> FiniteDuration.Seconds, Symbol("recommendedExposureTime")-> FiniteDuration.Seconds, Symbol("readoutTime")-> FiniteDuration.Seconds, Symbol("readCount")-> Int, Symbol("readNoise")-> Double`.T
        sql"SELECT id, id tag, short_name, long_name, description, minimum_exposure_time, recommended_exposure_time, readout_time, read_count, read_noise FROM e_f2_read_mode".query[(String, R)]
      },

      EnumDef.fromQuery("F2WindowCover", "Flamingos2 window cover state") {
        type R = Record.`Symbol("tag")-> String, Symbol("shortName")-> String, Symbol("longName")-> String`.T
        sql"SELECT id, id tag, short_name, long_name FROM e_f2_window_cover".query[(String, R)]
      }

    )

}