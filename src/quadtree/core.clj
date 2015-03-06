(ns quadtree.core
  (:require [quil.core :as q]
            [quadtree.tree :as qt]))


(def screen-w 720)
(def screen-h 480)
(def num-obj 500)

(defn rand-dot []
  [(rand-int 400) (rand-int 400) 5 5])

(def rand-dots
  (vec (repeatedly num-obj rand-dot)))

(def objs (atom rand-dots))

(defn setup []
  (q/smooth)                                                ;antialiasing
  (q/frame-rate 60)

  (q/background 0)
  (q/stroke-weight 1.5)
  (q/stroke 0 0 125)

  (q/text-align :right)
  (q/fill 255)
  (q/text-size 25)

;  (dr [50])

  (comment)
  (let [quad (qt/build-quadtree [0 0 400 400] rand-dots)]
    (qt/draw-quadtree quad))

  ;(qt/build-quadtree [0 0 300 300] rand-dots)
  )

;(do (map #(if (= 1 %) (println "works")) '(1 2 1 3 1)) nil)

;(qt/draw-helper (qt/build-quadtree [0 0 300 300] rand-dots))
;(qt/draw-quadtree (qt/build-quadtree [0 0 300 300] rand-dots))
;(map qt/draw-helper (:nodes (qt/build-quadtree [0 0 300 300] rand-dots)))
;(qt/draw-helper (qt/build-quadtree [0 0 300 300] rand-dots))

(comment
  (map #(if-not (= 1 %) (println "good!")) '(1 2 3 4 5 6))
  (:nodes (qt/build-quadtree [0 0 300 300] rand-dots))
  rand-dots
  (map #(if-not (nil? %)
         (println "made it")
         (println "didfn't make it"))
       (:nodes (qt/build-quadtree [0 0 300 300] rand-dots))))
;(-> (qt/build-quadtree [0 0 300 300] rand-dots) :nodes (nth 0) :rect (nth 0))

(comment
  rand-dots
  (binding [clojure.pprint/*print-right-margin* 200]
    (clojure.pprint/pprint (qt/build-quadtree [0 0 300 300] rand-dots))))


;(qt/build-quadtree [235 115 250 250] rand-dots)

(defn draw []
  (q/text (str "max depth: " 5) 600 22)
  ;(q/text (str "active nodes: " qt/num-nodes) 200 46)
  (q/text (str "total objects: " num-obj) 600 70)
  (q/text (str "obj 0 x pos: " 0) 600 94))

(q/defsketch example
             :title "Quadtree"
             :setup setup
             :draw draw
             :size [screen-w screen-h])
