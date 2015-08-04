function visualizeJson(classId)
{
	var jsonString = $('#'+classId).find('code').text();
  var json = JSON.parse(jsonString);

  popupWin = window.open('swagger-localised/jsonVisualizer.html');
  popupWin.focus();

  popupWin.addEventListener('load', function(){
		popupWin.visualize(json);
    }, true);
}
		