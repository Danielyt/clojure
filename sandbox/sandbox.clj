(def l '(:lettuce :tomato :cheese))
(def v [:lettuce :tomato :cheese])

(conj l :salami)	; (:salami :lettuce :tomato :cheese)
(conj v :salami)	; [:lettuce :tomato :cheese :salami]

(peek l)		; :lettuce
(peek v)		; :cheese

(pop l)		; (:tomato :cheese)
(pop v)		; [:lettuce :tomato]

(defn fib [n]
  (let [fib-seq (lazy-cat [1 1] (map + (rest fib-seq) fib-seq))]
    (nth fib-seq n)))

(def numbers [1 2 3 4])
(def squares
  (for [n numbers] (* n n)))

squares ; (1 4 9 16)

(def pythagorean
  (for   [x (range 1 15)
          y (range 1 15)
          z (range 1 15)
          :when (and (< x y z)
                     (= (+ (* x x) (* y y)) (* z z)))] [x y z]))
pythagorean	; ([3 4 5] [5 12 13] [6 8 10])

