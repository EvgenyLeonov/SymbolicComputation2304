(ns clojure-course.week11.warcraft)

(def gold_amount (agent 0))
(def lumber_amount (agent 0))
(def barrack (agent nil))

; initially, we don't have trained footmen
(def footmen_count (atom 0))

(defn peasant_gather_resources
  [current_value resource_name]
  (Thread/sleep (if (= resource_name "gold")
                  1500
                  2000
                  ))
  (println "Peasant gathered" resource_name)
  (inc current_value)
  )

(defn train_unit
  [current_value unit_name]
  (println "Training...")
  (Thread/sleep 4000)
  (swap! footmen_count inc)
  (println unit_name "trained")
  )

(defn if_enough_resources
  [key ref old_val new_val]
  (let [gold (if (= key :goldWatcher)
               new_val
               @gold_amount
               )
        lumber (if (= key :lumberWatcher)
                 new_val
                 @lumber_amount
                 )
        ]
    (if (and (>= gold 2) (>= lumber 1))
      (do
        (remove-watch gold_amount :goldWatcher)
        (remove-watch lumber_amount :lumberWatcher)
        (send barrack train_unit "Footman")
        )
      (do
        (send gold_amount peasant_gather_resources "gold")
        (send lumber_amount peasant_gather_resources "lumber")
        )
      )
    )
  )

(add-watch gold_amount :goldWatcher if_enough_resources)
(add-watch lumber_amount :lumberWatcher if_enough_resources)

(println "Game started")

(send gold_amount peasant_gather_resources "gold")
(send lumber_amount peasant_gather_resources "lumber")





