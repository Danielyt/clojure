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

(use 'clojure.set)
(difference #{1 2 3 4} #{2 4 6 8})		; #{1 3}
(intersection #{1 2 3} #{2 3 4 5})		; #{2 3}
(subset? #{1 2} #{0 1 2 3})		; true
(union #{0 1 2 3} #{2 3 4 5})		; #{0 1 2 3 4 5}

