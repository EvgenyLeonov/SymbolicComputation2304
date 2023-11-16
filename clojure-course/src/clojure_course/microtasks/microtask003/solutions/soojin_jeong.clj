(ns clojure-course.microtasks.microtask003.solutions.soojin-jeong)

;- Task -
; One day, Mr. Furball became interested in how many grandfathers and grandmothers he had.
;Mr. Furball has a mother and father, and each of them has a mother and father as well.
;And so on, and so on.
;Let's say a baby is born in a typical cat family when both parents are three years old.
;Please use recursion and calculate how many cat grandfathers and grandmothers Mr. Furball had for 100 years.

;- Hint -
;When a new generation gets three years old, they give birth to a new cat.
;You may raise recursion generation after generation with step equal 3 until reach 100.
;Also, you may refer to file week5\fibonacci.clj.

(defn count-grandparents [age]
  (if (>= age 100)
    0
    (+ (count-grandparents (+ age 3)) 2)))

(println (count-grandparents 0))




