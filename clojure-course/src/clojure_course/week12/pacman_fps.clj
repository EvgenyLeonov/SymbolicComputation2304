(ns clojure-course.week12.pacman_fps
  (:require [clojure-course.week11.scene_rendering :as scene_rendering])
  (:require [clojure-course.week12.maze_map :as maze_map_rendering])
  )

(def maze_map maze_map_rendering/maze_map)

(def direction_of_sight (atom "S"))
(def pacman_current_row (atom 1))
(def pacman_current_column (atom 1))

(defn main_loop
  []
  (let [
        ;scene_instr (scene_rendering/prepare_instructions_for_scene scene_rendering/scene_c3)
        scene_def (maze_map_rendering/get_scene_definition maze_map @pacman_current_row @pacman_current_column @direction_of_sight)
        _ (println "scene_def =" scene_def)
        scene_instr (scene_rendering/prepare_instructions_for_scene scene_def)
        ]
    ; hack to clear the REPL screen
    ;(print (str (char 27) "[2J"))
    (scene_rendering/render_scene scene_instr)
    (println)
    (maze_map_rendering/render_maze_map maze_map @direction_of_sight)
    )
  )

(main_loop)







