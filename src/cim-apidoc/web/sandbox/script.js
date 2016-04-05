function customScriptInitialize(){

    removeCurl();
    disableHash();
    injectHashGeneration();
    customizeResponseModelTable();

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
