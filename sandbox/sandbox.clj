(def fib (fn [n]
	(if (= n 0)
		1
		(if (= n 1)
			1
			(+ (fib (- n 1)) (fib (- n 2)))))))

(fib 0)  ; 1
(fib 11) ; 144
