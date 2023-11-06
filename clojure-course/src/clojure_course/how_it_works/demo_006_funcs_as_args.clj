(ns clojure-course.how_it_works.demo_006_funcs_as_args
  ; we can import functions from other namespaces.
  ; At the left is a namespace from where we import.
  ; At the right two functions from this namespace
  (:use [clojure.repl :only [doc source]])
  )

; Let's create a function with anonymous function in it
(defn create_func_multiply
  []
  (fn [a b]
    (* a b))
  )

; this function calls the previous one
(def perform_multiplication
  (create_func_multiply)
  )

; and we can call it with arguments. The arguments will be sent in very end of the functions chain.
(println "perform_multiplication =" (perform_multiplication 5 4))

; let's create more functions with encapsulated arguments
(def perform_multiplication_5_4
  (perform_multiplication 5 4)
  )

(def perform_multiplication_6_8
  (perform_multiplication 6 8)
  )

; and we can just call them
(println "perform_multiplication (5_4 and 6_8) =" perform_multiplication_5_4 perform_multiplication_6_8)

(println "========")

; Functions as arguments of another function

; we define a function "funcs_as_args" with 4 arguments
; and let's say we decided to have first two as functions and the last two as some values
; all can have any names. I put f,v just for convenience.
(defn funcs_as_args
  [f1 f2 v1 v2]
  (f1 (f2 v1 v2))
  )

; here we send two functions first and then two numbers
(println "funcs_as_args [1] = " (funcs_as_args inc perform_multiplication 9 9))
(println "funcs_as_args [2] = " (funcs_as_args #(/ % 9) perform_multiplication 9 9))

; comp function runs a chain of functions from right to left
; firstly, #(* % 5) and its result goes to #(/ 100 %)
; and then its result goes to print function
; % -- is a placeholder for the only argument.
; if we would have more arguments, we should use %1, %2, ...
; please refer to week2 abracadabra task
(def func_created_by_comp
  (comp #(println "func_created_by_comp=" %) #(/ 100 %) #(* % 5))
  )

(func_created_by_comp 100)
; use doc function to get documentation from a function of interest
;(doc comp)
