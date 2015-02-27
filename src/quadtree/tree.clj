(ns quadtree.tree)

(def max-obj 10)
(def max-lvl 6)

;rectangle with 0,0 at the top-left corner
(defrecord rect [x y w h])

(defrecord quad [lvl rect objs nodes])

(defn split [rect]
  )

;which quad an object belongs to
;returns -1 if it does not fit
(defn get-index [rect]
  )

(defn insert-all [quad obj]
  )

(defn retrieve [rect]
  )

(defn build-quadtree [objs {:keys [x y w h]}]
  (let [base-objs (filter #(= (get-index %) -1) objs)
        nodes (quad) (insert-all base objs)
        base (quad. 0 (rect. x y w h) base-objs nodes)]))
