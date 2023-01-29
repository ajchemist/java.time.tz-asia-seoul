(ns io.github.ajchemist.java.time.tz-asia-seoul.coerce.alpha
  (:import
   java.time.Instant
   java.time.LocalDate
   java.time.LocalDateTime
   java.time.LocalTime
   java.time.ZoneId
   java.time.ZonedDateTime
   java.time.format.DateTimeFormatter
   java.time.temporal.Temporal
   java.util.Date
   ))


(def ^ZoneId tz-asia-seoul (ZoneId/of "Asia/Seoul"))


;;


(defn zdt
  (^ZonedDateTime [] (ZonedDateTime/now tz-asia-seoul))
  (^ZonedDateTime [^Temporal t] (ZonedDateTime/of t tz-asia-seoul)))


(defn local-date
  ^LocalDate
  []
  (LocalDate/now tz-asia-seoul))


(defn local-dt
  ^LocalDateTime []
  (LocalDateTime/now tz-asia-seoul))


;;


(defprotocol InstantCoercions
  "Coerce between various java time things."
  (^Date -as-inst [x] "Coerce argument to a java.util.Date.")
  (^Instant -as-instant [x] "Coerce argument to a java.time.Instant.")
  (^ZonedDateTime -as-zdt [x] "Coerce argument to a java.time.ZonedDateTime.")
  (^LocalDate -as-ld [x] "Coerce argument to a java.time.LocalDate.")
  (^LocalDateTime -as-ldt [x] "Coerce argument to a java.time.LocalDateTime."))


(extend-protocol InstantCoercions
  Long
  (-as-inst [x] (Date. x))
  (-as-instant [x] (Instant/ofEpochMilli x))
  (-as-zdt [x] (-as-zdt (-as-instant x)))
  (-as-ld [x] (-as-ld (-as-instant x)))
  (-as-ldt [x] (-as-ldt (-as-zdt x)))

  Date
  (-as-inst [x] x)
  (-as-instant [x] (.toInstant x))
  (-as-zdt [x] (.atZone (-as-instant x) tz-asia-seoul))
  (-as-ld [x] (-as-ld (-as-zdt x)))
  (-as-ldt [x] (-as-ldt (-as-zdt x)))

  Instant
  (-as-inst [x] (Date/from x))
  (-as-instant [x] x)
  (-as-zdt [x] (.atZone x tz-asia-seoul))
  (-as-ld [x] (-as-ld (-as-zdt x)))
  (-as-ldt [x] (-as-ldt (-as-zdt x)))

  ZonedDateTime
  (-as-inst [x] (-as-inst (-as-instant x)))
  (-as-instant [x] (.toInstant x))
  (-as-zdt [x] x)
  (-as-ld [x] (.toLocalDate x))
  (-as-ldt [x] (.toLocalDateTime x))

  LocalDateTime
  (-as-inst [x] (-as-inst (-as-zdt x)))
  (-as-instant [x] (-as-instant (-as-zdt x)))
  (-as-zdt [x] (ZonedDateTime/of x tz-asia-seoul))
  (-as-ld [x] (.toLocalDate x))
  (-as-ldt [x] x)

  LocalDate
  (-as-inst [x] (-as-inst (-as-ldt x)))
  (-as-instant [x] (-as-instant (-as-ldt x)))
  (-as-zdt [x] (-as-zdt (-as-ldt x)))
  (-as-ld [x] x)
  (-as-ldt [x] (LocalDateTime/of x (LocalTime/of 0 0))))


(defn as-inst
  ^Date
  [x]
  (-as-inst x))


(defn as-instant
  ^Instant
  [x]
  (-as-instant x))


(defn as-zdt
  ^ZonedDateTime
  [x]
  (-as-zdt x))


(defn as-ld
  ^LocalDate
  [x]
  (-as-ld x))


(defn as-ldt
  ^LocalDateTime
  [x]
  (-as-ldt x))


;;


(defn parse-local-date
  ^LocalDate
  [^String s ^DateTimeFormatter dtf]
  (LocalDate/parse s dtf))


(defn parse-local-dt
  ^LocalDateTime
  [^String s ^DateTimeFormatter dtf]
  (LocalDateTime/parse s dtf))
