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

; l1 l2 c1 c2 c3 r1 r2
(def sight_modifiers {
                      "S" [
                           [1 0]
                           [1 1]
                           [0 1]
                           [0 2]
                           [0 3]
                           [-1 1]
                           ]
                      })

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
                                   (= item 0) "  "
                                   (= item 1) (str player_symbol " ")
                                   (= item 2) "[]"
                                   (= item 3) "A "
                                   (= item 4) "F "
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

(render_maze_map maze_map "N")
(render_maze_map maze_map "S")
(render_maze_map maze_map "W")
(render_maze_map maze_map "E")

(defn get_maze_item
  [row column]
  ; TODO
  )

(defn can_move?
  [current_row current_column direction_of_sight maze]
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
        result (not= WALL (get_maze_item new_row new_column))
        ]
    result
    )
  )

