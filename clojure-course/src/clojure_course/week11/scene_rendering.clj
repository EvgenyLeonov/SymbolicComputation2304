(ns clojure-course.week11.scene_rendering)

(def SCENE_WIDTH 15)
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

(render_scene scene_content_demo)

; all are boolean
(defrecord Scene_definition [c1 c2 c3 l1 l2 r1 r2])

(def scene1 (Scene_definition. false false true true true true true))
(def scene2 (Scene_definition. false true false true true true true))

(defn c1 [])
(defn c2 [])
(defn c3 []
  (into []
        (concat
          [" " " " "|" "|" " " " "]
          [" " "_" " " " " "_" " "]
          [" " "_" " " " " "_" " "]
          [" " "_" " " " " "_" " "]
          [" " "_" " " " " "_" " "]
          [" " " " "|" "|" " " " "]
          )
        )
  )
(defn l1 []
  (into []
    (concat
      (into [] (repeat SCENE_HEIGHT "|"))
      ["\\" " " " " " " " " "/"]
      )
    )
  )
(defn l2 []
  (into []
        (concat
          [" " "|" "|" "|" "|" " "]
          [" " "/" " " " " "\\" " "]
          )
        )
  )
(defn r1 [])
(defn r2 []


  )

(def rendering_rules
  ; render from left to right
  {"C1" [c1]
   "C2" [l1 c2 r1]
   "C3" [l1 l2 c3 r2 r1]
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

(defn render_instructions_to_scene
  [rule scene_definition]
  (let [scene (atom [])]
    (loop [index 0]
      (when (< index (count rule))
        (let [rule (get rule index)]
          (swap! scene conj (rule))
          )
        (recur (inc index))
        )
      )

    )

  )

