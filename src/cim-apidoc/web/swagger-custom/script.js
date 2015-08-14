$(document).ready(function() {

  addCustomLogo();
  removeCurl();
  disableHash(); // temporarily
    injectVisualizer();

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

});

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
