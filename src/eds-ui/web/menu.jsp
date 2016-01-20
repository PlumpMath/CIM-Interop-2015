<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="utf-8">
    <title> EDS Suite </title>
    <meta name="description" content="">
    <meta name="author" content="">

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <!-- Basic Styles -->
    <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/font-awesome.min.css">

    <!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
    <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-skins.css">

    <!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
    <link rel="stylesheet" type="text/css" media="screen" href="css/demo.css">

    <!-- FAVICONS -->
    <link rel="shortcut icon" href="img/favicon/favicon.ico" type="image/x-icon">
    <link rel="icon" href="img/favicon/favicon.ico" type="image/x-icon">

    <!-- GOOGLE FONT -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

</head>
<body class="">
<!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

<!-- HEADER -->
<header id="header">
    <div id="logo-group">
        <span id="logo"> <img src="img/logo.png" style="margin-top:-10px;width:100%;height:100%"> </span>
    </div>


    <!-- pulled right: nav area -->
    <div class="pull-right">

        <!-- collapse menu button -->
        <div id="hide-menu" class="btn-header pull-right">
            <span> <a href="javascript:void(0);" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
        </div>
        <!-- end collapse menu -->

        <!-- logout button -->
        <div id="logout" class="btn-header transparent pull-right">
            <span> <a href="\" title="Sign Out"><i class="fa fa-sign-out"></i></a> </span>
        </div>
        <!-- end logout button -->


    </div>
    <!-- end pulled right: nav area -->

</header>
<!-- END HEADER -->

<!-- Left panel : Navigation area -->
<!-- Note: This width of the aside area can be adjusted through LESS variables -->
<aside id="left-panel">

    <!-- User info -->
    <div class="login-info">
				<span> <!-- User image size is adjusted inside CSS, it should stay as it -->

					<a href="javascript:void(0);" id="show-shortcut">
						<span>
							Test User
						</span>
                        <i class="fa fa-angle-down"></i>
                    </a>

				</span>
    </div>
    <!-- end user info -->

    <!-- NAVIGATION : This navigation is also responsive

    To make this navigation dynamic please make sure to link the node
    (the reference to the nav > ul) after page load. Or the navigation
    will not initialize.
    -->
    <nav>
        <!-- NOTE: Notice the gaps after each icon usage <i></i>..
        Please note that these links work a bit different than
        traditional hre="" links. See documentation for details.
        -->
        <ul>
            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-question"></i> <span class="menu-item-parent">Search & Analytics</span></a>
                <ul>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/query/query-builder.jsp');" title="Query Builder" style="cursor:pointer"> <span class="menu-item-parent">Query Builder</span></a>
                    </li>
                 </ul>
            </li>
            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-medkit"></i> <span class="menu-item-parent">Clinical Statements</span></a>
                <ul>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp?resource_type=allergy_intolerance');" title="AllergyIntolerance" style="cursor:pointer"> <span class="menu-item-parent">AllergyIntolerance</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp?resource_type=condition');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Condition</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Procedure</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">FamilyMemberHistory</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Medication</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">MedicationOrder</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">MedicationAdministration</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">MedicationDispense</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">MedicationStatement</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Immunization</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp?resource_type=observation');" title="Observation" style="cursor:pointer"> <span class="menu-item-parent">Observation</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">DiagnosticReport</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">DiagnosticOrder</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Specimen</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">CarePlan</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Goal</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">ReferralRequest</span></a>
                    </li>

                </ul>
            </li>
            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-user"></i> <span class="menu-item-parent">Identification Statements</span></a>
                <ul>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Patient</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Practitioner</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Organization</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">HealthcareService</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Location</span></a>
                    </li>

                </ul>
            </li>
            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">Workflow Statements</span></a>
                <ul>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp?resource_type=encounter');" title="Encounter" style="cursor:pointer"> <span class="menu-item-parent">Encounter</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">EpisodeOfCare</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Appointment</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">AppointmentResponse</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Schedule</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Slot</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">Order</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">OrderResponse</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">CommunicationRequest</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">ProcessRequest</span></a>
                    </li>
                    <li>
                        <a onclick="menuFormLoader('frame1','${pageContext.request.contextPath}/resource/resource-wizard.jsp');" title="Condition" style="cursor:pointer"> <span class="menu-item-parent">ProcessResponse</span></a>
                    </li>
                </ul>
            </li>

        </ul>
    </nav>
    <span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>

</aside>
<!-- END NAVIGATION -->

