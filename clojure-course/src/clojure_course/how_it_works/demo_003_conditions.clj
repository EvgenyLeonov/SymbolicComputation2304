(ns clojure-course.how_it_works.demo_003_conditions)

(def x 5)

(defn if_func
  []
  ; "if" supports only one function/statement for "true" and one function/statement for "false"
  (if (= x 5)
    (println "x equals 5")
    (println "x not equals 5")
    )
  )

(defn if_func_mult
  []
  ; if we want to have several sentences, we should pack them in "do". Consider it as a container.
  (if (= x 5)
    (do
      (println "x equals 5")
        (println "let's do something")
      )
    (do
      (println "x not equals 5")
        (println "let's do something")
      )
    )
  )

(defn when_func
  []
  ; elegant option for conditional statement if you have just one sentence for "true"
  (when (= 5 x)
    (println "x equals 5")
    )
  )

(defn cond_func
  []
  ; this function performs checks one by one, from top to bottom
  ; if a check is "true", the corresponding code executed. All other checks are ignored
  (cond
    (< x 3) "x has another value"
    (= x 5) (do
              (println "x equals 5")
              (println "do something else")
              )
    ; for all other cases
    :else 42
    )
  )

(defn case_func
  []
  ; case checks particular options
  (case x
    3 ("x equals 3")
    5 (println "x equals 5")
    )
  )

(if_func)
(if_func_mult)
(when_func)
(cond_func)
(case_func)







