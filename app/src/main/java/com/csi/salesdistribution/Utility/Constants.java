package com.csi.salesdistribution.Utility;

import com.csi.salesdistribution.Model.Product;

import java.util.ArrayList;



public class Constants {

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static ArrayList<com.csi.salesdistribution.Model.Product> productList = new ArrayList<>();


    public class Api{
        public static final String API_LOG_IN = "http://192.168.0.36/api/v1/login";
        public static final String API_CUSTOMER = "http://192.168.0.36/api/v1/customer";
        public  static final String API_CUSTOMERAuto="http://192.168.0.36/api/v1/customer/autocomplete?q=";
        public  static final String API_PRODUCT="http://192.168.0.36/api/v1/product/autocomplete?q=";
    }

    public class LogInFields{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String USER_NAME = "username";
        public static final String EMP_CODE = "emp_code";
        public static final String API_TOKEN = "api_token";
        //public static final String USER_STATUS = "user_status";
        public static final String STATUS = "status";

    }
    public class Product{
        public static final String PRODUCT_ID = "prod_id";
        public static final String NAME = "name";
        public static final String RATE = "rate";
        public static final String VAT = "vat";
        public static final String TRADE_VALUE = "trade_value";
        public static final String PACK_SIZE ="pack_size";
    }
}
