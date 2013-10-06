(ns block'd.state
	(use block'd.logic)
	(use clojure.pprint))

(def duration 120)

(def ^:private board (ref (new-board)))
(def ^:private current-cell (ref [-1 -1]))
(def ^:private current-cell-colour (ref -1))
(def ^:private score (ref 0))
(def ^:private level-score (ref 0))
(def ^:private time-left (ref duration))
(def ^:private level (ref 1))
(def ^:private goal (ref 3000))

(defn reset-values []
  (dosync
    (ref-set board (new-board))
    (ref-set current-cell [-1 -1])
    (ref-set current-cell-colour -1)
    (ref-set score 0)
    (ref-set level-score 0)
    (ref-set time-left duration)
    (ref-set level 1)
    (ref-set goal 3000)))


(defn get-cell-colour [cell]
  (get-colour (get-cell @board cell)))

(defn square-of-whites []
  (* (count-whites @board)
     (count-whites @board)))

(defn get-score []
  @score)

(defn get-last []
  @current-cell-colour)

(defn get-formated-time []
  (let [seconds @time-left]
    (format "%02d:%02d" (quot seconds 60) (rem seconds 60))))

(defn get-time []
  @time-left)

(defn get-goal []
  (- @goal @level-score))

(defn get-level []
  @level)

(defn countdown []
  (dosync (alter time-left dec)))

(defn next-level []
  (dosync ;(ensure time-left)
          ;(ensure goal)
          (alter level inc)
          (ref-set time-left duration)
          (ref-set level-score 0)
          (ref-set goal (+ 3000 (* 2000 (dec @level))))))

(defn hover-over-cell [cell]
  (if-not (= (get-cell @board cell) (get-cell @board @current-cell))
    (dosync
      (let [now-board @board
            now-cell @current-cell
            now-cell-colour @current-cell-colour]
        (alter board #(-> %1 (unmake-white now-cell-colour) (make-white cell)))
        (ref-set current-cell cell)
        (ref-set current-cell-colour (get-cell now-board cell))))))

;;removes white cells and updates score
(defn remove-cells []
  (dosync
    (let [wh (square-of-whites)
          new-matrix (make-empty @board)]
      (if (all-empty? new-matrix)
        (do
          (alter score + wh 1000)
          (alter level-score + wh 1000))
        (do
          (alter score + wh)
          (alter level-score + wh))) 
      (ref-set board new-matrix)))
  (Thread/sleep wait-time)
  (dosync (alter board #(move-down %1))
          (ref-set current-cell [-1 -1]))
  (Thread/sleep wait-time)
  (dosync ;(ensure current-cell)
    (alter board #(remove-empty-columns %1))
    (ref-set current-cell [-1 -1])))

(defn possible-move? []
  (if (and (zero? (count (check-columns @board)))
           (zero? (count (check-rows @board))))
    (dosync (alter board #(superpose %1 (new-board))))))

 ; (defn remove-cells []
 ;  (dosync
 ;    (let [now-board (ensure board)
 ;          now-color (ensure current-cell-colour)]
 ;      (alter board #(move-down (make-empty %1)))
 ;      (ref-set current-cell-colour -1))))