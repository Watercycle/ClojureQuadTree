(ns quadtree.core-test
  (:use [clojure.test] :reload)
  (:import [quadtree.tree rect])
  (:require [quadtree.core :refer :all]
            [quadtree.tree :as qt]))

(def test-objs [[60 60 5 5]
                [70 70 5 5]
                [20 20 5 5]
                [130 175 5 5]
                [176 130 5 5]
                [173 400 5 5]])

(def test-region [50 50 250 250])
;(def base-tree (qt/build-quadtree test-region test-objs))


(binding [clojure.pprint/*print-right-margin* 200]
  (clojure.pprint/pprint (qt/build-quadtree test-region test-objs)))

(deftest quad-get-index
  (are [a b] (= a b)
             (qt/get-index [50 50 250 250] [176 154 20 20]) :I
             (qt/get-index [50 50 250 250] [154 154 20 20]) :II
             (qt/get-index [50 50 250 250] [154 176 20 20]) :III
             (qt/get-index [50 50 250 250] [176 176 20 20]) :IV
             (qt/get-index [50 50 250 250] [400 400 20 20]) :IV
             (qt/get-index [50 50 250 250] [400 175 20 20]) :IV
             (qt/get-index [50 50 250 250] [175 400 5 5]) :IV
             (qt/get-index [50 50 250 250] [175 175 5 5]) :IV
             (qt/get-index [50 50 250 250] [100 175 5 5]) :III
             (qt/get-index [50 50 250 250] [170 170 10 10]) :NONE
             (qt/get-index [50 50 250 250] [150 170 10 10]) :NONE
             (qt/get-index [50 50 250 250] [173 400 10 10]) :NONE
             ))

(deftest quad-center
  (are [a b] (= a b)
             (qt/center [50 50 250 250]) [175 175]
             (qt/center [0 0 25 25]) [25/2 25/2]))

(deftest quad-build
  (let [base-objs [[60 60 5 5] [70 70 5 5]
                   [20 20 5 5] [130 175 5 5]
                   [176 130 5 5] [173 400 5 5]]
        base-region [50 50 250 250]
        indexed-objs (group-by #(qt/get-index base-region %) base-objs)]
    (are [a b] (= a b)
               1 1
               (count indexed-objs) 5
               (count (mapcat concat (vals indexed-objs))) 6
               (count (:II indexed-objs)) 2
               )))

(dissoc (group-by #(qt/get-index test-region %) test-objs) :NONE)

(def others [:a :b])
(def objects {:a [1 2 3] :b [2 3 4]})
(map #(% objects) others)
{:a (+ 5 2)}
(run-tests 'quadtree.core-test)

(defn select-values [map ks]
  (remove nil? (reduce #(conj %1 (map %2)) [] ks)))
