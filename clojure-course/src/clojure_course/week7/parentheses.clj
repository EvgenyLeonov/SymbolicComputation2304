(ns clojure-course.week7.parentheses
  (:require [clojure-course.week7.parentheses-init-data :as data])
  )

(def must_match {"(" ")", "{" "}", "[", "]"})
(def opposite_must_match { ")" "(", "}" "{", "]" "[" })

(def stack (atom []))

; adds a new element in the end of stack
(defn stack_push [new_value]
  (swap! stack conj new_value)
  (println "stack=" @stack)
  )

; returns the last element and removes it
(defn stack_pop []
  (let [last_element (last @stack)
        _ (reset! stack (into [] (drop-last @stack)))
        ]
    (str last_element)
    )
  )

(defn stack_empty? []
  (empty? @stack)
  )

(defn check_syntax [input_string]
  (let [result (atom true)]
    (doseq [ltr input_string
            :let [letter (str ltr)]
            :when (true? @result)
            ]
      (if (contains? must_match letter)
        (stack_push letter)
        (when (contains? opposite_must_match letter)
          (let [
                opposite (get opposite_must_match letter)
                prev_parenthesis (stack_pop)
                ]
            (when (not= opposite prev_parenthesis)
              (reset! result false)
              )
            )
            )
          )
      )
    @result
    )
  )

(println "string1=" data/correct_string)
(println "result1=" (check_syntax data/correct_string))
(println "string2=" data/incorrect_string)
(println "result2=" (check_syntax data/incorrect_string))

; DEBUG
;(stack_add "1")
;(stack_add "2")
;(stack_pop)
;(stack_pop)




