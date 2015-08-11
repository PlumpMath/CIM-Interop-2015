$(document).ready(function() {

  addCustomLogo();
  removeCurl();
  disableHash(); // temporarily

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
});