<!-- MAIN PANEL -->
<div id="main" role="main">

    <!-- RIBBON -->
    <div id="ribbon">

        <span class="ribbon-button-alignment"> <span id="refresh" class="btn btn-ribbon" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true"><i class="fa fa-refresh"></i></span> </span>

        <!-- breadcrumb -->
        <ol class="breadcrumb">
            <li>
                Endeavour Data-Store Testing Suite
            </li>

        </ol>
        <!-- end breadcrumb -->

        <!-- You can also add more buttons to the
        ribbon for further usability

        Example below:

        <span class="ribbon-button-alignment pull-right">
        <span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa-grid"></i> Change Grid</span>
        <span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa-plus"></i> Add</span>
        <span id="search" class="btn btn-ribbon" data-title="search"><i class="fa-search"></i> <span class="hidden-mobile">Search</span></span>
        </span> -->

    </div>
    <!-- END RIBBON -->

    <!-- MAIN CONTENT -->
    <div id="content">

        <iframe
                id="frame1" style="display:none; height: 10000px; "  frameborder="0">
        </iframe>



    </div>
    <!-- END MAIN CONTENT -->

</div>
<!-- END MAIN PANEL -->



<!--================================================== -->

<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
<script data-pace-options='{ "restartOnRequestAfter": true }' src="js/plugin/pace/pace.min.js"></script>

<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script>
    if (!window.jQuery) {
        document.write('<script src="js/libs/jquery-2.0.2.min.js"><\/script>');
    }
</script>

<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script>
    if (!window.jQuery.ui) {
        document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');
    }
</script>

<!-- JS TOUCH : include this plugin for mobile drag / drop touch events
<script src="js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->

<!-- BOOTSTRAP JS -->
<script src="js/bootstrap/bootstrap.min.js"></script>

<!-- CUSTOM NOTIFICATION -->
<script src="js/notification/SmartNotification.min.js"></script>

<!-- JARVIS WIDGETS -->
<script src="js/smartwidgets/jarvis.widget.min.js"></script>

<!-- EASY PIE CHARTS -->
<script src="js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>

<!-- SPARKLINES -->
<script src="js/plugin/sparkline/jquery.sparkline.min.js"></script>

<!-- JQUERY VALIDATE -->
<script src="js/plugin/jquery-validate/jquery.validate.min.js"></script>

<!-- JQUERY MASKED INPUT -->
<script src="js/plugin/masked-input/jquery.maskedinput.min.js"></script>

<!-- JQUERY SELECT2 INPUT -->
<script src="js/plugin/select2/select2.min.js"></script>

<!-- JQUERY UI + Bootstrap Slider -->
<script src="js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

<!-- browser msie issue fix -->
<script src="js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

<!-- FastClick: For mobile devices -->
<script src="js/plugin/fastclick/fastclick.js"></script>

<!--[if IE 7]>

<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

<![endif]-->

<!-- Demo purpose only -->
<script src="js/demo.js"></script>

<!-- MAIN APP JS FILE -->
<script src="js/app.js"></script>

<!-- PAGE RELATED PLUGIN(S) -->
<script src="js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script src="js/plugin/fuelux/wizard/wizard.js"></script>


<script type="text/javascript">

    // DO NOT REMOVE : GLOBAL FUNCTIONS!
    var frames = ['frame1'];

    $(document).ready(function() {

        pageSetUp();

        var newwidth = Math.min(1280, $(window).width() - 50);
        if (document.getElementById('left-panel').offsetLeft >= 0)
            newwidth -= $('#left-panel').width();

        frames.forEach( function(frame) {
            document.getElementById(frame).style.width = newwidth+'px';
        });

        $(window).resize(function() {
            var newwidth = Math.min(1280, $(window).width() - 50);
            if (document.getElementById('left-panel').offsetLeft >= 0)
                newwidth -= $('#left-panel').width();

            frames.forEach( function(frame) {
                document.getElementById(frame).style.width = newwidth+'px';
            });
        });
    })

    function menuFormLoader(selectedFrame, url) {
        // hide all
        frames.forEach( function(frame) {
            document.getElementById(frame).style.display = "none";
        });

        var frame = document.getElementById(selectedFrame);

        if (frame.src == "")
            frame.src = url;

        // show selected frame
        frame.style.display = "inline";
    }

</script>

<!-- Your GOOGLE ANALYTICS CODE Below -->
<script type="text/javascript">
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-XXXXXXXX-X']);
    _gaq.push(['_trackPageview']);

    (function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.condition ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();

</script>

</body>

</html>