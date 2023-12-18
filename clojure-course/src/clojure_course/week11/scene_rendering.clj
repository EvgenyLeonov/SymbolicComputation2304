(ns clojure-course.week11.scene_rendering)

; columns 0-13
; rows 0-5
(def SCENE_WIDTH 14)
(def SCENE_HEIGHT 6)
(def MONSTER 3)
(def FOOD 4)

(defn render_scene
  [content]
  (loop [row 0]
    (when (< row (count content))
      (let [row_content (get content row)
            ]
        (println (apply str row_content))
        (recur (inc row)))
      )
    )
  )


; all are boolean except o1, o2 -- they are ids of objects
(defrecord Scene_definition [c1 c2 c3 l1 l2 r1 r2 o1 o2])

(def scene_c3 (Scene_definition. false false true true true true true nil MONSTER))
(def scene_c2 (Scene_definition. false true false true true true true FOOD nil))
(def scene_c1 (Scene_definition. true false true true true true true nil nil))

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

(defn lr_empty []
  [
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))]
  )

(defn c3_empty []
  [
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   ]
  )

(defn c2_empty []
  [
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   ]
  )

(defn c1_empty []
  [
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   (into [] (repeat SCENE_HEIGHT " "))
   ]
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

; FOOD, 2 level
(defn f1 []
  [[" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " "^" "_" " "]
   [" " " " " " "^" "_" " "]
   [" " " " " " "^" "_" " "]
   [" " " " " " "^" "_" " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]])

(defn f2 []
  [
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " "^" "_" " "]
   [" " " " " " "^" "_" " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   ]
  )

(defn m1 []
  [[" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " "|" " "]
   [" " " " " " "_" "_" " "]
   [" " " " " " "_" "_" " "]
   [" " " " " " " " "|" " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]])

(defn m2 []
  [
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   [" " " " " " "_" "|" " "]
   [" " " " " " "_" "|" " "]
   [" " " " " " " " " " " "]
   [" " " " " " " " " " " "]
   ]
  )


(defn get_func_object
  [id level1?]
  (cond
    (and (= id FOOD) (true? level1?)) f1
    (and (= id FOOD) (false? level1?)) f2
    (and (= id MONSTER) (true? level1?)) m1
    (and (= id MONSTER) (false? level1?)) m2
    )
  )

(defn prepare_rendering_instructions
  [scene_definition]
  (let [c3_set (:c3 scene_definition)
        c2_set (:c2 scene_definition)
        c1_set (:c1 scene_definition)
        rule_key (cond
                   (true? c1_set) "C1"
                   (true? c2_set) "C2"
                   (true? c3_set) "C3"
                   :else "C3"
                   )
        rendering_rules {"C1" [c1]
                         "C2" [l1 c2 r1]
                         "C3" [l1 l2 c3 r2 r1]
                         }
        rule (atom (get rendering_rules rule_key))
        ;_ (println "rules=" rules)
        o1_id (:o1 scene_definition)
        o2_id (:o2 scene_definition)
        o1? (not (nil? o1_id))
        o2? (not (nil? o2_id))
        ]
    ; apply standing objects
    (when (and (= rule_key "C2") o1?)
      ; replace c2 with o1 TODO merge them here (o1 + c2)
      (reset! rule [l1 (get_func_object o1_id true) r1])
      )

    (when (and (= rule_key "C3"))
      ; replace c3 with o1 or o2 TODO merge them here (o1 + o2 + c3)
      (when o1?
        (reset! rule [l1 l2 (get_func_object o1_id true) r2 r1])
        )
      (when o2?
        (reset! rule [l1 l2 (get_func_object o2_id false) r2 r1])
        )
      )
    @rule
    )
  )

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

(defn prepare_instructions_for_scene
  [scene_definition]
  (let [rule (prepare_rendering_instructions scene_definition)
        ;_ (println rule)
        scene (atom [])]
    (loop [index 0]
      (when (< index (count rule))
        (let [rule (get rule index)
              rules_mapping
              {
               l1 :l1
               l2 :l2
               r1 :r1
               r2 :r2
               c1 :c1
               c2 :c2
               c3 :c3
               }
              key (get rules_mapping rule)
              ; f1, f2, m1, m2 we render without conditions
              exists (cond
                       (or (= rule f1)(= rule f2)(= rule m1)(= rule m2)) true
                       :else (true? (get scene_definition key))
                       )
              ;_ (println "key =" key "exists =" exists)
              ]
          (if (true? exists)
            (swap! scene concat (rule))
            (cond
              (= key :c3) (swap! scene concat (c3_empty))
              (= key :c2) (swap! scene concat (c2_empty))
              (= key :c1) (swap! scene concat (c1_empty))
              :else (swap! scene concat (lr_empty))
              )
            )
          )
        (recur (inc index))
        )
      )
    ;(println "scene =" @scene)
    ; rotate this matrix by 90 degrees clockwise
    (let [scene_rotated (atom [])]
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

(def scene_instr (prepare_instructions_for_scene scene_c3))

(render_scene scene_instr)




