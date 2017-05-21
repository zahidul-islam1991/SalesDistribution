package com.csi.salesdistribution.Utility;

import com.csi.salesdistribution.Model.Product;

import java.util.ArrayList;



public class Constants {

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static ArrayList<com.csi.salesdistribution.Model.Product> productList = new ArrayList<>();
    public static ArrayList<com.csi.salesdistribution.Model.Customer> customerList = new ArrayList<>();
    public static ArrayList<com.csi.salesdistribution.Model.Market> marketList = new ArrayList<>();



    public class Api{
        public static final String URL ="http://172.16.31.83:8080/api/v1/";
        public static final String API_LOG_IN = URL+"login";
        //public static final String API_LOG_IN = "http://192.168.5.118:8080/api/v1/login";
        public static final String API_CUSTOMER = URL+"customer";
        public  static final String API_CUSTOMER_AUTO=URL+"customer/autocomplete?q=";
        public  static final String API_PRODUCT=URL+"product/autocomplete?q=";
        public  static final String API_MARKET=URL+"market/autocomplete?q=";
        public static final String API_ORDER_ENTRY = URL+"order";
        public static final String API_CUSTOMER_TYPE = URL+"customer-type";
    }

    public class LogInFields{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String USER_NAME = "username";
        public static final String EMP_CODE = "emp_code";
        public static final String API_TOKEN = "api_token";
        public static final String TERRITORY = "teri_name";
        public static final String TERRITORY_CODE = "teri_code";
        public static final String STATUS = "status";

    }
    public class Product{
        public static final String PRODUCT_ID = "prod_id";
        public static final String NAME = "name";
        public static final String RATE = "rate";
        public static final String VAT = "sku_vat";
        public static final String TRADE_VALUE = "trade_value";
        public static final String PACK_SIZE ="pack_size";
        public static final String DISCOUNT ="sku_discount";
    }

    public class Customer{
        public static final String CUSTOMER_ID = "id";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String CAREOF = "mkt_desc";
    }
    public class Market{
        public static final String MARKET_CODE = "mkt_code";
        public static final String MARKET_DESCRIPTION = "mkt_desc";
    }
    public class CustomerType{
        public static final String CUSTOMER_TYPE = "cust_type";
        public static final String CUSTOMER_DESCRIPTION = "cust_type_desc";
        public static final String JSON_ARRAY = "data";
    }
    public class StaticValue {
        public static final String VAT = "15";
    }
}
