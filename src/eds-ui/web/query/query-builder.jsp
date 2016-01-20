<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>EDS Suite</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type='text/javascript' src='/js/joint.min.js'> </script>
<script type='text/javascript' src='/js/splitter.js'> </script>
<script type='text/javascript' src='/js/joint.shapes.pn.min.js'> </script>
<script type='text/javascript' src='/js/joint.shapes.uml.min.js'> </script>
<script type='text/javascript' src='/js/joint.shapes.org.min.js'> </script>
<script type='text/javascript' src='/js/joint.shapes.erd.min.js'> </script>
<link rel="stylesheet" media="all" href="/css/style.css" />
<link rel="stylesheet" media="all" href="/css/joint.min.css" />
<link rel="stylesheet" media="all" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script type="text/javascript" src="/js/jquery-ui.js"></script>

<script type="text/javascript">

$(document).ready(function() {

    $('#main-form').height(window.screen.availHeight-270);

    var h = $('#main-form').height()-50;
    var w = $('#main-form').width();

    $('#main').width(w).height(h).split({orientation:'vertical', limit:100, position:'78%'});

    $( "#accordion" ).accordion({
        heightStyle: "content"

    });

    $( "#accordion" ).draggable();

    $( "#accordion img" ).draggable({ helper:'clone', appendTo: 'body' });


    $( "#paper" ).droppable({

        drop: function( event, ui ) {

            if (ui.draggable.prop('id')=="link1"||ui.draggable.prop('id')=="link2"||ui.draggable.prop('id')=="link3")
                addLink(ui.draggable.prop('id'))
            else if (ui.draggable.prop('id')!="accordion")
                addToChart(ui.draggable.prop('id'), ui.draggable.prop('id'));

        }
    })

    paper.on('cell:pointerdown',
            function(cellView, evt, x, y) {

                if (cellView.model.attr('.eType').text=="component")
                {
                    flowElementClick(cellView.model.id,cellView.model.attr('.id').text,cellView.model.attr('.rank').text,cellView.model.attr('.name').text,cellView.model.attr('.calc').text,cellView.model.attr('.displayName').text,cellView.model.attr('.p1Label').text,cellView.model.attr('.p2Label').text)
                }
            }
    );

    paper.on('cell:pointerdblclick',
            function(cellView, evt, x, y) {

                if (cellView.model.attr('.eType').text=="component")
                {
                    flowElementDblClick(cellView.model.id,cellView.model.attr('.id').text,cellView.model.attr('.rank').text,cellView.model.attr('.name').text,cellView.model.attr('.calc').text,cellView.model.attr('.displayName').text,cellView.model.attr('.p1Label').text,cellView.model.attr('.p2Label').text)
                }
            }
    );


    paper.on('cell:pointerup', function(cellView, evt, x, y) {

        // Find the first element below that is not a link nor the dragged element itself.
        var elementBelow = graph.get('cells').find(function(cell) {
            if (cell instanceof joint.dia.Link) return false; // Not interested in links.
            if (cell.id === cellView.model.id) return false; // The same element as the dropped one.
            if (cell.getBBox().containsPoint(g.point(x, y))) {
                return true;
            }
            return false;
        });

        // If the two elements are connected already, don't
        // connect them again (this is application specific though).
        if (elementBelow && !_.contains(graph.getNeighbors(elementBelow), cellView.model)) {

            graph.addCell(new joint.dia.Link({
                source: { id: cellView.model.id }, target: { id: elementBelow.id },
                attrs: { '.marker-source': { d: 'M 10 0 L 0 5 L 10 10 z' } },
                labels: [
                    { position: .5, attrs: { text: { text: 'narrow results to' } } }
                ]
            }));
            cellView.model.translate(0, 100);
        }
    });

});


