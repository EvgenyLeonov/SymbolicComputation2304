(ns clojure-course.week9.loan-data-analysis
  (:require [clojure.string :as string :only [split]])
  )

(defn read_dataset [filepath]
  (with-open [rdr (clojure.java.io/reader filepath)]
    (let [line (line-seq rdr)]
      (clojure.string/join "\n" line))))

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
  (rest (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/payments.txt")]
          (reduce conj [] (line-seq rdr))))
  )

(defn get_missed_months [prev_date current_date]
  1
  )

(defn get_missed_payments_by_customer
  [customer_name payments_data]
  (let [current_loan (atom "")
        prev_date (atom "")
        missed_payments_count (atom 0)]
    (for [payment_info payments_data]
      (let [payment_info_tokens (clojure.string/split payment_info #",")
            _ (println "payment_info=" payment_info)
            person_name (first payment_info_tokens)
            loan_name (second payment_info_tokens)
            current_date (str (get payment_info_tokens 2))
            ]
        (println "customer_name=" customer_name)
        (when (= customer_name person_name)
          (if (not= @current_loan loan_name)
            (do
              (println "there")
              (reset! current_loan loan_name)
              (reset! prev_date current_date)
              )
            (do
              (println "here")
              (swap! missed_payments_count + (get_missed_months @prev_date current_date))
              (reset! prev_date current_date)
              )
            )
          )
        )
      )
    @missed_payments_count
    )
  )


(println (get_missed_payments_by_customer "Charlie Holmes" (read_payments)))






; DEBUG
;(println (read_customer_personal_data))






