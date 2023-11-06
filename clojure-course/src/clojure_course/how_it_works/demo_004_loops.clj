(ns clojure-course.how_it_works.demo_004_loops)

; loop int value from 0 to 10
(loop [x 0]
  (if (> x 10)
    (do
      ; we return x and stop the loop. it works as return.
      x
      )
    (do
      (println "x=" x)
      ; otherwise...
      ; the next row increases i on 1 and goes back to loop function
      ; to perform the next iteration
      (recur (inc x))
      )
    )
  )

(println "-----------------")
; this is atom. It points to a variable in memory we can change.
; we call it "y" and set value as 0
(def y (atom 10))
; loop int value from 10 to 0
; remember, "y" points to some object in memory.
; If you print "y", you will see something like "object...". This is Java toString() method.
; to get value of atom, use "@" symbol
(while (>= @y 0)
  (println "y=" @y)
  ; this function applies the function standing on second position
  ; on the atom standing on the first position
  ; it is the same as (dec @y)
  (swap! y dec)
  )

(println "-----------------")
; two vectors declared
(def vector1 ["a" "b" "c"])
(def vector2 [1 2 3])
; let's loop via one vector
(for [just_a_variable vector1
      ]
  (println "just_a_variable=" just_a_variable)
  )

; let's loop two vectors: one inside another
; it works like loop inside another loop
; like this:
; for i=0;i < 10; i++
; for j=0;j < 10; j++

(for [var_from_vector1 vector1
      var_from_vector2 vector2
      ]
  ; it creates and prints small vectors of two values: the first one is taken from vector1, the second one -- from vector2.
  ; as usual, it iterates inner loop (vector2) for each value from outer loop (vector1)
  (println [var_from_vector1 var_from_vector2])
  )

; another way to iterate
(doseq [var_for_iteration vector1]
  ; do something
  (println "var_for_iteration=" var_for_iteration)
  )