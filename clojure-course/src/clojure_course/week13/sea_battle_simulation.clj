(ns clojure-course.week13.sea_battle_simulation
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
          (engine/shot! x y ships hits true)
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
    (println)
    (engine/draw_map (engine/create_map ships hits))
    (Output. (dec @turn_number) ships)
    )
  )

; TODO you may analyse output
; TODO you should run it hundreds of times to collect statistics
(def output (run (engine/set_ships)))
(println "number of turns =" (:number_of_turns output))
(println "ships =" (:ships output))








