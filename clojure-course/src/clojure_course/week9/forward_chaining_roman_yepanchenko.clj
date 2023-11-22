(ns clojure-course.week9.forward-chaining-roman-yepanchenko
  (:require  [clojure.string :as string :only [split, trim]])
  )

(defrecord Rule [name])

(defn find-duplicates [numbers]
  (->> numbers
       (frequencies)
       (filter (fn [[k v]] (> v 1)))
       (keys)))

(defn init
  [rulesSets datasets]

  (let [globalResponse (atom [])]
    (doseq [ruleSet rulesSets]
      ; (println ruleSet)

      (if (= (:operator ruleSet) "and")
        (do
          (let [response (atom ())]
            (doseq [rule (:rules ruleSet)]
              (reset! response (concat @response (get datasets (:name rule))))
              )

            (if (> (count (:rules ruleSet)) 1)
              (reset! globalResponse (conj @globalResponse (into [] (find-duplicates @response))))
              (reset! globalResponse (conj @globalResponse (into [] @response)))
              )
            )
          )
        )

      (if (= (:operator ruleSet) "or")
        (do
          (let [response (atom ())]
            (doseq [rule (:rules ruleSet)]
              (reset! response (distinct (concat @response (get datasets (:name rule)))))
              )

            (reset! globalResponse (conj @globalResponse (into [] @response)))
            )
          )
        )
      )
    @globalResponse
    )
  )

(defn adapter
  [text]
  (let [
        response (atom {})
        hero (atom "")
        temp (atom [])
        superpowerTemp (atom "")
        ]
    (doseq [string text]
      (reset! temp (string/split (string/trim string) #" "))
      (reset! hero (get @temp 0))
      (reset! superpowerTemp (string/trim (apply str (drop 1 (map (fn [aaa] (str " " aaa)) @temp)))))

      (if (contains? @response @superpowerTemp)
        (do
          (reset! response (assoc @response @superpowerTemp (conj (get @response @superpowerTemp) @hero)))
          )
        (do
          (reset! response (assoc @response @superpowerTemp [@hero]))
          )
        )
      )

    @response
    )
  )

(def superheroesText
  "Juggernaut has extra strength\n  Beast has extra strength\n  Deadpool has regeneration\n  Wolverine has regeneration\n  Juggernaut is bulletproof\n  Wolverine is bulletproof\n  Professor_X has telepathy\n  Jean_Grey has telepathy\n  Mystique is shape-shifter\n  Sand_man is shape-shifter\n  Sand_man is bulletproof\n  Magneto is bulletproof\n  Storm can fly\n  Magneto can fly\n  Mystique has extra strength")

(println (init
           [
            {:operator "and" :rules [(Rule. "has extra strength")]}
            {:operator "and" :rules [(Rule. "is bulletproof") (Rule. "is shape-shifter")]}
            {:operator "or" :rules [(Rule. "has regeneration") (Rule. "has telepathy")]}
            ]
           (adapter (string/split superheroesText #"\n"))
           ))




