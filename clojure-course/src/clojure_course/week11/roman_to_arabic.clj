(ns clojure-course.week11.roman_to_arabic)

(def roman_letters {"I" 1, "V" 5, "X" 10, "L" 50, "C" 100, "D" 500, "M" 1000})

(defn roman_to_arabic
  [roman_num]
  (let [result (atom 0)]
    (loop [index 0]
      (when (< index (count roman_num))
        (let [letter (str (get roman_num index))
              letter_next (str (get roman_num (inc index)))
              ;_ (println "letter=" letter "; letter_next=" letter_next)
              val1 (get roman_letters letter)
              val2 (get roman_letters letter_next)
              ;_ (println "val1=" val1 "; val2=" val2)
              ]
          (if (nil? val2)
            (swap! result + val1)
            (if (< val1 val2)
               (swap! result - val1)
               (swap! result + val1)
               )
            )

          (recur (inc index)))
        )
      )
    @result
    )
  )

(println (roman_to_arabic "VI"))
(println (roman_to_arabic "XXI"))
(println (roman_to_arabic "XLVI"))
;(roman_to_arabic "XXI")
;(roman_to_arabic "XLVI")

