package com.anningtex.commonbasedata.entity;

import java.util.List;

import lombok.Data;

/**
 * @Author Song
 */
@Data
public class RecentBean {
    private String id;
    private String load_date;
    private String supplier_delivery_no;
    private List<DeliverListDetailBean> deliver_list_detail;
    private List<String> order_no_list;
    private List<String> supplier_name_list;

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

    @Data
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
    }
}
