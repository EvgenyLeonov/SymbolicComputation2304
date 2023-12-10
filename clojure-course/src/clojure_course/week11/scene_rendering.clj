(ns clojure-course.week11.scene_rendering)

; columns 0-13
; rows 0-5
(def SCENE_WIDTH 14)
(def SCENE_HEIGHT 6)

; 13 x 6
(def scene_content_demo
  [["|" "\\" " " " " " " " " " " " " " " " " " " " " "/" "|"]
   ["|" " " "|" "\\" " " "_" "_" "_" "_" " " "/" "|" " " "|"]
   ["|" " " "|" " " "|", " " " " " " " ", "|" "" " " "|" " " "|"]
   ["|" " " "|" " " "|", "_" "_" "_" "_", "|" "" " " "|" " " "|"]
   ["|" " " "|" "/" " " " " " " " " " " " " "\\" "|" " " "|"]
   ["|" "/" " " " " " " " " " " " " " " " " " " " " "\\" "|"]
   ]

  )

(defn render_scene
  [content]
  ; hack to clear the REPL screen
  (print (str (char 27) "[2J"))
  (loop [row 0]
    (when (< row (count content))
      (let [row_content (get content row)
            ]
        (println (apply str row_content))
        (recur (inc row)))
      )
    )

  )

;(render_scene scene_content_demo)

; all are boolean
(defrecord Scene_definition [c1 c2 c3 l1 l2 r1 r2])

(def scene_c3 (Scene_definition. false false true true true true true))
(def scene_c2 (Scene_definition. false true false true true true true))
(def scene_c1 (Scene_definition. true false false true true true true))

(defn c1 []
  [
   (into [] (repeat SCENE_HEIGHT "|"))
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   ["_" " " " " " " " " "_"]
   (into [] (repeat SCENE_HEIGHT "|"))]
  )
(defn c2 []
  [[" " "|" "|" "|" "|" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   ["_" " " " " " " "_" " "]
   [" " "|" "|" "|" "|" " "]])
(defn c3 []
  [[" " " " "|" "|" " " " "]
   [" " "_" " " "_" " " " "]
   [" " "_" " " "_" " " " "]
   [" " "_" " " "_" " " " "]
   [" " "_" " " "_" " " " "]
   [" " " " "|" "|" " " " "]]
  )
(defn l1 []
  [
   (into [] (repeat SCENE_HEIGHT "|"))
   ["\\" "," "," "," "," "/"]]
  )
(defn l2 []
  [
   [" " "|" "|" "|" "|" " "]
   [" " "\\" "," "," "/" " "]
   ]
  )
(defn r1 []
  [
   ["/" "," "," "," "," "\\"]
   (into [] (repeat SCENE_HEIGHT "|"))
   ]
  )
(defn r2 []
  [
   [" " "/" "," "," "\\" " "]
   [" " "|" "|" "|" "|" " "]
   ]
  )

(def rendering_rules
  ; render from left to right
  {"C1" [c1]
   "C2" [l1 c2 r1]
   "C3" [l1 l2 c3 r2 r1]
   ;"C3" [l1 l2]
   }
  )

(defn prepare_rendering_instructions
  [scene_definition]
  (let [c3_set (:c3 scene_definition)
        c2_set (:c2 scene_definition)
        c1_set (:c1 scene_definition)
        rule_key (cond
                   (true? c3_set) "C3"
                   (true? c2_set) "C2"
                   (true? c1_set) "C1"
                   )
        rule (get rendering_rules rule_key)
        ;_ (println "rules=" rules)
        ]
    rule
    )
  )

;(prepare_rendering_instructions scene1)
;(prepare_rendering_instructions scene2)

(defn concat_matrix_column
  [matrix column_index]
  (let [current (atom [])]
    (loop [row_index 0]
      (when (< row_index SCENE_WIDTH)
        (let [scene_item (get (into [] matrix) row_index)
              ;_ (println "scene_item =" scene_item)
              item (str (get scene_item column_index))
              ;_ (println "item =" item)
              ]
          (swap! current concat item)
          ;(println "current ="  @current)
          )
        (recur (inc row_index))
        )
      )
    @current
    )
  )

(defn render_instructions_to_scene
  [rule scene_definition]
  (let [scene (atom [])]
    (loop [index 0]
      (when (< index (count rule))
        (let [rule (get rule index)]
          (swap! scene concat (rule))
          )
        (recur (inc index))
        )
      )
    ;(println "scene =" @scene)
    ; rotate this matrix by 90 degrees clockwise
    (let [scene_rotated (atom [])
          ]
      (loop [column_index 0]
        (when (< column_index SCENE_HEIGHT)
          (let [column_concatenated (concat_matrix_column @scene column_index)]
            (swap! scene_rotated conj column_concatenated)
            )
          (recur (inc column_index))
          )
        )
      @scene_rotated
      )
    )
  )


(def scene_rule (prepare_rendering_instructions scene_c1))
(println "scene_rule =" scene_rule)
(def scene_instr (render_instructions_to_scene scene_rule nil))
(println "scene_instr =" scene_instr)

(render_scene scene_instr)

;(println (l1))


