(ns quadtree.tree
  (:require [quil.core :as q]))

(def max-obj 2)
(def max-lvl 6)

(defrecord quad [lvl rect objs nodes])

(defn center
  ([x y w h]
   [(+ x (/ w 2)) (+ y (/ h 2))])
  ([[x y w h]] (center x y w h)))

(defn split [[x y w h]]
  (let [hw (/ w 2) hh (/ h 2)
        cx (+ x hw) cy (+ y hh)]
    {:I   [cx y hw hh]
     :II  [x y hw hh]
     :III [x cy hw hh]
     :IV  [cx cy hw hh]}))

;determine which quad the AABB belongs to
(defn get-index [region [x y w h]]
  (let [[cx cy] (center region)
        in-top? (<= (+ y h) cy)
        in-bot? (>= y cy)
        in-right? (>= x cx)
        in-left? (<= (+ x w) cx)]
    (cond
      in-top? (cond
                in-right? :I
                in-left? :II
                :else :NONE)
      in-bot? (cond
                in-left? :III
                in-right? :IV
                :else :NONE)
      :else :NONE)))

(defn group-zones [region coll] (group-by #(get-index region %) coll))

;(if ( < (count objs) max-obj))

(defn insert-all [region objs level]
;  (if (not-empty objs))
  (let [div (split region)
        lvl (inc level)]
    (map #(build-quadtree (% div) (% objs) lvl %) [:I :II :III :IV])))

(defn build-quadtree
  ([region objs lvl zone]
   (cond (> (count objs) max-obj)
         (let [indexed-objs (group-zones region objs)
               base-objs (flatten (:NONE indexed-objs))
               nodes (insert-all region (dissoc indexed-objs :NONE) lvl)]
           {zone (quad. lvl region base-objs nodes)})
         (> (count objs) 0) {zone (quad. lvl region objs nil)}
         :else nil))
  ([region objs] (build-quadtree region objs 0 :ROOT)))

(comment
  (let [[x y w h] region]
    ;     (q/line (+ x (/ w 2)) y (+ x (/ w 2)) (+ y h))
    ;     (q/line x (+ y (/ h 2)) (+ x w) (+ y (/ h 2)))
    ))

(defn retrieve [rect]
  )







;EXTRA

;rectangle with 0,0 at the top-left corner
(defrecord rect [x y w h])

(defrecord vec2 [x y])
(defrecord vec3 [x y z])

;is region 1 fully in the original region?
(comment
  (in? [{:keys [x y w h]} {:keys [x1 x2 w1 h2]}]
       (and (> (- x1 w1) x)
            (< (+ x1 w1 (+ x w)))
            (> (- y1 h1) 0)
            (< (+ y1 h1) (+ y h)))))