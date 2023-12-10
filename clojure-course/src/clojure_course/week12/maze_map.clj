(ns clojure-course.week12.maze_map)

; rows = 0-5; columns = 0-7
; 0 - empty
; 1 - player (current positions)
; 2 - wall
; 3 - monster
; 4 - food (Pacman eats this)
(def maze_map
  [
   [2 2 2 2 2 2 2 2]
   [2 0 2 0 2 0 2 2]
   [2 1 0 0 0 0 0 2]
   [2 2 0 2 0 0 2 2]
   [2 0 0 0 2 0 2 2]
   [2 2 2 2 2 2 2 2]
   ]
  )

(defn render_maze_map [maze_map]
  (loop [row 0]
    (when (< row (count maze_map))
      (let [row_content (get maze_map row)
            row_content_count (count row_content)
            last_column (dec row_content_count)
            ]
        (loop [column 0]
          (when (< column row_content_count)
            (let [item (get row_content column)
                  item_to_render (cond
                                   (= item 0) "  "
                                   ; TODO dynamically calculate symbol that depends on sight direction
                                   (= item 1) "X "
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

(render_maze_map maze_map)


