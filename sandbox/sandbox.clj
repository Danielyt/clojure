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

(def person
  {:name "John Smith"
  :sex "male"
  :age 35})

(person :name)	; “John Smith“
(:name person)	; “John Smith“

(keys person)	; (:age :name :sex)
(vals person)	; (35 “John Smith" “male”)
