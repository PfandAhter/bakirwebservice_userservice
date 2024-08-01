package com.bws.userservice.model.constants;

public class PropertyConstants {
    //CONTROLLER PATHS
    public static final String REQUEST_SERVICE_USER_CONTROLLER = "${app.routesController.controllers.userServiceController}";

    public static final String REQUEST_SERVICE_BALANCE_CONTROLLER = "${app.routesController.controllers.balanceServiceController}";

    public static final String REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_FIND_BY_USER_NAME = "${app.routesController.requestMapping.notSecure.user-service.findByUsername}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_BALANCE_SERVICE_BALANCE_ADD = "${app.routesController.requestMapping.secure.balance-service.addBalance}"; //

    public static final String REQUEST_SECURE_REST_CONTROLLER_BALANCE_SERVICE_BALANCE_GET = "${app.routesController.requestMapping.secure.balance-service.getBalance}"; //

    public static final String REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_USER_REGISTER = "${app.routesController.requestMapping.notSecure.user-service.registerUser}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_ADD_USER_ADDRESS_INFO = "${app.routesController.requestMapping.secure.user-service.addAddressInfo}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_CHANGE_PASSWORD = "${app.routesController.requestMapping.secure.user-service.passwordChange}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_EXTRACT_ROLE = "${app.routesController.requestMapping.secure.user-service.extractRole}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_USER_VALIDATE_EMAIL = "${app.routesController.requestMapping.secure.user-service.validateUserEmail}";

    public static final String REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_SELLER_REGISTER = "${app.routesController.requestMapping.notSecure.user-service.registerSeller}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_SELLER_ACTIVATE_BY_ADMIN = "${app.routesController.requestMapping.secure.user-service.activateSellerByAdmin}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_GET_SELLERS = "${app.routesController.requestMapping.secure.user-service.getSeller}";

    public static final String REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_EXTRACT_SELLER_NAME = "${app.routesController.requestMapping.notSecure.user-service.extractSellerName}";

    public static final String REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_FORGET_PASSWORD = "${app.routesController.requestMapping.notSecure.user-service.passwordForget}";

    public static final String REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_CHANGE_PW_BY_CODE = "${app.routesController.requestMapping.notSecure.user-service.passwordChangeWithCode}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_USER_PHOTO_ADD = "${app.routesController.requestMapping.secure.user-service.addPhoto}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_USER_PHOTO_GET = "${app.routesController.requestMapping.secure.user-service.getPhoto}";
}
