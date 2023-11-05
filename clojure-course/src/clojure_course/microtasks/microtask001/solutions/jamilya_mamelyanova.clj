(ns clojure-course.microtasks.microtask001.solutions.jamilya-mamelyanova)

(def toes-on-front-paws 5)
(def toes-on-back-paws 4)

; Define the number of toes on different types of legs
; Two legs with four toes on each
(def toes-on-four-toes-legs (* 2 4))

; Two legs with five toes on each
(def toes-on-five-toes-legs (* 2 5))

; Calculate the total number of toes
(def total-toes (+ toes-on-four-toes-legs toes-on-five-toes-legs))

; Print the result
(println "I have" total-toes "toes in total.")
