<script>

    function onFilterService() {

        var serviceId = document.getElementById("service_filter").value;

        var resourceType = getParamValue('resource_type');

        var filters = "&service_id="+serviceId;

        loadExistingResources(resourceType,filters);

        $.smallBox({
            title: "Filter",
            content: "<i class='fa fa-clock-o'></i> <i>Service filter applied successfully!</i>",
            color: "#5F895F",
            iconSmall: "fa fa-check bounce animated",
            timeout: 4000
        });
    }

    function onFilterStatus() {

        var status = document.getElementById("status_filter").value;

        var resourceType = getParamValue('resource_type');

        var filters = "&status="+status;

        loadExistingResources(resourceType,filters);

        $.smallBox({
            title: "Filter",
            content: "<i class='fa fa-clock-o'></i> <i>Status filter applied successfully!</i>",
            color: "#5F895F",
            iconSmall: "fa fa-check bounce animated",
            timeout: 4000
        });
    }
</script>

<div class="row">

    <div class="col-sm-6">
        <div class="form-group">
            <label>Filter by Service:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-search fa-lg fa-fw"></i></span>

                <select id="service_filter" name="service_filter" class="form-control input-lg" onchange="onFilterService();">
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
    <div class="col-sm-6">
        <div class="form-group">
            <label>Filter by Code:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-search fa-lg fa-fw"></i></span>
                <input class="form-control input-lg" placeholder="Code" type="text" name="code_filter" id="code_filter" >

            </div>
        </div>
    </div>

</div>

<div class="row">

    <div class="col-sm-6">
        <div class="form-group">
            <label>Filter by Effective Date From:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-search fa-lg fa-fw"></i></span>
                <input class="form-control input-lg" placeholder="Date Recorded From" type="date" name="effective_datetime_from_filter" id="effective_datetime_from_filter" >

            </div>
        </div>

    </div>
    <div class="col-sm-6">
        <div class="form-group">
            <label>Filter by Effective Date To:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-search fa-lg fa-fw"></i></span>
                <input class="form-control input-lg" placeholder="Date Recorded To" type="date" name="effective_datetime_to_filter" id="effective_datetime_to_filter" >

            </div>
        </div>

    </div>

</div>

<div class="row">

    <div class="col-sm-12">
        <div class="form-group">
            <label>Filter by Status:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-search fa-lg fa-fw"></i></span>

                <select id="status_filter" name="status_filter" class="form-control input-lg" onchange="onFilterStatus();">
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
            <label>Filter by Severity</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-search fa-lg fa-fw"></i></span>

                <select id="severity_filter" name="severity_filter" class="form-control input-lg">
                    <option value="" selected="selected">Select Severity</option>
                    <option value="mild">Mild</option>
                    <option value="moderate">Moderate</option>
                    <option value="severe">Severe</option>
                </select>

            </div>
        </div>

    </div>

</div>