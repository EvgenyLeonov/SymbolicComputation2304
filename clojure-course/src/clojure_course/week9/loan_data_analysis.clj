(ns clojure-course.week9.loan-data-analysis)

(defn read_customer_personal_data []
  (rest
    (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/customer_personal_data.txt")]
      (reduce conj [] (line-seq rdr)))
    )
  )

(defn read_loans []
  (rest
    (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/loans.txt")]
      (reduce conj [] (line-seq rdr)))
    )
  )

(defn read_payments []
  (rest
    (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/payments.txt")]
      (reduce conj [] (line-seq rdr)))
    )
  )

(defn get_missed_payments_by_customer
  [customer_name]
  )



; DEBUG
;(println (read_customer_personal_data))






