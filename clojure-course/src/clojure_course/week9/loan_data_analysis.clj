(ns clojure-course.week9.loan-data-analysis
  (:require [clojure.string :only [split]])
  )

(defn read_dataset [filepath]
  (with-open [rdr (clojure.java.io/reader filepath)]
    (let [line (line-seq rdr)]
      (clojure.string/join "\n" line))))

(defn read_customer_personal_data []
  (into [] (rest
             (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/customer_personal_data.txt")]
               (reduce conj [] (line-seq rdr)))
             ))
  )

(defn read_loans []
  (into []
        (rest
          (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/loans.txt")]
            (reduce conj [] (line-seq rdr)))
          )
        )
  )

(defn read_payments []
  (into [] (rest (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/payments.txt")]
                   (reduce conj [] (line-seq rdr)))))
  )

(defn get_missed_payments_by_customer
  [customer_name payments_data]
  (let [missed_payments_count (atom 0)]
    (loop [ind 0]
      (when (< ind (count payments_data))
        (let [payment_info_tokens (clojure.string/split (get payments_data ind) #",")
              person_name (first payment_info_tokens)
              payment (Integer/parseInt (last payment_info_tokens))
              ]
          (when (and (= customer_name person_name)
                     (= 0 payment)
                     )
            (swap! missed_payments_count inc)
            )
          (recur (inc ind)))
        )
      )
    @missed_payments_count
    )
  )

(defn get_missed_payments_by_loan
  [customer_name loan_name payments_data]
  (let [missed_payments_count (atom 0)]
    (loop [ind 0]
      (when (< ind (count payments_data))
        (let [payment_info_tokens (clojure.string/split (get payments_data ind) #",")
              person_name (first payment_info_tokens)
              loan_title (second payment_info_tokens)
              payment (Integer/parseInt (last payment_info_tokens))
              ]
          (when (and (= customer_name person_name)
                     (= loan_name loan_title)
                     (= 0 payment)
                     )
            (swap! missed_payments_count inc)
            )
          (recur (inc ind)))
        )
      )
    @missed_payments_count
    )
  )

(defn get_customer_income
  [customer_name customers_data]
  (let [income (atom 0)]
    (loop [ind_customer 0]
      (when (< ind_customer (count customers_data))
        (let [customer_info_tokens (clojure.string/split (get customers_data ind_customer) #",")
              cust_name (first customer_info_tokens)
              income_value (Integer/parseInt (last customer_info_tokens))
              ]
          (if (= customer_name cust_name)
            (reset! income income_value)
            (recur (inc ind_customer)))
            )
        )
      )
    @income
    )
  )

(defn hypothesis_loan_share_of_salary
  [customers_data loan_data payments_data]
  (loop [ind 0]
    (when (< ind (count loan_data))
      (let [loan_tokens (clojure.string/split (get loan_data ind) #",")
            customer_name (first loan_tokens)
            customer_income (get_customer_income customer_name customers_data)
            loan_title (second loan_tokens)
            loan_amount (Integer/parseInt (get loan_tokens 3))
            loan_income_rate (float (/ loan_amount customer_income))
            missed_payments_by_loan (get_missed_payments_by_loan customer_name loan_title payments_data)
            ]
        (println customer_name "," loan_title "," customer_income "," loan_amount "," loan_income_rate "," missed_payments_by_loan)
        (recur (inc ind)))
      )
    )
  )

(defn hypothesis_income_missed_payments
  [customers_data payments_data]
  (loop [ind_customer 0]
    (when (< ind_customer (count customers_data))
      (let [customer_info_tokens (clojure.string/split (get customers_data ind_customer) #",")
            customer_name (first customer_info_tokens)
            income (Integer/parseInt (last customer_info_tokens))
            missed_payments (get_missed_payments_by_customer customer_name payments_data)
            ]
        (println customer_name "," income "," missed_payments)
        (recur (inc ind_customer)))
      )
    )
  )


;(hypothesis_income_missed_payments (read_customer_personal_data) (read_payments))

(hypothesis_loan_share_of_salary (read_customer_personal_data) (read_loans) (read_payments))

; DEBUG
;(println (read_customer_personal_data))

;(println (get_missed_payments_by_loan "Ivy Allen" "loan_2" (read_payments)))







