(ns logging-dashboard.components.log_dashboard.log_table
  (:require [reagent-modals.modals            :as reagent-modals         :refer [modal-window]]
            [logging-dashboard.components.log_dashboard.log_table.header :refer [header]]
            [logging-dashboard.components.log_dashboard.log_table.table  :refer [table]]
            [logging-dashboard.components.log_dashboard.log_table.pager  :refer [pager]]))

(defn log-table 
  [columns table-settings filters sorting logs]
  [:div.log-table
   [:div.container-fluid
    [header columns filters table-settings logs]
    [table logs columns sorting]
    [pager table-settings logs]]
   [reagent-modals/modal-window]])

