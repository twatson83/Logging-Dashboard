(ns logging-dashboard.components.log_dashboard.header.settings
  (:require [logging-dashboard.stores.config  :as config-store      :refer [validate-page-size validate-refresh-interval]]
            [reagent-modals.modals            :as reagent-modals    :refer [modal! modal-window close-modal!]]
            [logging-dashboard.dispatcher     :as dispatcher]
            [reagent.core                     :as reagent]
            [cljs-flux.dispatcher             :refer [dispatch]]
            [taoensso.encore                  :refer (tracef debugf infof warnf errorf)]
            [reagent-forms.core               :refer [bind-fields]]))

(defn validate-doc 
  [doc]
  (and (validate-page-size (:page-size @doc))
       (validate-refresh-interval (:refresh-interval @doc))))

(defn settings-modal
  [table-settings]
  (let [doc (reagent/atom {:page-size (:page-size @table-settings) :refresh-interval (:refresh-interval @table-settings)
                   :name (:name @table-settings)})]
    (fn []
      [:div
       [:div.modal-header
        [:button.close {:type "button" :data-dismiss "modal" :aira-label "Close"} 
         [:span {:aria-hidden "true"} "x"]]
        [:h4.modal-title "Settings"]]
       [:div.modal-body
        [:form
         [:div.form-group 
          [:label.control-label {:for "name"} "Name"]
          [bind-fields [:input.form-control {:field :text :id :name}] doc]]
         [:div.form-group {:class (if-not (validate-page-size (:page-size @doc)) "has-error")}
          [:label.control-label {:for "page-size"} "Page Size"]
          [bind-fields [:input.form-control {:field :numeric :id :page-size}] doc]
          [:span.error-message "Page size must be greater than 0."]]
         [:div.form-group {:class (if-not (validate-refresh-interval (:refresh-interval @doc))  "has-error")}
          [:label.control-label {:for "refresh-interval"} "Refresh Interval (seconds)"]
          [bind-fields [:input.form-control {:field :numeric :id :refresh-interval
                                             :in-fn #(/ % 1000)
                                             :out-fn #(* % 1000)}] doc]
          [:span.error-message "Refresh interval must greater than or equal to 0."]]]]
       [:div.modal-footer
        [:button.btn.btn-danger.pull-left.btn-sm {:type "button" :on-click #(do (dispatch dispatcher/delete-config (get-in @table-settings [:name]))
                                                                                (close-modal!))} "Delete Dashboard"] 
        [:button.btn.btn-default {:type "button"
                                  :on-click #(let [{:keys [page-size refresh-interval name]} @doc]
                                               (when (validate-doc doc)
                                                 (dispatch dispatcher/save-dashboard {:page-size page-size 
                                                                                      :refresh-interval refresh-interval
                                                                                      :name name})
                                                 (close-modal!)))} "Save Dashboard"]
        [:button.btn.btn-default {:type "button"
                                  :on-click #(let [{:keys [page-size refresh-interval name]} @doc]
                                               (when (validate-doc doc)
                                                 (dispatch dispatcher/update-settings {:page-size page-size 
                                                                                       :refresh-interval refresh-interval
                                                                                       :name name})
                                                 (close-modal!)))} "Close"]]])))

(defn settings 
  [table-settings]
  [:a.btn.btn-default.btn-sm.pull-right.log-table-button {:href "#" 
                                                          :on-click #(do (.preventDefault %)
                                                                         (reagent-modals/modal! [settings-modal table-settings]))}
   [:span.glyphicon.glyphicon-cog]])

