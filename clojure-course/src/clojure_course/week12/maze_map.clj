(ns clojure-course.week12.maze_map)

; rows = 0-5; columns = 0-7
; 0 - empty
; 1 - player (current positions)
; 2 - wall
; 3 - monster
; 4 - food (Pacman eats this)

(def PACMAN 1)
(def WALL 2)
(def MONSTER 3)
(def FOOD 4)

; all are boolean except o1, o2 -- they are ids of objects
(defrecord Scene_definition [c1 c2 c3 l1 l2 r1 r2 o1 o2])
(defrecord Object_on_map [id row column])

(def maze_map
  [
   [2 2 2 2 2 2 2 2]
   [2 0 0 0 0 0 0 2]
   [2 0 2 2 0 2 0 2]
   [2 0 0 0 0 0 0 2]
   [2 2 2 2 2 2 2 2]
   ]
  )

(defn get_id_object
  ; objects are Object_on_map
  [objects row column]
  (some->> objects
           (filter #(and (= (:row %) row) (= (:column %) column)))
           first
           :id)
  )

(defn render_maze_map [maze_map direction_of_sight objects]
  (let [direction_of_sight_upper (clojure.string/upper-case direction_of_sight)
        player_symbol (cond
                        (= "N" direction_of_sight_upper) "^"
                        (= "S" direction_of_sight_upper) "V"
                        (= "W" direction_of_sight_upper) "<"
                        (= "E" direction_of_sight_upper) ">"
                        )
        food_symbol "."
        monster_symbol "A"
        ]
    (loop [row 0]
      (when (< row (count maze_map))
        (let [row_content (get maze_map row)
              row_content_count (count row_content)
              last_column (dec row_content_count)
              ]
          (loop [column 0]
            (when (< column row_content_count)
              (let [object_here (get_id_object objects row column)
                    item (get row_content column)
                    item_to_render (if (nil? object_here)
                                     ; no object found on these coordinates
                                     (cond
                                       (= item 0) " "
                                       (= item 2) "#"
                                       )
                                     ; let's render object
                                     (cond
                                       (= object_here PACMAN) player_symbol
                                       (= object_here FOOD) food_symbol
                                       (= object_here MONSTER) monster_symbol
                                       )
                                     )
                    ]
                (if (= last_column column)
                  (println item_to_render)
                  (print item_to_render)
                  )
                (recur (inc column)))
              )
            )
          (recur (inc row)))
        )
      )
    )
  )

(defn get_maze_item
  [maze row column]
  (let [last_row_index (dec (count maze))
        ; assumption is the maze has at least one row
        last_column_index (dec (count (get maze 0)))]
    (if (and (>= row 0) (<= row last_row_index)
             (>= column 0) (<= column last_column_index)
             )
      (let [row_content (get maze row)]
        (int (get row_content column))
        )
      nil
      )
    )
  )

(defn is_wall?
  [maze row column]
  (let [v (get_maze_item maze row column)]
    (if (nil? v)
      true
      (= WALL v)
      )
    )
  )
(defn is_wall?_vec
  [maze row_col_vec]
  (is_wall? maze (int (first row_col_vec)) (int (second row_col_vec)))
  )

(defn apply_sight_modifiers
  [current_row current_column sight_modifiers index]
  (let [modif_vec (get sight_modifiers index)
        modif_pos [(+ current_row (first modif_vec)) (+ current_column (second modif_vec))]
        ]
    modif_pos
    )
  )

(defn get_scene_definition
  [maze current_row current_column direction_of_sight objects]
  (let [direction_of_sight_upper (clojure.string/upper-case direction_of_sight)
        ; c1 c2 c3 l1 l2 r1 r2
        sight_modifiers_definition {
                                    "N" [[-1 0]
                                         [-2 0]
                                         [-3 0]
                                         [-1 -1]
                                         [-2 -1]
                                         [-1 1]
                                         [-2 1]
                                         ]
                                    "W" [[0 -1]
                                         [0 -2]
                                         [0 -3]
                                         [1 -1]
                                         [1 -2]
                                         [-1 -1]
                                         [-1 -2]
                                         ]
                                    "E" [[0 1]
                                         [0 2]
                                         [0 3]
                                         [-1 1]
                                         [-1 2]
                                         [1 1]
                                         [1 2]
                                         ]
                                    "S" [[1 0]
                                         [2 0]
                                         [3 0]
                                         [1 1]
                                         [2 1]
                                         [1 -1]
                                         [2 -1]
                                         ]
                                    }
        sight_modifiers (get sight_modifiers_definition direction_of_sight_upper)
        m0 (apply_sight_modifiers current_row current_column sight_modifiers 0)
        m1 (apply_sight_modifiers current_row current_column sight_modifiers 1)
        m2 (apply_sight_modifiers current_row current_column sight_modifiers 2)
        m3 (apply_sight_modifiers current_row current_column sight_modifiers 3)
        m4 (apply_sight_modifiers current_row current_column sight_modifiers 4)
        m5 (apply_sight_modifiers current_row current_column sight_modifiers 5)
        m6 (apply_sight_modifiers current_row current_column sight_modifiers 6)
        ]
    (println "m0=" m0 "; m1=" m1)
    (Scene_definition.
      (is_wall?_vec maze m0)
      (is_wall?_vec maze m1)
      (is_wall?_vec maze m2)
      (is_wall?_vec maze m3)
      (is_wall?_vec maze m4)
      (is_wall?_vec maze m5)
      (is_wall?_vec maze m6)
      ; let's render objects that are directly ahead: c1 and c2 positions
      (get_id_object objects (int (first m0)) (int (second m0)))
      (get_id_object objects (int (first m1)) (int (second m1)))
      )
    )
  )

(defn get_new_sight_direction
  [direction_of_sight is_turn_left?]
  ; TODO may it be solved more elegant?
  (let [direction_of_sight_upper (clojure.string/upper-case direction_of_sight)
        turn_rules {"N" ["W" "E"]
                    "E" ["N" "S"]
                    "S" ["E" "W"]
                    "W" ["S" "N"]
                    }
        turn_rules_item (get turn_rules direction_of_sight_upper)
        ]
    (if (true? is_turn_left?)
      (first turn_rules_item)
      (second turn_rules_item)
      )
    )
  )

; ======= DEBUG =========

;(render_maze_map maze_map "N")
;(render_maze_map maze_map "S")
;(render_maze_map maze_map "W")
;(render_maze_map maze_map "E")

;(println (is_wall? maze_map 1 1))
;(println (is_wall? maze_map 3 1))
;(println (is_wall? maze_map 1 3))

;(println (get_scene_definition maze_map 1 1 "N"))


; =======================