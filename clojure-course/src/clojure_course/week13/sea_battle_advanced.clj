(ns clojure-course.week13.sea_battle_advanced
  (:require [clojure-course.week13.sea_battle_engine :as engine])
  )

(def ships_statistics [[6 6 100] [6 4 100] [4 6 100] [4 4 100] [6 2 76] [10 4 69] [1 6 69] [1 4 63] [10 6 59] [2 2 54] [6 5 52] [4 5 52] [9 2 50] [2 9 50] [6 8 48] [5 6 48] [5 4 48] [4 8 48] [4 2 48] [9 9 46] [8 2 28] [5 2 28] [3 2 28] [2 8 26] [2 6 26] [2 5 26] [2 3 26] [8 9 24] [6 9 24] [5 9 24] [3 9 24] [9 8 22] [9 6 22] [9 5 22] [9 3 22] [4 10 20] [5 10 19] [6 10 18] [1 7 17] [5 1 16] [7 10 15] [10 3 15] [6 1 14] [4 1 14] [3 10 13] [10 5 13] [10 2 13] [1 5 13] [7 1 12] [3 1 12] [10 7 11] [1 8 11] [8 1 10] [10 8 9] [10 10 9] [1 9 9] [8 10 8] [2 1 8] [10 9 8] [2 10 7] [10 1 7] [1 10 7] [9 10 5] [9 1 5] [1 3 5] [1 1 3] [1 2 2] [9 7 0] [9 4 0] [8 8 0] [8 7 0] [8 6 0] [8 5 0] [8 4 0] [8 3 0] [7 9 0] [7 8 0] [7 7 0] [7 6 0] [7 5 0] [7 4 0] [7 3 0] [7 2 0] [6 7 0] [6 3 0] [5 8 0] [5 7 0] [5 5 0] [5 3 0] [4 9 0] [4 7 0] [4 3 0] [3 8 0] [3 7 0] [3 6 0] [3 5 0] [3 4 0] [3 3 0] [2 7 0] [2 4 0]])

(def max_games_count 100)
(def turn_numbers (atom []))

(defrecord Output [number_of_turns ships])

(defn run [ships]
  "main method for the simulation"
  (let [hits (atom [])
        turn_number (atom 1)]
    (loop [index 0]
      (when (< index (count ships_statistics))
        (let [item (get ships_statistics index)
              x (first item)
              y (second item)
              ]
          (when
            (not (engine/all_ships_sunken? ships hits))
            (engine/shot! x y ships hits false)
            (swap! turn_number inc)
            (recur (inc index))
            )
          )
        )
      )
    (Output. (dec @turn_number) ships)
    )
  )

(loop [game_num 1]
  (when (<= game_num max_games_count)
    ;(println "Game #" game_num "...")
    (let [ships (engine/set_ships)
          output (run ships)
          ]
      (swap! turn_numbers conj (:number_of_turns output))
      )
    (recur (inc game_num))
    )
  )

(defn average [vect]
  (float (/ (reduce + vect) (count vect)))
  )

(println "Games completed:" max_games_count)
(def average_turns (average @turn_numbers))
(println "Turns count average =" average_turns)






