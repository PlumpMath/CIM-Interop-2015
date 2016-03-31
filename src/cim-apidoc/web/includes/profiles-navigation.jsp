<%@ page import="java.util.LinkedHashMap" %>

<ul id="profile-nav-menu" class="nav nav-tabs">

    <%

        LinkedHashMap tabs = new LinkedHashMap();

        tabs.put("profiles.html", "Resource profiles");
        tabs.put("profiles-extensions.html", "Extensions");
        tabs.put("profiles-valuesets.html", "Valuesets");
        tabs.put("profiles-codesystems.html", "Code systems");
        tabs.put("profiles-identifiersystems.html", "Identifier systems");

        String pageName = this.getClass().getSimpleName().replaceAll("_", ".").replaceAll(".002d", "-");

        for (Object key : tabs.keySet())
        {
            String active = "";

            if (key.equals(pageName))
                active = " class=\"active\"";

            out.println("<li role=\"presentation\"" + active + "><a href=\"" + key + "\">" + (String)tabs.get(key) + "</a></li>");
        }
    %>

</ul>