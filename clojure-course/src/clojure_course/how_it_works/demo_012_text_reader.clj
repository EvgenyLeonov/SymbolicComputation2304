(ns clojure-course.how-it-works.demo-012-text-reader)

(defn Example []
  (with-open [rdr (clojure.java.io/reader "src/clojure_course/how_it_works/test.csv")]
    (reduce conj [] (line-seq rdr))))

(for [line (Example)]
  (println line)
  )




