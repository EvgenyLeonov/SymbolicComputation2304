(ns clojure-course.week12.pacman_fps
  (:require [clojure-course.week11.scene_rendering :as scene_rendering])
  (:require [clojure-course.week12.maze_map :as maze_map_rendering])
  )

(def maze_map maze_map_rendering/maze_map)
(def PACMAN 1)
(def MONSTER 3)
(def FOOD 4)
(defrecord Object_on_map [id row column])

(def direction_of_sight (atom "E"))
(def pacman_current_row (atom 1))
(def pacman_current_column (atom 1))
(def food_pieces (atom [
                        (Object_on_map. FOOD 1 3)
                        (Object_on_map. FOOD 3 3)
                        ]))
(def monsters (atom [(Object_on_map. MONSTER 3 1)]))

(defn remove_food [row column]
  "finds a particular food item and removes it from the vector"
  (loop [index 0]
    (when (< index (count @food_pieces))
      (let [item (get @food_pieces index)]
        (if (and (= (:row item) row) (= (:column item) column))
          (reset! food_pieces (into [] (concat (subvec @food_pieces 0 index)
                                                (subvec @food_pieces (inc index)))))
          (recur (inc index))
          )
        )
      )
    )
  )

(defn main_loop
  []
  ; calculate a condition to finish the game
  ; there are two conditions:
  ; 1) all FOOD are consumed (WIN)
  ; 2) coordinates of PACMAN match with coordinates of a monster (LOSE)
  (let [win? (atom false)
        lose? (atom false)
        ]
    (while (and (false? @win?) (false? @lose?))
      (let [
            ; current state of dynamic game objects (Pacman, monsters, food)
            objects (into []
                          (concat
                            [(Object_on_map. PACMAN @pacman_current_row @pacman_current_column)]
                            @food_pieces
                            @monsters
                            )
                          )
            scene_def (maze_map_rendering/get_scene_definition maze_map @pacman_current_row @pacman_current_column @direction_of_sight objects)
            _ (println "scene_def=" scene_def)
            scene_instr (scene_rendering/prepare_instructions_for_scene scene_def)
            ]
        ; render the current scene
        (print (str (char 27) "[2J"))
        (scene_rendering/render_scene scene_instr)
        (println "Food to eat:" (count @food_pieces))
        (maze_map_rendering/render_maze_map maze_map @direction_of_sight objects)

        ; ask a user where to go next and change states of dynamic game objects
        (println "Input your next move:")
        ;(println "w -- forward")
        ;(println "a -- turn left")
        ;(println "d -- turn right")
        ;(flush)
        (let [user_input (read-line)]
          ; TODO validate user input
          (if (or (= "a" user_input) (= "d" user_input))
            (let [new_direction_of_sight (maze_map_rendering/get_new_sight_direction @direction_of_sight (= "a" user_input))]
              (reset! direction_of_sight new_direction_of_sight)
              )
            (let [direction_of_sight_upper (clojure.string/upper-case @direction_of_sight)
                  ; _ (println "direction_of_sight_upper =" direction_of_sight_upper)
                  modificator_row (cond
                                    (= "N" direction_of_sight_upper) -1
                                    (= "S" direction_of_sight_upper) 1
                                    :else 0
                                    )
                  modificator_column (cond
                                       (= "W" direction_of_sight_upper) -1
                                       (= "E" direction_of_sight_upper) 1
                                       :else 0
                                       )
                  ;_ (println modificator_row " ; " modificator_column)
                  new_row (+ @pacman_current_row modificator_row)
                  new_column (+ @pacman_current_column modificator_column)
                  ]
              (when (not (maze_map_rendering/is_wall? maze_map new_row new_column))
                (reset! pacman_current_row new_row)
                (reset! pacman_current_column new_column)

                ; check collision with Food or Monster
                (let [object_here (maze_map_rendering/get_id_object objects
                                                                    @pacman_current_row
                                                                    @pacman_current_column)]
                  (cond
                    ; monster and LOSE condition are first
                    (= object_here MONSTER) (reset! lose? true)
                    (= object_here FOOD)
                    (do
                      ; remove this Food item from vector
                      (remove_food @pacman_current_row @pacman_current_column)
                      (when (empty? @food_pieces)
                        (reset! win? true)
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    (when @win?
      (println "YOU WON!")
      )
    (when @lose?
      (println "YOU LOSE!")
      )
    )
  )


(main_loop)







