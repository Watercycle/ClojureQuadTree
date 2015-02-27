(ns quadtree.tree)

(def max-obj 10)
(def max-lvl 6)

(defrecord vec2 [x y])
(defrecord vec3 [x y z])

;rectangle with 0,0 at the top-left corner
(defrecord rect [x y w h])

(defrecord quad [lvl rect objs nodes])

(defn split [rect]

  )

;is region 1 fully in the original region?
(in? [{:keys [x y w h]} {:keys [x1 x2 w1 h2]}]
     (and (> (- x1 w1) x)
          (< (+ x1 w1 (+ x w)))
          (> (- y1 h1) 0)
          (< (+ y1 h1) (+ y h))))

;which quad an object belongs to
;returns -1 if it does not fit
(defn get-index [parent-rect rect]
  (cond x > )
  )

(defn insert-all [quad obj]
  )

(defn retrieve [rect]
  )

(defn build-quadtree [objs {:keys [x y w h]}]
  (let [base-region (rect. x )
        base-objs (filter #(= (get-index %) -1) objs)
        nodes (quad) (insert-all base objs)
        base (quad. 0 (rect. x y w h) base-objs nodes)]))

(comment
  (defn center
    ([x y w h]
      {:x (+ x (/ w 2))
       :y (+ y (/ h 2))})
    ([{:keys [x y w h]}]
      (center x y w h)))

  (center (rect. 0 0 50 50))
  (center 0 0 50 50))