(ns clojure-course.week13.sea_battle_simulation
  (:require [clojure-course.week13.sea_battle_engine :as engine])
  )

(defrecord Coord [x y])
(defrecord Ship [name decks])

(defn run [ships]
  "main method for the simulation"
  (let [hits (atom [])
        ]
    ; number of game turns
    (loop [round_number 1]
      (when (not (engine/all_ships_sunken? ships hits))

        (recur (inc round_number))
        )
      )


    )
  )

; TODO you may analyse output
(def output (run (engine/set_ships)))









