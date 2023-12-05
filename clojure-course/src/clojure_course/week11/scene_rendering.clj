(ns clojure-course.week11.scene_rendering)

; 13 x 6
(def scene_content
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

(render_scene scene_content)

