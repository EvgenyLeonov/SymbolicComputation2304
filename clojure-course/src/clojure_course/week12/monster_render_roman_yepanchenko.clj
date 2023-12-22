(ns clojure-course.week12.monster_render_roman_yepanchenko)


; 13 x 6
(def layers
  {
   :background {
                :deadend [
                        ["|" "\\" " " " " " " " " " " " " " " " " " " " " "/" "|"]
                        ["|" " " "|" "\\" " " "_" "_" "_" "_" " " "/" "|" " " "|"]
                        ["|" " " "|" " " "|", " " " " " " " ", "|" "" " " "|" " " "|"]
                        ["|" " " "|" " " "|", "_" "_" "_" "_", "|" "" " " "|" " " "|"]
                        ["|" " " "|" "/" " " " " " " " " " " " " "\\" "|" " " "|"]
                        ["|" "/" " " " " " " " " " " " " " " " " " " " " "\\" "|"]
                        ]
                }

    :monsters [
               [
                [-1 -1 -1 -1 -1 -1 "_""_"-1 -1 -1]
                [-1 -1 -1 -1 "/" " " "_" " " "_" "\\"]
                [-1 -1 -1 -1 "|" " " "o" " " "o" " " "|"]
                [-1 -1 -1 -1 "|" " " " " "^" " " " " "|"]
                [-1 -1 -1 " " "\\" "_" "_""_" "_" "/" " " ]
                ]
               ]

   }
  )


(defn render_scene
  [background_layer front_layer]
  ; hack to clear the REPL screen
  (print (str (char 27) "[2J"))
  (loop [row 0]
    (when (< row (count background_layer))
      (let [
            background_row_content (get background_layer row)
            front_row_content (get front_layer row)
            row_content (atom [])
            ]

        (reset! row_content background_row_content)

        (let [index (atom 0)]
          (doseq [symbol front_row_content]
            (if (not (= symbol -1))
                (reset! row_content (assoc @row_content @index symbol))
              )
            (reset! index (inc @index))
            )
          )
         (println (apply str @row_content))
        (recur (inc row)))
      )
    )
  )

(render_scene (:deadend (:background layers)) (get (:monsters layers) 0))






