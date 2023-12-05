(ns clojure-course.week11.arabic_to_roman)

(def arabic [1000 900 500 400 100 90 50 40 10 5 4 1])
(def roman ["M" "CM" "D" "CD" "C" "XC" "L" "XL" "X" "V" "IV" "I"])

(defn arabic_to_roman
  [arabic_num]
  (let [current_value (atom arabic_num)
        roman_result (atom "")
        ]
    (while (> @current_value 0)
      (loop [index 0]
        (when (< index (count arabic))
          (let [value (int (get arabic index))
                letter (str (get roman index))
                ;_ (println "letter=" letter "; value=" value "; current_value=" @current_value)
                ]
            (if (>= @current_value value)
              (do
                (swap! roman_result str letter)
                (swap! current_value - value)
                )
              (recur (inc index))
              )
            )
          )
        )

      )

    @roman_result
    )
  )

(println (arabic_to_roman 6))
(println (arabic_to_roman 21))
(println (arabic_to_roman 46))


