(ns clojure-course.week9.forward_chaining
  (:require [clojure.string :as string :only [ends-with?]])
  (:require [clojure-course.week9.read_dataset_example :as data])
  )

(defn is_man_rule [s]
  (string/ends-with? s "man")
  )

(defn is_woman_rule [s]
  (string/ends-with? s "woman")
  )

(defn has_extra_strength [s]
  (string/ends-with? s "extra strength")
  )

(defn has_regeneration [s]
  (string/ends-with? s "regeneration")
  )

(defn is_bulletproof [s]
  (string/ends-with? s "bulletproof")
  )

(defn has_telepathy [s]
  (string/ends-with? s "telepathy")
  )

(defn is_shape_shifter [s]
  (string/ends-with? s "shape-shifter")
  )

(defn can_fly [s]
  (string/ends-with? s "fly")
  )

(defn reasoning_for_item
  [item rules_to_apply]
  (let [all_rules_executed (atom true)
        ]
    (doseq [rule rules_to_apply]
      (when (false? (rule item))
        (reset! all_rules_executed false)
        )
      )
    @all_rules_executed
    )
  )

(defn perform_reasoning
  [dataset rules_to_apply]
  (let [lines_match (atom [])]
    (doseq [line dataset]
      (when (reasoning_for_item line rules_to_apply)
        (swap! lines_match conj line)
        )
      )
    @lines_match
    )
  )

(println "test")
(def all_lines (data/read_superheroes_dataset))
(println "all_lines =" all_lines)
(def rules1 [can_fly])
(println "result1 =" (perform_reasoning all_lines rules1))



