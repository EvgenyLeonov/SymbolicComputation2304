(ns clojure-course.microtasks.microtask002.solutions.jamilya-mamelyanova)

; Define a function to calculate the square area of a rectangle
(defn rectangle-square [length width]
  (* length width))

; Define a vector to store the dimensions of different dragon parts (in meters)
(def dragon-parts [[2 3] ; Head and neck
                   [5 4] ; Body and paws
                   [1 3] ; Tail
                   ])

; Calculate the total square area for all dragon parts
(def total-square (apply + (map (partial apply rectangle-square) dragon-parts)))

; Calculate the number of cans of yellow paint needed (2 cans per square meter)
(def cans-of-paint (* 2 total-square))

; Print the result
(println "Xavier needs" cans-of-paint "cans of yellow paint to repaint the dragon.")