function addToChart(id, image)
{
    var xC = event.clientX-40;
    var yC = event.clientY-100;

    var param1 = "";
    var param2 = "";
    var param3 = "";
    var displayName = "";
    var p1Label = "";
    var p2Label = "";
    var calc = "";

    if (id=="patient")
    {
        param1 = "Find patients who";
        param2 = "";
        displayName = "Patients";
        p1Label = "Feature";
        p2Label = "Query Phrase";
    }
    else if (id=="condition")
    {
        param1 = "Have a condition of";
        param2 = "";
        displayName = "Conditions";
        p1Label = "Feature";
        p2Label = "Query Phrase";
    }
    else if (id=="observation")
    {
        param1 = "Have had an observation of";
        param2 = "";
        displayName = "Observations";
        p1Label = "Feature";
        p2Label = "Query Phrase";
    }
    else if (id=="medication")
    {
        param1 = "Are on medication of";
        param2 = "";
        displayName = "Medication";
        p1Label = "Feature";
        p2Label = "Query Phrase";
    }
    else if (id=="encounter")
    {
        param1 = "With an encounter of";
        param2 = "";
        displayName = "Encounters";
        p1Label = "Feature";
        p2Label = "Query Phrase";
    }
    else if (id=="allergy")
    {
        param1 = "With an allergy of";
        param2 = "";
        displayName = "Allergies";
        p1Label = "Feature";
        p2Label = "Query Phrase";
    }
    else if (id=="calc")
    {
        param1 = "Perform Calculation";
        param2 = "";
        displayName = "Calculation";
        p1Label = "Action";
        p2Label = "Calculation Phrase";
    }



    var cell = new joint.shapes.org.Member({
        position: { x: xC  , y: yC },
        attrs: {
            '.card': { fill: 'white', stroke: '#188E83'},
            image: { 'xlink:href': '/img/'+ image+".png" },
            '.rank': { text: param1 }, '.name': { text: param2 },'.id': { text: image },'.displayName': { text: displayName },'.p1Label': { text: p1Label },'.p2Label': { text: p2Label },'.calc': { text: calc },'.eType': { text: "component" },
            text: { 'font-size': 9 }
        },
        size: { width: 210, height: 65 }
    });

    graph.addCell([cell]);


}

function flowElementClick(cellId,id,p1,p2,calc,displayName,p1Label,p2Label)
{
    $( "#f-id").html('<div class="prop-id">'+displayName+'</div>');
    $( "#f-p1").html('<div><div class="prop-label">'+p1Label+':</div><div class="prop-input-div"><input type="text" class="prop-input" onkeyup="changeProp(\''+cellId+'\',this.value,1);" value="'+p1+'"/></div></div>');
    $( "#f-p2").html('<div><div class="prop-label">'+p2Label+':</div><div class="prop-input-div"><textarea style="height:200px" class="prop-input" onkeyup="changeProp(\''+cellId+'\',this.value,2);">'+p2+'</textarea></div></div>');
    $( "#f-p3").html('<div><div class="prop-label">Return Attributes:</div><div class="prop-input-div"><input type="text" class="prop-input" onkeyup="changeProp(\''+cellId+'\',this.value,3);" value="'+calc+'"/></div></div>');

}


function flowElementDblClick(cellId,id,p1,p2,calc,displayName,p1Label,p2Label)
{

    var queryObject = 'GET /ehr/patient/_search<br/>'+
    '{<br/>'+
        '&nbsp;&nbsp;&nbsp;"fields": ["id"],<br/>'+
         '&nbsp;&nbsp;&nbsp;"filter": {<br/>'+
        '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"bool": {<br/>'+
            '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"must": [<br/>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>'+
               '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"match": {<br/>'+
              '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"address.city": "newcastle"<br/>'+
             '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>'+
            '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>'+
           '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>'+
          '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"match": {<br/>'+
         '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"sex": "male"<br/>'+
        '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>'+
       '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>'+
      '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br/>'+
     '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>'+
    '}';


    document.getElementById("queryDialogBody").innerHTML  = p1+' '+p2+'<br/><br/>'+queryObject;

    $( "#queryDialog" ).dialog( { modal: true,
        draggable: false,
        resizable: false,
        width: 600,
        height:600,
        buttons: {
            "Run this query part": function() {
                runQuery();
                $(this).dialog("close");
            }
        },

        position: ['center', 'top'] } );




}

function changeProp(cellId,value,p)
{

    switch(p) {
        case 1:
            graph.getCell(cellId).attr('.rank/text', value.substring(0,20)+'\n'+value.substring(20,40));
            break;
        case 2:
            graph.getCell(cellId).attr('.name/text',  value.substring(0,20)+'\n'+value.substring(20,40));
            break;
        case 3:
            graph.getCell(cellId).attr('.calc/text',  value.substring(0,20)+'\n'+value.substring(20,40));
            break;
    }
}

