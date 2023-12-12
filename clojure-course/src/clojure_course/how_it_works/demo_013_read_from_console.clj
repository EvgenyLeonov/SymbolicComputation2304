(ns clojure-course.how-it-works.demo_013_read_from_console)

(do (print "Input any string:")
    (flush)
    (def your_input (read-line)))

(println "You printed:" your_input)




