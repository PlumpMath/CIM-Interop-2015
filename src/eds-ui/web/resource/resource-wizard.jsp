<%@include file="/common/page-header.jsp" %>
<body class="">
<!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

<!-- MAIN CONTENT -->
<div id="content">

<!-- widget grid -->
<section id="widget-grid" class="">

<!-- existing resources table -->
<div class="row" id="existing-resources" style="display:inline">

    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

        <!-- Widget ID (each widget will need unique ID)-->
        <div class="jarviswidget jarviswidget-color-greenDark" id="existingResourcesWidget" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
             data-widget-editbutton="false"
             data-widget-togglebutton="false"
             data-widget-deletebutton="false"
             data-widget-fullscreenbutton="false"
             data-widget-custombutton="false">
            <!-- widget options:
            usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

            data-widget-colorbutton="false"
            data-widget-editbutton="false"
            data-widget-togglebutton="false"
            data-widget-deletebutton="false"
            data-widget-fullscreenbutton="false"
            data-widget-custombutton="false"
            data-widget-collapsed="true"
            data-widget-sortable="false"

            -->

            <header>
                <span class="widget-icon"> <i class="fa fa-filter"></i> </span>
                <h2><div id="resource_header"></div></h2>

            </header>

            <!-- widget div-->
            <div>

                <!-- widget edit box -->
                <div class="jarviswidget-editbox">
                    <!-- This area used as dropdown edit box -->

                </div>
                <!-- end widget edit box -->

                <!-- widget content -->
                <div class="widget-body no-padding">
                    <%@include file="/common/widget-toolbar-add-edit-delete.jsp" %>
                    <%@include file="/common/widget-toolbar-search.jsp" %>

                    <table class="table table-bordered table-striped table-condensed table-hover smart-form has-tickbox">
                        <thead>
                        <tr>
                            <th>
                                <label class="checkbox">
                                    <input type="checkbox" name="checkbox-inline">
                                </label>
                            </th>
                            <th>Entry Id</th>
                            <th>Service Id</th>
                            <th>Effective Date</th>
                            <th>Meta Data</th>
                            <th>Entry Data<a href="javascript:void(0);" class="btn btn-xs btn-default pull-right"><i class="fa fa-filter"></i></a></th>


                        </tr>
                        </thead>
                        <tbody id="existingResourcesTable">

                        </tbody>
                    </table>



                </div>
                <!-- end widget content -->

            </div>
            <!-- end widget div -->

        </div>
        <!-- end widget -->

    </article>
    <!-- WIDGET END -->

</div>
<!-- end existing resources table -->

<!-- resource wizard -->
<div class="row" id="resource-wizard" style="display:none">

<!-- NEW WIDGET START -->
<article class="col-sm-12 col-md-12 col-lg-6">

<!-- Widget ID (each widget will need unique ID)-->
<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
     data-widget-editbutton="false"
     data-widget-togglebutton="false"
     data-widget-deletebutton="false"
     data-widget-fullscreenbutton="false"
     data-widget-custombutton="false">
<!-- widget options:
usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

data-widget-colorbutton="false"
data-widget-editbutton="false"
data-widget-togglebutton="false"
data-widget-deletebutton="false"
data-widget-fullscreenbutton="false"
data-widget-custombutton="false"
data-widget-collapsed="true"
data-widget-sortable="false"

-->
<header>
    <span class="widget-icon"> <i class="fa fa-filter"></i> </span>
    <h2><div id="resource_header2"></div></h2>

</header>

