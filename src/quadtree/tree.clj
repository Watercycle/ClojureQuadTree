(ns quadtree.tree
  (:require [quil.core :as q]))

(def max-obj 5)
(def max-lvl 6)

(declare build-quadtree)
(defrecord quad [lvl rect objs nodes])

(defn- center
  "returns the center of a rectangle as [x y]."
  [[x y w h]]
  [(+ x (/ w 2)) (+ y (/ h 2))])

(defn- split
  "returns equal sub-rectangles of the parent rectangle as
   a hash map of their respective quadrants."
  [[x y w h]]
  (let [hw (/ w 2) hh (/ h 2)
        cx (+ x hw) cy (+ y hh)]
    {:I   [cx y hw hh]
     :II  [x y hw hh]
     :III [x cy hw hh]
     :IV  [cx cy hw hh]}))

(defn- get-index
  "returns which quadrant a rectangle would fall into if the
   rectangular region provided was equally split up (or :NONE
   if the object is sitting on top of a quadrant line)."
  [region [x y w h]]
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

(defn- draw-out-rect
  "draws an outlined rectangle with the Quil graphics API."
  [[x y w h]]
  (let [bt (+ y h)
        rt (+ x w)]
    (q/line x y x bt)
    (q/line x bt rt bt)
    (q/line rt bt rt y)
    (q/line rt y x y)))

(defn draw-quadtree
  "draws all active subnodes in the provided quadtree using the Quil graphics API."
  [tree]
  (do
    (dorun (map #(if-not (nil? %) (draw-quadtree %)) (:nodes tree)))
    (draw-out-rect (:rect tree))))

(defn- insert-all
  "recursively constructs the quadtree by deciding what quadrant a
  set of objects belongs to."
  [region objs level]
  (let [div (split region)
        lvl (inc level)]
    (do
      (println "running on thread " (Thread/currentThread))
      (if (= level 1)
        (pmap #(build-quadtree (% div) (% objs) lvl) [:I :II :III :IV])
        (map #(build-quadtree (% div) (% objs) lvl) [:I :II :III :IV])))))

(defn build-quadtree
  "returns a fully constructed quadtree based on a given region and set of objects.
  objects that do not clearly fit in a quadrant will remain on their current level
  and while others are passed down to another quadtree node."
  ([region objs lvl]
   (cond (and (> (count objs) max-obj) (< lvl max-lvl))
         (let [indexed-objs (group-by #(get-index region %) objs)
               base-objs (:NONE indexed-objs)
               nodes (insert-all region (dissoc indexed-objs :NONE) lvl)]
           (quad. lvl region base-objs nodes))
         (> (count objs) 0) (quad. lvl region objs nil)
         :is-empty nil))
  ([region objs] (build-quadtree region objs 0)))

(defn retrieve
  "returns a vector of all rectangular spaces that may
  make contact with the provided rectangle."
  [tree [x y w h]]
  )
