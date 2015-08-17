$(document).ready(function() {

  addCustomLogo();
  removeCurl();
  disableHash();
  injectVisualizer();
  injectHashGeneration();

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
            $(form).find("input").each(function() {
                $(this).change(function() {
                    generateHash(form);
                });
            });
            $(form).find("textarea").each(function() {
                $(this).change(function() {
                    generateHash(form);
                });
            });
        });
    }

});



function generateHash(form) {
    var privateKey = $("#input_privateKey").val();
    var path = $(form).parent().parent().find(".path").text().trim()+"/";
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
