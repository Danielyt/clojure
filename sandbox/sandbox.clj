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

(keys person)	; (:age :name :sex)
(vals person)	; (35 “John Smith" “male”)

(def numbers {:one 1 :two 2 :three 3})

(assoc numbers :four 4)		; {:one 1, :three 3, :two 2, :four 4}
(dissoc numbers :two)		; {:one 1, :three 3}

(merge numbers {:four 4 :five 5})	; {:one 1, :five 5, :three 3, :two 2, :four 4}