function addLink(type)
{
    var pn = joint.shapes.pn;

    var xC = event.clientX-250;
    var yC = event.clientY-78;

    var link1 = new pn.Link({
        source: { x: xC+120, y: yC+25 },
        target: { x: xC+300, y: yC+25 },
        attrs: {

            '.marker-target': { d: 'M 10 0 L 0 5 L 10 10 z' },'.eType': { text: "link" }
        },
        labels: [
            { position: .5, attrs: { text: { text: 'narrow results to' } } }
        ]
    });

    var link2 = new pn.Link({
        source: { x: xC+120, y: yC+25 },
        target: { x: xC+300, y: yC+25 },
        attrs: {
            '.marker-source': { d: 'M 10 0 L 0 5 L 10 10 z' },
            '.marker-target': { d: 'M 0 0 L 0 0 L 0 0 z' },'.eType': { text: "link" }

        },
        labels: [
            { position: .5, attrs: { text: { text: 'label' } } }
        ]
    });

    var link3 = new pn.Link({
        source: { x: xC+120, y: yC+25 },
        target: { x: xC+300, y: yC+25 },
        attrs: {
            '.marker-source': { d: 'M 10 0 L 0 5 L 10 10 z' },
            '.marker-target': { d: 'M 10 0 L 0 5 L 10 10 z' },'.eType': { text: "link" }
        },
        labels: [
            { position: .5, attrs: { text: { text: 'label' } } }
        ]
    });

    if (type=="link1")
        graph.addCell(link1)
    else
    if (type=="link2")
        graph.addCell(link2)
    else
    if (type=="link3")
        graph.addCell(link3)

}

function newQuery()
{
    graph.clear();
}


function runQuery() {

    alert("ok");
}


</script>
</head>
<body class="blurBg-false" style="background-color:#EBEBEB">

<form id="main-form" class="solid-blue" style="background-color:#FFFFFF;font-size:12px;font-family:'Roboto',Arial,Helvetica,sans-serif;color:#34495E" >

    <div id="menu" class="menu-header">
        <span><a style='cursor:pointer' onclick="newQuery();" >New Query</a></span>
        <span> &nbsp;&nbsp;|&nbsp;&nbsp;<a style='cursor:pointer' onclick="window.location='help'" >Open Query</a></span>
        <span> &nbsp;&nbsp;|&nbsp;&nbsp;<a style='cursor:pointer' onclick="window.location='help'" >Delete Query</a></span>
        <span> &nbsp;&nbsp;|&nbsp;&nbsp;<a style='cursor:pointer' onclick="window.location='help'" >Query Tester</a></span>
        <span> &nbsp;&nbsp;|&nbsp;&nbsp;<a style='cursor:pointer' onclick="viewResults();" >View Results</a></span>
    </div>
    <div id="main">
        <div id="chart">

            <div id="accordion" style="padding:10px; font-size: 10px; width:210px; height: auto;position: absolute;top: 10; left: 10;">

                <h3>Statements</h3>
                <div id="connector-paper" style="height: auto">
                    <img id="patient" src="/img/patient.png" width="50" height="50" style="cursor: move;padding:5px">
                    <img id="condition" src="/img/condition.png" width="50" height="50" style="cursor: move;padding:5px">
                    <img id="observation" src="/img/observation.png" width="50" height="50" style="cursor: move;padding:5px">
                    <img id="medication" src="/img/medication.png" width="50" height="50" style="cursor: move;padding:5px">
                    <img id="encounter" src="/img/encounter.png" width="50" height="50" style="cursor: move;padding:5px">
                    <img id="allergy" src="/img/allergy.png" width="50" height="50" style="cursor: move;padding:5px">
                </div>
                <h3>Links</h3>
                <div id="links-paper" style="height: auto">
                    <img id="link1" src="/img/link2.png" width="160" height="20" style="cursor: move">
                    <img id="link2" src="/img/link3.png" width="160" height="20" style="cursor: move">
                    <img id="link3" src="/img/link.png" width="160" height="20" style="cursor: move">
                </div>

                <h3>Aggregations & Calculations</h3>
                <div>
                    <img id="calc" src="/img/calc.png" width="50" height="50" style="cursor: move;padding:5px">
                </div>
                <h3>Graphs</h3>
                <div>
                </div>

            </div>
            <div id="paper" style="background-color:white;overflow: scroll;height:100%"></div>

        </div>
        <div id="prop-editor" style="width: 200px">
            <div id="panel-header" class="panel-header">Feature Editor</div>
            <div>
                <div id="f-id"></div>
                <div id="f-p1"></div>
                <div id="f-p2"></div>
                <div id="f-p3"></div>
                <br/><br/>
                <div id="run"> <img id="run_query" src="/img/run.png" width="30" height="30" style="cursor: pointer;padding-left:10px;padding-right:10px;padding-bottom: 20px"><a onclick="runQuery();" style="cursor:pointer; font-size: 18px; font-weight: bold; vertical-align: top; ">Run Query</a> </div>
            </div>
        </div>
    </div>

    <div id="queryDialog" title="View Query Object">
        <div id="queryDialogBody"></div>
    </div>

</form>

<script>
    var graph = new joint.dia.Graph;

    var paper = new joint.dia.Paper({
        el: $('#paper'),
        width: 3000,
        height: 2000,
        gridSize: 2,
        model: graph
    });

    paper.$el.css('background-image', 'url("/img/grid2.png")');

    $('#menu').width($('#main-form').width()-9);

</script>
</body>


</html>
