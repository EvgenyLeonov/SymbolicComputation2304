(ns clojure-course.week9.bank-loan-roman-yepanchenko)

(defrecord Person [name risk salary])
(defrecord Rule [name riskFrom riskTo annualRate loanAmount])

(defn init
  [rules persons]

  (doseq [person persons]

    (let [ruleState (atom false)]
      (doseq [rule rules]

        (if (and (= @ruleState false) (>= (:risk person) (:riskFrom rule)) (<= (:risk person) (:riskTo rule)))
          (do
            (println "The risk is" (:name rule))
            (println rule)
            (println person)

            (reset! ruleState true)
            )
          )
        )
      )
    )
  )


(init [(Rule. "High" 0 40 23 1) (Rule. "Mid" 41 60 10 2) (Rule. "Low" 61 100 8 3)] [(Person. "Mary" 70 40000) (Person. "John" 38 20000)])




