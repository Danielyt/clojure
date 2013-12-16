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

(def primes #{13 11 7 5 3 2})
(primes 11)		; 11
(primes 4)		; nil
(contains? primes 7)	; true
(contains? primes 4)	; false
