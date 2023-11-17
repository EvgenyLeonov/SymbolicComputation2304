(ns clojure-course.week9.forward_chaining
  (:require [clojure.string :as string :only [ends-with?]])
  (:require [clojure-course.week9.read_dataset_example :as data])
  )

(defn check_rule
  [dataset rule]
  (let [output_items (atom [])]
    (doseq [item dataset]
      (when (true? (rule item))
        (swap! output_items conj item)
        )
      )
    @output_items
    )
  )

(defn get_names [items]
  (into []
        (map #(first (string/split % #"\ ")) items)
        )
  )

(defn has_extra_strength [dataset]
  (check_rule dataset #(string/ends-with? % "extra strength"))
  )

(defn has_regeneration [dataset]
  (check_rule dataset #(string/ends-with? % "regeneration"))
  )

(defn is_bulletproof [dataset]
  (check_rule dataset #(string/ends-with? % "bulletproof"))
  )

(defn has_telepathy [dataset]
  (check_rule dataset #(string/ends-with? % "telepathy"))
  )

(defn is_shape_shifter [dataset]
  (check_rule dataset #(string/ends-with? % "shape-shifter"))
  )

(defn can_fly [dataset]
  (check_rule dataset #(string/ends-with? % "fly"))
  )

(defn and! [arg1 arg2]
  (let [names1 (get_names arg1)
        names2 (get_names arg2)
        names_output (atom [])
        ]
    (doseq [n names1
            :when (.contains names2 n)
            ]
      (swap! names_output conj n)
      )
    @names_output
    )
  )

(defn or! [arg1 arg2]
  (distinct (concat arg1 arg2))
  )

(def dataset (data/read_superheroes_dataset))
(println "Super heroes with extra strength =" (get_names (has_extra_strength dataset)) )
(println "Super heroes who is a shape-shifter AND is bulletproof =" (get_names (and! (is_shape_shifter dataset) (is_bulletproof dataset))))
(println "Super heroes who has telepathy OR regeneration =" (get_names (or! (has_telepathy dataset) (has_regeneration dataset))))







