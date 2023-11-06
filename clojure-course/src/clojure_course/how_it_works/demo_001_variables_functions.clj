(ns clojure-course.how_it_works.demo_001_variables_functions)

; these are variables. I can store data using them.
; def defines global variables, visible in the whole scope of namespace.
(def x 100)
(def mom "mama")

; let's print their values
(println "x=" x)
(println "mom=" mom)

; there are local variables with limited visibility.
(defn function_with_local_variables
  []
  (let [first_local_var 555
        second_local_var 444
        sum_of_local_vars (+ first_local_var second_local_var)
        ; the first argument in this couple is underscore -- we omit the argument
        _ (println "functions can be called from here")
        ]
    ; we may use the local variables in local scope only
    ; if we create a variable with "def" here, it will be global
    (println "sum_of_local_vars=" sum_of_local_vars)
    )
  )

(function_with_local_variables)