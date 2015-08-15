package org.endeavourhealth.cim.routes.config;


public class Route {

    public static final String CIM_CORE = direct(CoreRouteName.CIM_CORE);

    public static final String direct(String route) {
        return "direct:" + route;
    }
}
