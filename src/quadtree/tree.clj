(ns quadtree.tree
  (:require [quil.core :as q]))

(def max-obj 5)
(def max-lvl 6)

(declare build-quadtree)
(defrecord quad [lvl rect objs nodes])

(defn- center [[x y w h]]
  [(+ x (/ w 2)) (+ y (/ h 2))])

(defn- split [[x y w h]]
  (let [hw (/ w 2) hh (/ h 2)
        cx (+ x hw) cy (+ y hh)]
    {:I   [cx y hw hh]
     :II  [x y hw hh]
     :III [x cy hw hh]
     :IV  [cx cy hw hh]}))

;determine which quad the AABB belongs to
(defn- get-index [region [x y w h]]
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

(defn- group-zones [region coll] (group-by #(get-index region %) coll))

(defn- draw-out-rect [[x y w h]]
  (let [bt  (+ y h)
        rt (+ x w)]
    (q/line x y x bt)
    (q/line x bt rt bt)
    (q/line rt bt rt y)
    (q/line rt y x y)))

(defn draw-quadtree [tree]
  (do
    (dorun (map #(if-not (nil? %) (draw-quadtree %)) (:nodes tree)))
    (draw-out-rect (:rect tree))))

(defn- insert-all [region objs level]
  (let [div (split region)
        lvl (inc level)]
    (map #(build-quadtree (% div) (% objs) lvl) [:I :II :III :IV])))

(defn build-quadtree
  ([region objs lvl]
   (do
     (cond (and (> (count objs) max-obj) (< lvl max-lvl))
           (let [indexed-objs (group-zones region objs)
                 base-objs (flatten (:NONE indexed-objs))
                 nodes (insert-all region (dissoc indexed-objs :NONE) lvl)]
             (quad. lvl region base-objs nodes))
           (> (count objs) 0) (quad. lvl region objs nil)
           :is-empty nil)))
  ([region objs] (build-quadtree region objs 0)))

(defn retrieve [rect]
  )

;EXTRA
(comment

;rectangle with 0,0 at the top-left corner
(defrecord rect [x y w h])

(defrecord vec2 [x y])
(defrecord vec3 [x y z])

;is region 1 fully in the original region?
  (in? [{:keys [x y w h]} {:keys [x1 x2 w1 h2]}]
       (and (> (- x1 w1) x)
            (< (+ x1 w1 (+ x w)))
            (> (- y1 h1) 0)
            (< (+ y1 h1) (+ y h))))
)
