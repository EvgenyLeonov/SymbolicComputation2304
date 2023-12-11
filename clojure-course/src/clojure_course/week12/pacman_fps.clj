(ns clojure-course.week12.pacman_fps
  (:require [clojure-course.week11.scene_rendering :as scene_rendering])
  (:require [clojure-course.week12.maze_map :as maze_map_rendering])
  )

(def direction_of_sight (atom "S"))
(def coord_row 2)
(def coord_column 1)

(defn main_loop
  []
  (let [scene_instr (scene_rendering/prepare_instructions_for_scene scene_rendering/scene_c3)]
    ; hack to clear the REPL screen
    (print (str (char 27) "[2J"))
    (scene_rendering/render_scene scene_instr)
    (println)
    (maze_map_rendering/render_maze_map maze_map_rendering/maze_map direction_of_sight)
    )
  )

(main_loop)







