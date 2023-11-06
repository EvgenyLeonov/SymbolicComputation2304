(ns clojure-course.how_it_works.demo_005_more_about_funcs)

; in this namespace you can see how to define functions in different ways
; f1, f2, f3 do the same

(defn f1
  ; arguments
  [a b]
  ; one function in body
  (+ a b)
  )
(def f2
  ; this is anonymous function - it has no name
  ; input arguments for f2 will be sent down below -> to fn
  (fn [a b]
    (+ a b))
  )
(def f3
  ; yes, the same two input arguments. If we had just one input argument, we may use % without number
  #(+ %1 %2)
  )

; they all work the same
(println "f1=" (f1 2 3))
(println "f2="(f2 2 3))
(println "f3="(f3 2 3))
(println "========")
(let
  ; in local scope we may define not only variable but also functions
  [internal_func (fn [a b]
                   (println "result of internal function=" (+ a b))
                   )
   ; we may create variable that points to a function defined in outer scope
   ; when we call "func_from_outer_scope", it will run "f1" function
   func_from_outer_scope f1
   _ (internal_func 2 3)
   _ (println "func_from_outer_scope="  (func_from_outer_scope 50 25))
   ]
  )

; uncomment it to see that "internal_func" is unknown function
; it defined and visible for the internal scope only
;(internal_func 2 3)
