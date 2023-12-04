(ns clojure-course.week11.unquote_splicing)

(def three-and-four (list 3 4))
(println `(1 ~three-and-four))
(println `(1 ~@three-and-four))

