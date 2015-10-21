package org.endeavourhealth.common.routes.common;


public class Route {

    public static final String CORE = direct(CoreRouteName.CORE);

    public static final String direct(String route) {
        return "direct:" + route;
    }
}
