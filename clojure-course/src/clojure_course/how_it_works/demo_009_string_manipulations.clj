(ns clojure-course.how_it_works.demo_009_string_manipulations
  ; good practice to import only things you need
  (:require [clojure.string :as string :only [split]])
  )


(def str_friend "my friend!")
; returns a letter from string by index (starts with 0)
(println (get str_friend 3))
; first and last letters
(println (first str_friend))
(println (last str_friend))
; extracts substring from the first index to the second index
(println (subs str_friend 3 9))
; extracts substring from the given index till the end of the string
(println (subs str_friend 3))
(def str_text "Sentence number one. Sentence number two.")
; splits text using the given regular expressions and returns array of strings
(def str_split (string/split str_text #"\."))
(println str_split)

(def taco_cat "taco cat")
; returns sequence, not a string
(def taco_cat_reversed (reverse taco_cat))
; this how we can return string.
; "apply" function applies "str" function to each element in the given string and combines them together.
(def taco_cat_reversed_str (apply str taco_cat_reversed))

;palindrome
(println "plane >" taco_cat "; reversed >> " taco_cat_reversed "; reversed str >> " taco_cat_reversed_str)








