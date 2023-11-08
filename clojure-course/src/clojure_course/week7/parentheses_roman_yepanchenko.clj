(ns clojure-course.week7.parentheses-roman-yepanchenko)


(def correct_string "var x={1, 2, 3}; y=(1 + [2 x]);")
(def incorrect_string "var x={1, 2, 3]; y=(1 + [2 x]);")

(defn run
  [string]

  (let [
        symbols-for-observing {"{" "}" "}" "{" "(" ")" ")" "(" "[" "]" "]" "["}
        stack (atom [])
        ]

    (doseq [symbol string]
      (if (contains? symbols-for-observing (str symbol))
        (do

          (if (or (empty? @stack) (not (= (last @stack) (get symbols-for-observing (str symbol)))))
            (do
              (reset! stack (conj @stack (str symbol)))
              )
            (do
              (reset! stack (into [] (drop-last @stack)))
              )
            )
          )

        )
      )

    (println (= (count @stack) 0))

    )
  )
(run incorrect_string)


