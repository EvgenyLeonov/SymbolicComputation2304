(ns clojure-course.week12.maze_map)

; rows = 0-5; columns = 0-7
; 0 - empty
; 1 - player (current positions)
; 2 - wall
; 3 - monster
; 4 - food (Pacman eats this)

(def WALL 2)

(def maze_map
  [
   [2 2 2 2 2 2 2 2]
   [2 1 2 0 2 0 2 2]
   [2 0 0 0 0 0 0 2]
   [2 2 0 2 0 0 2 2]
   [2 0 0 0 2 0 2 2]
   [2 2 2 2 2 2 2 2]
   ]
  )

(defn render_maze_map [maze_map direction_of_sight]
  (loop [row 0]
    (when (< row (count maze_map))
      (let [row_content (get maze_map row)
            row_content_count (count row_content)
            last_column (dec row_content_count)
            ]
        (loop [column 0]
          (when (< column row_content_count)
            (let [item (get row_content column)
                  direction_of_sight_upper (clojure.string/upper-case direction_of_sight)
                  player_symbol (cond
                                  (= "N" direction_of_sight_upper) "^"
                                  (= "S" direction_of_sight_upper) "V"
                                  (= "W" direction_of_sight_upper) "<"
                                  (= "E" direction_of_sight_upper) ">"
                                  )
                  item_to_render (cond
                                   (= item 0) " "
                                   (= item 1) player_symbol
                                   (= item 2) "#"
                                   (= item 3) "A"
                                   (= item 4) "F"
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

(defn get_maze_item
  [maze row column]
  (let [last_row_index (dec (count maze))
        ; assumption is the maze has at least one row
        last_column_index (dec (count (get maze 0)))]
    (if (and (>= row) (<= row last_row_index)
             (>= column) (<= column last_column_index)
             )
      (let [row_content (get maze row)]
        (int (get row_content column))
        )
      nil
      )
    )
  )

(defn can_move?
  [maze current_row current_column direction_of_sight]
  (let [
        direction_of_sight_upper (clojure.string/upper-case direction_of_sight)
        modificator_row (cond
                          (= "N" direction_of_sight_upper) -1
                          (= "S" direction_of_sight_upper) 1
                          )
        modificator_column (cond
                             (= "W" direction_of_sight_upper) -1
                             (= "E" direction_of_sight_upper) 1
                             )
        new_row (+ current_row modificator_row)
        new_column (+ current_column modificator_column)
        last_row_index (dec (count maze))
        ;:when (>= last_row_index 0)
        last_column_index (dec (count (get maze 0)))
        result (not= WALL (get_maze_item maze new_row new_column))
        ]
    result
    )
  )

(defrecord Scene_definition [c1 c2 c3 l1 l2 r1 r2])

(defn is_wall?
  [maze row column]
  (let [v (get_maze_item maze row column)]
    ;(println "v =" v)
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
        ;_ (println "modif_vec =" modif_vec)
        ;_ (println "sight_modifiers =" sight_modifiers)
        ;_ (println "index =" index)
        modif_pos [(+ current_row (first modif_vec)) (+ current_column (second modif_vec))]
        ]
    modif_pos
    )
  )

(defn get_scene_definition
  [maze current_row current_column direction_of_sight]
  (let [direction_of_sight_upper (clojure.string/upper-case direction_of_sight)
        ;_ (println "direction_of_sight_upper =" direction_of_sight_upper)
        ; c1 c2 c3 l1 l2 r1 r2
        sight_modifiers_definition {
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
        ]
    (Scene_definition.
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 0))
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 1))
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 2))
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 3))
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 4))
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 5))
      (is_wall?_vec maze (apply_sight_modifiers current_row current_column sight_modifiers 6))
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

;(println (get_scene_definition maze_map 1 1 "s"))


; =======================