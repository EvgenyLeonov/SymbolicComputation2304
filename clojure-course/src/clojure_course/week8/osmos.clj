(ns clojure-course.week8.osmos
  (:require [clojure-course.week8.osmos_init_data :as data])
  )

(defrecord Molecule [name x y value])

(defn print_molecules
  [molecules_in_game]
  (if (not (empty? molecules_in_game))
    (doseq [mol molecules_in_game]
      (print (:name mol) " ")
      )
    (print "none")
    )
  )

(defn consume_others
  [my_mol all_mols]
  (let [my_molecule (atom my_mol)
        molecules_in_game (atom all_mols)
        round (atom 0)
        can_continue (atom true)
        ]
    (while (and (true? @can_continue)
                (not (empty? @molecules_in_game)))
      (swap! round inc)
      (println "--------------")
      (println "Round" @round)
      (println "--------------")
      ; Let's range molecules to find the most profitable
      ; the most profitable means it gets utmost energy for our molecule
      (let [best_molecule (atom nil)
            max_profit (atom 0)
            molecule_index (atom -1)
            best_molecule_index (atom -1)
            my_mol_val (:value @my_molecule)
            ]
        (doseq [mol @molecules_in_game]
          (let [
                _ (swap! molecule_index inc)
                my_mol_x (:x @my_molecule)
                my_mol_y (:y @my_molecule)
                mol_x (:x mol)
                mol_y (:y mol)
                mol_val (:value mol)
                distance (+ (abs (- my_mol_x mol_x))
                            (abs (- my_mol_y mol_y))
                            )
                can_consume? (> my_mol_val (+ mol_val distance))
                profit (- mol_val distance)
                _ (println "for" (:name mol) "distance" distance ";can_consume?" can_consume? ";profit" profit)
                ]
            (when (and
                    (true? can_consume?)
                    ; we need to eat the last molecule even if it is not profitable
                    (or (= (count @molecules_in_game) 1) (> profit @max_profit))
                    )
              (reset! best_molecule mol)
              (reset! max_profit profit)
              (reset! best_molecule_index @molecule_index)
              (println "Best molecule. Name:" (:name @best_molecule) "; Profit:" @max_profit "; mol index:" @best_molecule_index)
              )
            )
          )
        (if (empty? @best_molecule)
          (do
            (reset! can_continue false)
            (println "Can't find any molecule to consume")
            )
          (do
            (println "go eat" (:name @best_molecule))
            (reset! my_molecule (Molecule. "blue"
                                           (:x @best_molecule)
                                           (:y @best_molecule)
                                           (+ my_mol_val @max_profit)
                                           ))
            ; create a new vector for molecules; Put there all molecules except eaten.
            (reset! molecules_in_game
                    (into [] (concat (subvec @molecules_in_game 0 @best_molecule_index)
                                     (subvec @molecules_in_game (inc @best_molecule_index))))

                    )

            (println "My x:" (:x @my_molecule) "; y:" (:y @my_molecule) "; value:" (:value @my_molecule))
            (println "Molecules left:")
            (print_molecules @molecules_in_game)
            (println)
            )
          )
        )
      )
    )
  )

(consume_others data/your_molecule data/all_molecules)





