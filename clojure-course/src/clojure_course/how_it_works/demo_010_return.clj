(ns clojure-course.how-it-works.demo-010-return)

; Clojure returns result of the last statement of a function
; in Clojure EACH statement is a function

(defn simple_function []
  ; simply returns number
  42
  )

(defn function_with_let []
  (let [a 10 b 15]
    ; this is the last statement, and it will be returned
    (+ a b)
    )
  ; let function returns a+b.
  ; And, also, this function -- is the last one in "function_with_let"
  ; so, return value of "let" function will be return value of "function_with_let"
  )

(println "simple_function=" (simple_function))
(println "function_with_let=" (function_with_let))

