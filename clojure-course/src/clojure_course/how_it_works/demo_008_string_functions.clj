(ns clojure-course.how_it_works.demo_008_string_functions
  (:require [clojure.string :as string :only [index-of, last-index-of, trim]]))


; run the functions and see its output
(println (str 123))
(println (str 1 2 3))
(println (str [1 2 3]))
(println (apply str [1 2 3]))
(println "Hello" "my friend" "!")
(println (format "Hello, %s" "my friend!"))
(println (format "This is 165 >>> %X" 165))
(println (format "Leading zeros %07d" 1234))
(println (format "Float formatting %.2f" 3.14563223))
(println (count "my friend!"))

(def abra "abracadabra")
(println (string/index-of abra "r"))
(println (string/last-index-of abra "r"))
(def london "     London is the capital of Great Britain     ")
(println (string/trim london))









