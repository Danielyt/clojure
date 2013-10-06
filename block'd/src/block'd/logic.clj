(ns block'd.logic)

(def board-height 15)
(def board-width 10)
(def wait-time 100)


(defn mapmatrix [func matrix]
  (into [] (map-indexed (fn[y vect]
                          (into [] (map-indexed (fn[x el]
                                                  (func el x y))
                                                vect)))
                        matrix)))

(defn new-board []
  (mapmatrix (fn [el x y]
               (+ 2 (rand-int 3)))
             (vec (repeat board-height (vec (repeat board-width nil))))))

(defn get-cell [matrix [x y]]
  (get-in matrix [y x] -1))

(defn get-colour [c]
  (cond
    (zero? c)	:black
    (= 1 c)		:white
    (= 2 c)		:green
    (= 3 c)		:yellow
    (= 4 c)		:red
    :else		:blue))

(defn get-neighbours
  "Finds the coordinates of the neighbours of a cell."
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1]
        :when (and (some zero? [dx dy])
                   (not= dx dy)
                   (<= 0 (+ dx x))
                   (<= 0 (+ dy y))
                   (> board-width (+ dx x))
                   (> board-height (+ dy y)))]
    [(+ dx x) (+ dy y)]))

(defn get-new-neighbours [cells]
  (set (for [cell cells
             neighbour (get-neighbours cell)
             :when (not (cells neighbour))]
         neighbour)))

;;vrashta set ot koordinatite na sysedite na kletkata sas sashtiya
;;tsvyat vklyuchtelno i samata kletka
(defn get-white [matrix cells]
  (let [colour (get-cell matrix (first cells))
        new-neighbours (filter #(= colour (get-cell matrix %1)) (get-new-neighbours cells))]
    (if (and (seq new-neighbours)
             (pos? colour))
      (recur matrix (set (concat cells new-neighbours)))
      cells)))

;; slaga 1 na myastoto na tezi kletki, koito tryabva da migat
(defn make-white [matrix cell]
  (let [whites (get-white matrix (set [cell]))]
    (if (> (count whites) 1)
      (mapmatrix (fn [el x y]
                   (if (whites [x y])
                     1
                     el))
                 matrix)
      matrix)))

(defn unmake-white [matrix colour]
  (mapmatrix (fn [el x y]
               (if (= 1 el)
                 colour
                 el))
             matrix))

;; slaga 0 na myastoto na 1
(defn make-empty [matrix]
  (mapmatrix (fn [el x y]
                   (if (= 1 el)
                     0
                     el))
                   matrix))

(defn move-down [matrix]
  (let [new-matrix (mapmatrix (fn [el x y]
                           (cond
                             (and (zero? el) (pos? y))
                             (get-cell matrix (conj [x] (dec y)))
                             (and (< y 14) (zero? (get-cell matrix (conj [x] (inc y)))))
                             0
                             :else el))
                         matrix)]
    (if-not (= matrix new-matrix)
      (recur new-matrix)
      new-matrix)))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn remove-empty-columns [matrix]
  (let [new-matrix (remove #(every? zero? %1) (transpose matrix))
        removed (- board-width (count new-matrix))
        before (quot removed 2)]
    (transpose (into (into (vec (repeat before (vec (repeat board-height 0))))
                           new-matrix)
                     (vec (repeat (- removed before) (vec (repeat board-height 0))))))))

(defn check-columns [matrix]
  (for [x (range (dec board-width))
        y (range board-height)
        :let [this-colour (get-cell matrix [x y])
              next-colour (get-cell matrix [(inc x) y])]
        :when (and (= this-colour next-colour)
                   (not= 0 this-colour))]
    [x y]))

(defn check-rows [matrix]
  (for [x (range board-width)
        y (range (dec board-height))
        :let [this-colour (get-cell matrix [x y])
              next-colour (get-cell matrix [x (inc y)])]
        :when (and (= this-colour next-colour)
                   (not= 0 this-colour))]
    [x y]))

(defn superpose [matrix1 matrix2]
  (mapmatrix (fn [el x y]
               (if (zero? el)
                 (get-cell matrix2 [x y])
                 el))
             matrix1))

(defn count-whites [matrix]
  (count (for [x (range board-width)
               y (range board-height)
               :let [colour (get-cell matrix [x y])]
               :when (= 1 colour)]
           1)))

(defn all-empty? [matrix]
  (every? zero? (flatten matrix)))