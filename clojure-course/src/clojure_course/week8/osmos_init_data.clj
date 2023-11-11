(ns clojure-course.week8.osmos_init_data)

(defrecord Molecule [name x y value])

(def all_molecules
  [
   (Molecule. "green" 3 1 3)
   (Molecule. "yellow" 2 2 4)
   (Molecule. "orange" 3 4 5)
   (Molecule. "red" 2 5 8)
  ]
  )

(def your_molecule
  (Molecule. "blue" 1 1 7)
  )





