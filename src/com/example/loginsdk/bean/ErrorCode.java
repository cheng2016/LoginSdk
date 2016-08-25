package com.example.loginsdk.bean;


public interface ErrorCode {
	
	enum Success {

        SUCCESS(200);

        private final int code;

        Success(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 100段,请求参数错误
     */
    enum RequestError {

        /**
         * 请求参数为空
         */
        REQUEST_PARAMETER_NULL(100),
        /**
         * 请求签名错误
         */
        REQUEST_SIGN_ERROR(101),
        /**
         * 请求参数错误
         */
        REQUEST_PARAMETER_ERROR(102),
        /**
         * 订单状态失败，不允许发货
         */
        REQUEST_ORDER_FAILED(103);

        private final int code;

        RequestError(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 300段,服务器配置异
     */
    enum ServerConfError {

        /**
         * 渠道配置为空
         */
        CHANNEL_CONF_NULL(300),
        /**
         * 渠道指定API接口配置为空
         */
        CHANNEL_CONF_API_NULL(301),
        /**
         * 渠道域名配置为空
         */
        CHANNEL_CONF_DOMAIN_NULL(302),
        /**
         * APP配置为空
         */
        APP_CONF_NULL(303),
        /**
         * 应用渠道对应关系配置错误,秘钥为空
         */
        APP_CHANNEL_CONF_SECRET_NULL(304),
        /**
         * 应用渠道对应关系配置错误,APPID为空
         */
        APP_CHANNEL_CONF_THIRD_APP_ID_NULL(305),

        /**
         * 未找到包配置
         */
        PACKAGE_CONF_NULL(306),
        /**
         * 未配置支付回调接口
         */
        PACKAGE_CONF_PAY_CALLBACK_NULL(307),
        /**
         * 订单对应缓存错误
         */
        ORDERID_CACHE_ERROR(308);

        private final int code;

        ServerConfError(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 400段,第三方接口异常
     */
    enum APIResultError {

        /**
         * 接口无返回
         */
        INTERFACE_NO_RESULT(400),
        /**
         * 接口返回失败状态
         */
        INTERFACE_FAILED_RESULT(401),
        /**
         * 请求ID与响应ID不一致
         */
        INTERFACE_ID_NOT_EQUAL(402),
        /**
         * 响应data或data中相关数据为空
         */
        INTERFACE_RESPONSE_DATA_NULL(403);

        private final int code;

        APIResultError(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 500段,程序异常
     */
    enum ServerError {

        /**
         * 服务端异常
         */
        SERVER_ERROR(500),
        /**
         * 正在缓存
         */
        CACHING(501),
        /**
         * 未找到登录验证处理方法
         */
        PROGRAM_ERROR_NO_LOGIN_HANDLER(502);

        private final int code;

        ServerError(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
