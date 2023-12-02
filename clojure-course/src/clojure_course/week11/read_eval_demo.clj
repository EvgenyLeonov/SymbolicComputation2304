(ns clojure-course.week11.read_eval_demo)

(println (read-string "(def x 100)"))
(eval (read-string "(def x 100)") )
(println "x=" x)
(def y (list '+ 100 200 300))
(println "y=" y)
(println "y=" (eval (list '+ 100 200 300)))

