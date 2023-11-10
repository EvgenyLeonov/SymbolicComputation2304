(ns clojure-course.week7.parentheses
  (:require [clojure-course.week7.parentheses-init-data :as data])
  )

(def must_match {"(" ")" "{" "}" "[" "]"})

(def stack (atom []))

; adds a new element in the end of stack
(defn stack_add [new_value]
  (swap! stack conj new_value)
  (println "stack=" @stack)
  )

; returns the last element and removes it
(defn stack_pop []
  (let [last_element (last @stack)
        ;_ (println "last_element=" last_element)
        _ (swap! stack drop-last)
        ;_ (println "last_element after removal=" last_element)
        ]
    last_element
    )
  )

(defn check_syntax [input_string]
  (doseq [letter input_string]
    (println "letter=" letter)
    (if (contains? must_match (str letter))
      (println "match")
      ()
      )

    )





  )

(check_syntax data/correct_string)

; DEBUG
;(stack_add "1")
;(stack_add "2")
;(stack_pop)
;(stack_pop)




