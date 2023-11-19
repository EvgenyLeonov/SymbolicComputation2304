(ns clojure-course.week8.osmos-roman-yepanchenko
  (:require [clojure-course.week8.osmos_init_data :as data])
  )

(defn init
  [myMolecule, all_molecules]

  (let [
        theBestVariant (atom nil)
        theBestVariantCalculatedPrice (atom -1)
        theBestVariantCalculatedProfit (atom -1)
        theBestVariantIndex (atom -1)
        a_all_molecules (atom nil)
        index (atom 0)
        ]

    (reset! a_all_molecules all_molecules)

    (doseq [molecule @a_all_molecules]
      (let [
            calculatedProfit(atom 0)
            calculatedPrice (atom 0)
            calculatedXdif (atom 0)
            calculatedYdif (atom 0)
            ]

        (if (<= (:value molecule) (:value myMolecule))
          (do

            (reset! calculatedXdif (abs (- (:x molecule) (:x myMolecule))))
            (reset! calculatedYdif (abs (- (:y molecule) (:y myMolecule))))
            (reset! calculatedPrice (+ @calculatedXdif @calculatedYdif))
            (reset! calculatedProfit (- (:value molecule) (+ @calculatedXdif @calculatedYdif)))

            ; Debug
            ; (println molecule)
            ; (println "x:calculated dif" @calculatedXdif "| y:calculated dif" @calculatedYdif)
            ; (println "Price:" @calculatedPrice "| Profit (value - price):" @calculatedProfit)

            (if
              (and
                (>= (- (:value myMolecule) @theBestVariantCalculatedPrice) (:value molecule))
                (or
                  (= @theBestVariant nil)
                  (and (< @theBestVariantCalculatedProfit @calculatedProfit))
                  )
                )
              (do
                ; Debug
                ; (println "> NEW BEST PROFIT POINT")
                (reset! theBestVariant molecule)
                (reset! theBestVariantCalculatedPrice @calculatedPrice)
                (reset! theBestVariantCalculatedProfit @calculatedProfit)
                (reset! theBestVariantIndex @index)
                )
              )

            ; (println " ")
            )
          )
        )

      (reset! index (inc @index))
      )

    (let [
          newMyMolecule (atom nil)
          ]



      ;(println @theBestVariant)
      ; (println @theBestVariantCalculatedPrice)
      (reset! newMyMolecule (assoc (assoc (assoc myMolecule :x (:x @theBestVariant)) :y (:y @theBestVariant)) :value (+ (:value myMolecule) @theBestVariantCalculatedProfit)))

      ;(println @newMyMolecule)

      (println "Start position and value" myMolecule)
      (println "Next victim" @theBestVariant)
      (println "New position and value" @newMyMolecule)
      (println "")

      (if (> (- (count all_molecules) 1) 0)
        (do
          (init @newMyMolecule (into [] (remove #{@theBestVariant} all_molecules)))
          )
        )
      )
    )
  )


(init data/your_molecule data/all_molecules)
