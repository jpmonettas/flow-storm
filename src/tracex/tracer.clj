(ns tracex.tracer
  (:require  [taoensso.sente  :as sente]))

(defonce send-fn-a (atom nil))

(defn init-trace [traced-form-id form]
  (@send-fn-a [:tracex/init-trace {:traced-form-id traced-form-id :form form}]))

(defn add-trace [traced-form-id coor form result]
  (@send-fn-a [:tracex/add-trace {:traced-form-id traced-form-id :coor coor :form form :result result}]))

(defn connect []
  (let [{:keys [chsk ch-recv send-fn state]} (sente/make-channel-socket-client! "/chsk"  nil {:type :ws
                                                                                              :client-id "tracer"
                                                                                              :host "localhost"
                                                                                              :port 8080})]
    (reset! send-fn-a send-fn)))
