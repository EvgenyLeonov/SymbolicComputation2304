(ns clojure-course.week9.bank_loan)

(def LOW "low")
(def MEDIUM "medium")
(def HIGH "high")

(defn define_risk_profile
  [credit_rating]
  (cond
    (<= credit_rating 40) HIGH
    (<= credit_rating 60) MEDIUM
    :else LOW
    )
  )

(defn define_annual_rate
  [risk_profile]
  (cond
    (= risk_profile HIGH) 12
    (= risk_profile MEDIUM) 10
    :else 8
    )
  )

(defn define_loan_amount_multiplier
  [risk_profile]
  (cond
    (= risk_profile HIGH) 1
    (= risk_profile MEDIUM) 2
    :else 3
    )
  )

(defn calculate_for_person
  [person_name credit_rating month_salary]
  (println "-------------")
  (println person_name)
  (println "-------------")
  (let [
        risk_profile (define_risk_profile credit_rating)
        _ (println "if credit rating is" credit_rating ", then Risk is" risk_profile)
        year_salary (* 12 month_salary)
        annual_rate (define_annual_rate risk_profile)
        amount_multiplier (define_loan_amount_multiplier risk_profile)
        ]
    (println "As result, for" person_name "the bank will lend" (* amount_multiplier year_salary) "CZK at" annual_rate "%")
    )
  )

(calculate_for_person "Mary" 70 40000)
(calculate_for_person "John" 38 20000)