<!-- widget div-->
<div>

    <!-- widget edit box -->
    <div class="jarviswidget-editbox">
        <!-- This area used as dropdown edit box -->

    </div>
    <!-- end widget edit box -->

    <!-- widget content -->
    <div class="widget-body">

        <div class="row">
            <form id="wizard-1" novalidate="novalidate">
                <input type="hidden" id="resourceId">
                <div id="bootstrap-wizard-1" class="col-sm-12">
                    <div class="form-bootstrapWizard">
                        <ul class="bootstrapWizard form-wizard">
                            <li class="active" data-target="#step1">
                                <a href="#tab1" data-toggle="tab"> <span class="step">1</span> <span class="title">Entry Information</span> </a>
                            </li>

                            <li data-target="#step3">
                                <a href="#tab3" data-toggle="tab"> <span class="step">2</span> <span class="title">Save Entry</span> </a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab1">
                            <br>
                            <h3><strong>Step 1 </strong> - Entry Information</h3>

                            <div class="row">

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>Service</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-hospital fa-lg fa-fw"></i></span>

                                            <select id="service" name="service" class="form-control input-lg">
                                                <option value="" selected="selected">Select Service</option>
                                                <option value="24c33aad-92bd-41d8-a622-ab88e00b97d6">Service 1</option>
                                                <option value="aecf6166-94b4-4a39-ad92-4eb212a938e5">Service 2</option>
                                                <option value="49aed362-bad8-4257-9b0e-3d4859f6a437">Service 3</option>
                                                <option value="2f64d424-3759-4c1f-b7cc-34d7ae8ba4c1">Service 4</option>
                                                <option value="aa8fedf6-cd0f-4903-8161-db0175ad264b">Service 5</option>
                                            </select>

                                        </div>
                                    </div>

                                </div>

                            </div>

                            <div class="row">

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>Effective Date</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar fa-lg fa-fw"></i></span>
                                            <input class="form-control input-lg" placeholder="Date Recorded" type="date" name="effective_datetime" id="effective_datetime" >

                                        </div>
                                    </div>

                                </div>

                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Code</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-edit fa-lg fa-fw"></i></span>
                                            <input class="form-control input-lg" placeholder="Code" type="text" name="code" id="code" >

                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>Status</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-question fa-lg fa-fw"></i></span>

                                            <select id="status" name="status" class="form-control input-lg">
                                                <option value="" selected="selected">Select Status</option>
                                                <option value="current">Current</option>
                                                <option value="past">Past</option>
                                            </select>

                                        </div>
                                    </div>

                                </div>

                            </div>
                            <div class="row">

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>Severity</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-exclamation fa-lg fa-fw"></i></span>

                                            <select id="severity" name="severity" class="form-control input-lg">
                                                <option value="" selected="selected">Select Severity</option>
                                                <option value="mild">Mild</option>
                                                <option value="moderate">Moderate</option>
                                                <option value="severe">Severe</option>
                                            </select>

                                        </div>
                                    </div>

                                </div>

                            </div>

                            <div class="row">

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>Entry Data</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-edit fa-lg fa-fw"></i></span>
                                            <textarea class="form-control input-lg" style="height:200px" placeholder="Entry Data" name="entry_data" id="entry_data"></textarea>

                                        </div>
                                    </div>

                                </div>

                            </div>



                        </div>


                        <div class="tab-pane" id="tab2">
                            <br>
                            <h3><strong>Step 2</strong> - Save Entry</h3>
                            <br>
                            <h1 class="text-center text-success"><strong><i class="fa fa-check fa-lg"></i> Complete</strong></h1>
                            <h4 class="text-center">Click done to finish and save</h4>
                            <br>
                            <br>
                        </div>

                        <div class="form-actions">
                            <div class="row">
                                <div class="col-sm-12">
                                    <ul class="pager wizard no-margin">
                                        <!--<li class="previous first disabled">
                                        <a href="javascript:void(0);" class="btn btn-lg btn-default"> First </a>
                                        </li>-->
                                        <li class="cancel">
                                            <a onclick="cancelWizard();" class="btn btn-lg btn-default"> Cancel </a>
                                        </li>
                                        <li class="previous disabled">
                                            <a href="javascript:void(0);" class="btn btn-lg btn-default"> Previous </a>
                                        </li>
                                        <!--<li class="next last">
                                        <a href="javascript:void(0);" class="btn btn-lg btn-primary"> Last </a>
                                        </li>-->
                                        <li class="last" style="display:none;" ><a class="btn btn-lg txt-color-darken" onclick="formSubmit();">Done</a></li>
                                        <li class="next">
                                            <a href="javascript:void(0);" class="btn btn-lg txt-color-darken"> Next </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </form>
        </div>

    </div>
    <!-- end widget content -->

</div>
<!-- end widget div -->

</div>
<!-- end widget -->

</article>
<!-- WIDGET END -->

</div>
<!-- end resource wizard -->

</section>
<!-- end widget grid -->

</div>
<!-- END MAIN CONTENT -->



<%@include file="/common/page-footer.jsp"%>

<!-- PAGE RELATED PLUGIN(S) -->
<script src="/js/plugin/select2/select2.min.js"></script>

<script src="/resource/resource-wizard.js"></script>

