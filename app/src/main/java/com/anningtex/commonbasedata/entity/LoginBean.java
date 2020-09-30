package com.anningtex.commonbasedata.entity;

import java.util.List;

import lombok.Data;

/**
 * @Author Song
 * @Desc: {"code":1,"msg":"登录成功","data":{"role":"国外同事","menu":{"module":[{"name":"报表","id":41}],"urlList":[{"url":"/api/v1/getSliceUUID","name":"获取分片上传的令牌"},{"url":"/api/v1/uploadSlice","name":"分片上传文件"},{"url":"/api/v1/containerImgUpload","name":"集装箱图片上传"},{"url":"/api/v1/containerFileUpload","name":"集装箱文件上传"},{"url":"/api/v1/getReport11","name":"获取报表11基础数据"},{"url":"/api/v1/getContainerSchedule","name":"报表11获取装箱单"},{"url":"/api/v1/getThinCode","name":"报表11获取细码单"},{"url":"/api/v1/addDailyRecord","name":"新增日记"},{"url":"/api/v1/getDailyRecordList","name":"获取日记列表"},{"url":"/api/v1/getDiaryListByTex","name":"根据品名id获取日记列表"},{"url":"/api/v1/getOrderClass","name":"获取布产单国别"},{"url":"/api/v1/saveForeignFiles","name":"保存国外集装箱文件"},{"url":"/api/v1/getForeignFiles","name":"获取国外集装箱文件"},{"url":"/api/v1/delForeignFiles","name":"删除国外集装箱文件"},{"url":"/api/v1/getList_LogiTitle_ContainerTransfer","name":"获取转运状态"},{"url":"/api/v1/saveLogiInfo_ContainerTransfer","name":"保存转运状态"},{"url":"/api/v1/registerContainerDriver","name":"国外注册司机信息"},{"url":"/api/v1/getList_ContainerTransfer","name":"货柜转运List"},{"url":"/api/v1/onSeaInStore","name":"海上的货柜入仓"},{"url":"/api/v1/getRemainingGoods","name":"获取保税仓剩余货物"},{"url":"/api/v1/getPullerOnwayGoods","name":"获取已发车货物"},{"url":"/api/v1/getPullerGoods","name":"获取正在排车的货物"},{"url":"/api/v1/savePuller","name":"新增和更新货运信息"},{"url":"/api/v1/getBondedWaitList","name":"获取等待入保税仓的货柜"},{"url":"/api/v1/inStock","name":"海上货柜入保税仓"},{"url":"/api/v1/saveGoodsPullerPlan","name":"装车"},{"url":"/api/v1/delGoodsPullerPlan","name":"删除装车货物"},{"url":"/api/v1/getGoodsSpecInOutData","name":"保税仓剩余货物细码单"},{"url":"/api/v1/modifyPullerOnWay","name":"排车变运输中"},{"url":"/api/v1/modifyPullerOffLoaded","name":"运输中变已卸货"},{"url":"/api/v1/saveGoodsSpecPuller","name":"保存货物的出货规格"},{"url":"/api/v1/delGoodsSpecPuller","name":"删除货物的出货规格"},{"url":"/api/v1/savePullerInfoFile","name":"保存货运文件数据"},{"url":"/api/v1/getPullerInfoFile","name":"获取货运文件数据"},{"url":"/api/v1/delPullerInfoFile","name":"删除货运文件数据"},{"url":"/api/v1/bondedImgUpload","name":"保税仓图片上传"},{"url":"/api/v1/bondedFileUpload","name":"保税仓文件上传"},{"url":"/api/v1/getGoodsPullerOffLoaded","name":"获取已入仓的货物列表"},{"url":"/api/v1/getPullerItemGoodSpec","name":"获取装车下单个货物的出货规格"},{"url":"/api/v1/getPullerGoodsSpec","name":"获取装车下所有货物的出货规格"},{"url":"/api/v1/getHistoryLocation","name":"获取集装箱历史位置"},{"url":"/api/v1/getCurrentLocations","name":"获取集装箱当前位置"},{"url":"/api/v1/getTransferHistory","name":"获取转运历史位置信息"}]},"IsSimplePWD":0}}
 */
@Data
public class LoginBean {
    private String role;
    private MenuBean menu;
    private int IsSimplePWD;

    public MenuBean getMenu() {
        return menu;
    }

    public void setMenu(MenuBean menu) {
        this.menu = menu;
    }

    @Data
    public static class MenuBean {
        private List<ModuleBean> module;
        private List<UrlListBean> urlList;

        public List<ModuleBean> getModule() {
            return module;
        }

        public void setModule(List<ModuleBean> module) {
            this.module = module;
        }

        public List<UrlListBean> getUrlList() {
            return urlList;
        }

        public void setUrlList(List<UrlListBean> urlList) {
            this.urlList = urlList;
        }

        @Data
        public static class ModuleBean {
            private String name;
            private int id;
        }

        @Data
        public static class UrlListBean {
            /**
             * url : /api/v1/getSliceUUID
             * name : 获取分片上传的令牌
             */

            private String url;
            private String name;
        }
    }
}
