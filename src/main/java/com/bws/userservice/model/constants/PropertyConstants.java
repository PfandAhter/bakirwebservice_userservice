package com.bws.userservice.model.constants;

public class PropertyConstants {
    //MICROSERVICES PATHS
    public static final String REST_TEMPLATE_REQUEST_MICROSERVICE_PAYMENT_SERVICE_BALANCE_CREATE = "${app.routesMicroservices.paymentService.createBalance}";


    //CONTROLLER PATHS
    public static final String REQUEST_USERSERVICE = "${app.routesController.requestmapping.userServiceController}";

    public static final String REQUEST_LOCKED_USER_FINDUSERBYUSERNAME = "${app.routesController.notlocked.user.findByUsername}";

    public static final String REQUEST_LOCKED_USER_REGISTER = "${app.routesController.notlocked.user.registerUser}";


}
