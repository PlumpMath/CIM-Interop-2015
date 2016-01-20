
function setEventHandlers() {
    var existingResourcesWidget = $('#existingResourcesWidget');
    existingResourcesWidget.find('#btnAdd').click(onAddResource);
    existingResourcesWidget.find('#btnEdit').click(onEditResource);
    existingResourcesWidget.find('#btnDelete').click(onDeleteResource);

}

$(document).ready(function() {

    var resourceType = getParamValue('resource_type');

    setEventHandlers();

    loadExistingResources(resourceType,"");

    pageSetUp();

    $('#resource_header').html(resourceType+" statement");
    $('#resource_header2').html("Add new "+resourceType+" statement");

    //Bootstrap Wizard Validations
    var $validator = $("#wizard-1").validate({



        messages: {
            resource: "Please specify the statement data",
            version: "Please specify the statement version"



        },

        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }


    });

    $('#bootstrap-wizard-1').bootstrapWizard({
        'tabClass': 'form-wizard',
        'onNext': function (tab, navigation, index) {
            var $valid = $("#wizard-1").valid();
            if (!$valid) {
                $validator.focusInvalid();
                return false;
            } else {
                var tabCount = navigation.find('li').length;
                var current = index+1;

                $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).addClass(
                    'complete');
                $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).find('.step')
                    .html('<i class="fa fa-check"></i>');
                // Optional: Show Done button when on last tab;
                // It is invisible by default.
                $('#bootstrap-wizard-1').find('.last').toggle(current >= tabCount);

                $('#bootstrap-wizard-1').find('.next').toggle(current < 2);
            }
        }

    });



});

function getParamValue(paramName)
{
    var url = window.location.search.substring(1); //get rid of "?" in querystring
    var qArray = url.split('&'); //get key-value pairs
    for (var i = 0; i < qArray.length; i++)
    {
        var pArr = qArray[i].split('='); //split key and value
        if (pArr[0] == paramName)
            return pArr[1]; //return value
    }
}

function formSubmit() {

    var resourceType = getParamValue('resource_type');

    var resource = {};

    var newGuid = guid();

    switch(resourceType) {
        case "allergy_intolerance":
            resource.allergyIntoleranceId = newGuid;
            break;
        case "condition":
            resource.conditionId = newGuid;
            break;
        case "observation":
            resource.observationId = newGuid;
            break;
        case "encounter":
            resource.encounterId = newGuid;
            break;
    }

    resource.patientId = "11af0e7f-be18-431e-9be9-fd1adb2f0742";
    resource.serviceId = document.getElementById('service').value;
    var map = {};
    map.id = newGuid;
    map.code = document.getElementById('code').value;
    map.severity = document.getElementById('severity').value;
    map.status = document.getElementById('status').value;
    map.statement_version = "1.0.0";
    resource.effectiveDateTime = new Date(document.getElementById('effective_datetime').value);
    resource.metaData = map;
    resource.entryData = document.getElementById('entry_data').value;
    resource.lastUpdated = new Date();

    saveResource(resource, resourceType);

    loadExistingResources(resourceType,"");

    document.getElementById("existing-resources").style.display = "inline";
    document.getElementById("resource-wizard").style.display = "none";

}

function loadExistingResources(resourceType, filters) {

    var urlQuery = '/Resources/GetResources?resource_type='+resourceType;

    if (filters!='') {
        urlQuery += filters;
    }

    $.ajax({
        type: 'GET',
        url: urlQuery,
        async: false,
        success: function (data, textStatus, jqXHR) {
            // Build existing Resources table
            var existingResources = JSON.parse(data);

            var existingResourceTable = "";



            for (var i = 0; i < existingResources.length; i++) {

                switch(resourceType) {
                    case "allergy_intolerance":
                        existingResourceTable += '<tr id="' + existingResources[i].allergyIntoleranceId +'">';
                        existingResourceTable += '<td><label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i> </label></th>';
                        existingResourceTable += '<td>' + existingResources[i].allergyIntoleranceId + '</td>';
                        break;
                    case "condition":
                        existingResourceTable += '<tr id="' + existingResources[i].conditionId +'">';
                        existingResourceTable += '<td><label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i> </label></th>';
                        existingResourceTable += '<td>' + existingResources[i].conditionId + '</td>';
                        break;
                    case "observation":
                        existingResourceTable += '<tr id="' + existingResources[i].observationId +'">';
                        existingResourceTable += '<td><label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i> </label></th>';
                        existingResourceTable += '<td>' + existingResources[i].observationId + '</td>';
                        break;
                    case "encounter":
                        existingResourceTable += '<tr id="' + existingResources[i].encounterId +'">';
                        existingResourceTable += '<td><label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i> </label></th>';
                        existingResourceTable += '<td>' + existingResources[i].encounterId + '</td>';
                        break;
                }

                existingResourceTable += '<td>' + existingResources[i].serviceId + '</td>';
                existingResourceTable += '<td>' + new Date(existingResources[i].effectiveDateTime).toString() + '</td>';
                existingResourceTable += '<td>' + JSON.stringify(existingResources[i].metaData, null, 4)+ '</td>';
                existingResourceTable += '<td>' + existingResources[i].entryData + '</td>';

                existingResourceTable += '</tr>';
            }

            $('#existingResourcesTable').html(existingResourceTable);


        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        },
        cache: false,
        contentType: false,
        processData: false
    });
}

function saveResource(resource, resourceType) {
    $.ajax({
        type: 'POST',
        url: '/Resources/SaveResource?resource_type='+resourceType,
        data: JSON.stringify(resource),
        async: false,
        success: function (data, textStatus, jqXHR) {

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        },
        cache: false,
        contentType: false,
        processData: false
    });

    $.smallBox({
        title: "Resource",
        content: "<i class='fa fa-clock-o'></i> <i>Has successfully been saved!</i>",
        color: "#5F895F",
        iconSmall: "fa fa-check bounce animated",
        timeout: 4000
    });
}

function onAddResource() {

    $('#bootstrap-wizard-1').find('.next').toggle(true);
    $('#bootstrap-wizard-1').find('.last').toggle(false);

    document.getElementById("wizard-1").reset();

    document.getElementById('resourceId').value = "";

    document.getElementById("existing-resources").style.display = "none";
    document.getElementById("resource-wizard").style.display = "inline";

    $('#bootstrap-wizard-1').bootstrapWizard('show',0);

}

function onEditResource() {

}

function onDeleteResource() {

}




function cancelWizard() {

    $.SmartMessageBox({
        title : "<i class='fa fa-warning' style='color:green'></i> Cancel New Resource",
        content : "Would you like to CANCEL the creation of this new entry?",
        buttons : '[No][Yes]'
    }, function(ButtonPressed) {
        if (ButtonPressed == "Yes") {
            document.getElementById("existing-resources").style.display = "inline";
            document.getElementById("resource-wizard").style.display = "none";
        }

    });


}

function guid() {
    function _p8(s) {
        var p = (Math.random().toString(16)+"000000000").substr(2,8);
        return s ? "-" + p.substr(0,4) + "-" + p.substr(4,4) : p ;
    }
    return _p8() + _p8(true) + _p8(true) + _p8();
}

function syntaxHighlight(json) {
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}
