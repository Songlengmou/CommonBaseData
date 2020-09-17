package com.anningtex.commonbasedata.entity;

import java.util.List;

/**
 * @Author Song
 */
public class RecentBean {
    private String id;
    private String load_date;
    private String supplier_delivery_no;
    private List<DeliverListDetailBean> deliver_list_detail;
    private List<String> order_no_list;
    private List<String> supplier_name_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoad_date() {
        return load_date;
    }

    public void setLoad_date(String load_date) {
        this.load_date = load_date;
    }

    public String getSupplier_delivery_no() {
        return supplier_delivery_no;
    }

    public void setSupplier_delivery_no(String supplier_delivery_no) {
        this.supplier_delivery_no = supplier_delivery_no;
    }

    public List<DeliverListDetailBean> getDeliver_list_detail() {
        return deliver_list_detail;
    }

    public void setDeliver_list_detail(List<DeliverListDetailBean> deliver_list_detail) {
        this.deliver_list_detail = deliver_list_detail;
    }

    public List<String> getOrder_no_list() {
        return order_no_list;
    }

    public void setOrder_no_list(List<String> order_no_list) {
        this.order_no_list = order_no_list;
    }

    public List<String> getSupplier_name_list() {
        return supplier_name_list;
    }

    public void setSupplier_name_list(List<String> supplier_name_list) {
        this.supplier_name_list = supplier_name_list;
    }

    public static class DeliverListDetailBean {
        /**
         * delivery_id : 105
         * order_no : SJ190724M
         * supplier_id : 251
         * supplier_name : 临清三和纺织集团有限公司
         */

        private String delivery_id;
        private String order_no;
        private String supplier_id;
        private String supplier_name;
        private String meters_per_bale_unit_id;
        private int bales_quantity;
        private int meters_per_bale;
        private int warehouse_count;
        private String meters_per_bale_unit_name_en;
        private String notes;
        private int checked;
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public int getWarehouse_count() {
            return warehouse_count;
        }

        public void setWarehouse_count(int warehouse_count) {
            this.warehouse_count = warehouse_count;
        }

        public String getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(String delivery_id) {
            this.delivery_id = delivery_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }

        public String getMeters_per_bale_unit_id() {
            return meters_per_bale_unit_id;
        }

        public void setMeters_per_bale_unit_id(String meters_per_bale_unit_id) {
            this.meters_per_bale_unit_id = meters_per_bale_unit_id;
        }

        public int getBales_quantity() {
            return bales_quantity;
        }

        public void setBales_quantity(int bales_quantity) {
            this.bales_quantity = bales_quantity;
        }

        public int getMeters_per_bale() {
            return meters_per_bale;
        }

        public void setMeters_per_bale(int meters_per_bale) {
            this.meters_per_bale = meters_per_bale;
        }

        public String getMeters_per_bale_unit_name_en() {
            return meters_per_bale_unit_name_en;
        }

        public void setMeters_per_bale_unit_name_en(String meters_per_bale_unit_name_en) {
            this.meters_per_bale_unit_name_en = meters_per_bale_unit_name_en;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
