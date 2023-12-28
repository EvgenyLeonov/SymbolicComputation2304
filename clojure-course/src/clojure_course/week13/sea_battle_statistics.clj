(ns clojure-course.week13.sea_battle_statistics
  (:require [clojure-course.week13.sea_battle_engine :as engine])
  )

(defrecord Coord [x y])
(defrecord Ship [name decks])
(defrecord Output [number_of_turns ships])

(defn run [ships]
  "main method for the simulation"
  (let [hits (atom [])
        turn_number (atom 1)]
    ; simplest brute force solution
    ; we traverse all squares (10x10) until all ships are sunk
    ; absolute maximum number of turns = 100
    (loop [x 1]
      (loop [y 1]
        (when
          (and (not (engine/all_ships_sunken? ships hits))
               (<= y 10)
               )
          (engine/shot! x y ships hits false)
          (swap! turn_number inc)
          (recur (inc y))
          )
        )
      (when (and (not (engine/all_ships_sunken? ships hits))
                 (<= x 10)
                 )
        (recur (inc x))
        )
      )
    ;(println)
    ;(engine/draw_map (engine/create_map ships hits))
    (Output. (dec @turn_number) ships)
    )
  )

(defn average [vect]
  (float (/ (reduce + vect) (count vect)))
  )

(def max_games_count 100)
; here we store statistics: number of turns and positions of ships
(def turn_numbers (atom []))
(def ships_positions (atom { }))
(loop [x 1]
  (when (<= x 10)
    (loop [y 1]
      (when (<= y 10)
        (swap! ships_positions assoc (str x "-" y) 0)
        (recur (inc y))
        )
      )
    (recur (inc x))
    )
  )
(loop [game_num 1 ]
  (when (<= game_num max_games_count)
    ;(println "Game #" game_num "...")
    (let [ships (engine/set_ships)
          output (run ships)
          ]
      (swap! turn_numbers conj (:number_of_turns output))
      (doseq [ship ships]
        (doseq [deck (:decks ship)
                :let [key (str (:x deck) "-" (:y deck))]
                ]
          (swap! ships_positions update key inc)
          )
        )
      )
    (recur (inc game_num))
    )
  )

(println "Games completed:" max_games_count)
(def average_turns (average @turn_numbers))
(println "Turns count average =" average_turns)
;(println @ships_positions)
(println "Matrix:")
(loop [y 1]
  (when (<= y 10)
    (loop [x 1]
      (when (<= x 10)
        (print (get @ships_positions (str x "-" y)))
        (print ",")
        (recur (inc x))
        )

      )
    (println)
    (recur (inc y))

    )

  )








