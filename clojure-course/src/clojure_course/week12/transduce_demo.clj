(ns clojure-course.week12.transduce_demo)
; (map inc)
(def my_even_nums_transducer (comp (filter even?) ))
(println "transducers =" my_even_nums_transducer)
(def v1 (into [] (eduction my_even_nums_transducer (range 10))))
(println "eduction =" v1)
(def v2 (transduce my_even_nums_transducer + (range 10)))
(println "transduce =" v2)
