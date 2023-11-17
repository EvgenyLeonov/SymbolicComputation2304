(ns clojure-course.week9.backward-chaining
  (:require [clojure.string :as string :only [ends-with?]])
  (:require [clojure-course.week9.read_dataset_example :as data])
  )

(defn has_extra_strength [s]
  (when (string/ends-with? s "extra strength")
    "extra strength"
    )
  )

(defn has_regeneration [s]
  (when (string/ends-with? s "regeneration")
    "regeneration"
    )
  )

(defn is_bulletproof [s]
  (when (string/ends-with? s "bulletproof")
    "bulletproof"
    )
  )

(defn has_telepathy [s]
  (when (string/ends-with? s "telepathy")
    "telepathy"
    )
  )

(defn is_shape_shifter [s]
  (when (string/ends-with? s "shape-shifter")
    "shape-shifter"
    )
  )

(defn print_super_powers
  [hero_name dataset]
  (doseq [item dataset]
    (when (= hero_name (first (string/split item #"\ ")))
      (doseq [rule [has_extra_strength
                    has_regeneration
                    is_bulletproof
                    has_telepathy
                    is_shape_shifter]
              :let [rule_result (rule item)]
              ]
        (when (not (nil? rule_result))
          (println rule_result)
          )
        )
      )
    )
  )

; Juggernaut
; Wolverine
(def hero_name "Wolverine")
(println hero_name "has the following super powers:")
(print_super_powers hero_name (data/read_superheroes_dataset))


