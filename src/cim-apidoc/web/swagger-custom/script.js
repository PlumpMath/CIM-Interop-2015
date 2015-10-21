$(function() {
    $('head').append("<link href='https://fonts.googleapis.com/css?family=Droid+Sans:400,700|Open+Sans' rel='stylesheet' type='text/css' />");
    $('#input_apiKey').parent().after("<div class='input'><input placeholder='secret' id='input_privateKey' name='privateKey' type='text' /></div>");
});

function customScriptInitialize(){

    addCustomLogo();
    removeCurl();
    disableHash();
    injectVisualizer();
    injectHashGeneration();
    customizeResponseModelTable();

    function addCustomLogo() {
        if ($('#custom-logo').length == 0) {
            $('<a href="/"><div id="custom-logo"></div></a>').insertBefore('#logo');
        }
    }

    function removeCurl() {
        $('h4').filter(function() {
            return $(this).text() == 'Curl'
        }).css('display', 'none');

        $('.block.curl').css('display', 'none');
    }

    function disableHash() {
        $('input[name=hash]').attr("disabled", "disabled");
        $('input[name=api_key]').attr("disabled", "disabled");
    }

    function injectVisualizer() {
        $('.response').each(function(){
            var responseBodyHeader = $($(this).find("h4")[3]);
            var contentClassId = responseBodyHeader.parents('.content').attr('id');
            responseBodyHeader.wrapInner('<a href="javascript:visualizeJson(\''+ contentClassId + '\');" target="_blank"/>');
        });
    }

    function injectHashGeneration() {
        $("form.sandbox").each(function() {
            var form = this;
            $(form).find("input.submit").each(function() {
                $(this).click(function() {
                    generateHash(form);
                });
            });
        });
    }


    function customizeResponseModelTable() {
        // locate Response Messages table
        var $responseTable = $('h4:contains("Response Messages") + table[class="fullwidth"]');

        // hide "Response Model" column
        $responseTable.find('thead > tr > th:contains("Response Model")').css('display', 'none');
        $responseTable.find('tbody.operation-status > tr > td[width="50%"]').css('display', 'none');

        // change "Headers" column to "Response Body"
        $responseTable.find('thead > tr > th:contains("Headers")').text("Response Body");
        var $responseTableBody = $responseTable.find('tbody.operation-status > tr');

        // Split contents of "Reason" column on '|' and copy to the new "Response Body" column
        $responseTableBody.each(function () {
            var $reasonText = $(this).find('td.markdown > p').html();

            $(this).find('td.markdown > p').text($reasonText.split('|')[0]);
            $(this).find('td.headers').html($reasonText.split('|')[1]);
        });
    }
}

function generateHash(form) {
    var apiKey = $("#input_apiKey").val();
    var privateKey = $("#input_privateKey").val();

    if (apiKey == "") {
        alert("Please enter your API key.");
        $("#input_apiKey").focus();
        event.stopPropagation();
        return;
    }

    if (privateKey == "") {
        alert("Please enter your secret key.");
        $("#input_privateKey").focus();
        event.stopPropagation();
        return;
    }

    var path = $(form).parent().parent().find(".path").text().trim();
    // Populate query params
    path = buildQuery(path, $(form).find('table'));

    var body = $(form).find("[name='body']").val();

    var message = path;
    if (body != null)
        message += body;

    // force message into UTF-8
    message = unescape(encodeURIComponent(message));

    var hash = CryptoJS.HmacSHA256(message, privateKey);
    var hashInBase64 = CryptoJS.enc.Base64.stringify(hash);

    $(form).find("input[name=hash]").val(hashInBase64);
}

function buildQuery(path, table) {
    var query = "";
    $(table).find('input,select,textarea').each(function() {
        if ($(this).val() != "") {
            if ($($(this).parent().parent().find("td")[3]).text() == "path") {
                path = path.replace("{"+this.name+"}", $(this).val());
            }
            if ($($(this).parent().parent().find("td")[3]).text() == "query") {
                if (query == "")
                    query += "?";
                else
                    query += "&";

                query += this.name + '=' + $(this).val().replace(/\n/g, '&' + this.name + '=');
            }
        }
    });

    return path + query;
}

function visualizeJson(classId)
{
    var jsonString = $('#'+classId).find('code').text();
    var json = JSON.parse(jsonString);

    popupWin = window.open('swagger-custom/jsonVisualizer.html');
    popupWin.focus();

    popupWin.addEventListener('load', function(){
        popupWin.visualize(json);
    }, true);
}
