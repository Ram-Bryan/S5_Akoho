/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public class ElementCarte {
    private static String elementCreate = "<script>map.addControl(new L.Control.Draw({\n        edit: {\n            featureGroup: drawnItems,\n            poly: {\n                allowIntersection: false\n            }\n        },\n        draw: {\n            polygon: {\n                allowIntersection: false,\n                showArea: true\n            }\n        }\n    }));\n\n    map.on(L.Draw.Event.CREATED, function (event) {\n        var layer = event.layer;\n\n        drawnItems.addLayer(layer);\n    });</script>";
    private static String elementBaseEssai = "\t\t<input id=\"1\" name=\"1\" value=\"-18.9798776\" type=\"hidden\">\n\t\t<input id=\"2\" name=\"2\" value=\"47.5327563\" type=\"hidden\">\t\t\t<div id=\"mapid\"></div>\t\n\t\t\n<script>\nvar map=L.map('mapid');setVueMap(document.getElementById('1').value,document.getElementById('2').value,map); function setVueMap(latti,longi,map){\tmap.setView([latti,longi], 16);\n\n\tL.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {\n\tattribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery \u00ef\u00bf\u00bd <a href=\"https://www.mapbox.com/\">Mapbox</a>',\n\tmaxZoom: 18,\n\tid: 'mapbox.streets',\n\taccessToken: 'YOUR_MAPBOX_ACCESS_TOKEN'\n\t}).addTo(map);\n\tvar osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',\n            osmAttrib = '&copy; <a href=\"http://openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n            osm = L.tileLayer(osmUrl, { maxZoom: 18, attribution: osmAttrib }),\n            drawnItems = L.featureGroup().addTo(map);\n    L.control.layers({\n        'osm': osm.addTo(map),\n        \"google\": L.tileLayer('http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x={x}&y={y}&z={z}', {\n            attribution: 'google'\n        })\n    }, { 'drawlayer': drawnItems }, { position: 'topleft', collapsed: false }).addTo(map);\n}</script>";
    private static String elementBase = "<div class=\"row\">\n<div class=\"col-md-12\">            <div id=\"mapid\" style=\"width: 100%; height: 750px;\"></div></div>    \n        </div>\n<script>\n    var map = L.map('mapid', { maxZoom: 13 }).setView([-18.9987355,47.5301833], 6);\n    var circle=[];var marker=[];var polyline=[];\n\n    var tiles = L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {\n        attribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery \u00c2\u00a9 <a href=\"https://www.mapbox.com/\">Mapbox</a>',\n        maxZoom: 13,\n        id: 'mapbox.streets',\n        accessToken: 'YOUR_MAPBOX_ACCESS_TOKEN'\n    }).addTo(map);\n\n    var osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',\n        osmAttrib = '&copy; <a href=\"http://openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n        osm = L.tileLayer(osmUrl, { maxZoom: 13, attribution: osmAttrib }),\n        drawnItems = L.featureGroup().addTo(map);\n    L.control.layers({\n        'osm': osm.addTo(map),\n        \"google\": L.tileLayer('http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x={x}&y={y}&z={z}', {\n            maxZoom: 13,\n            attribution: 'google'\n        })\n    }, { 'drawlayer': drawnItems }, { position: 'topleft', collapsed: false }).addTo(map);\n\n</script>";
    public static String clickInfo = "<script>\tfunction onMapClick(e) {\n\t\t\tdialog.open();\n\t}\n\tmap.on('click', onMapClick);\n</script>";
    public static String showModal = "<script>\n   dialog.open();    </script>";
    public static String getPosition = "<script>\nmap.on(L.Draw.Event.CREATED,function(e){\n           var layer=e.layer; var type = e.layerType;\n           drawnItems.addLayer(layer);\nconsole.log(type);if(type === 'circle'){var result=\"(\"+layer.getLatLng().lat+\" \"+layer.getLatLng().lng+\",\"+layer.getRadius()+\")\";result=\"CIRCLE\"+result;console.log(result);$('#geom').val(result);}if(type === 'polyline'){var array=layer.getLatLngs();var result=\"(\";for(var i=0;i<array.length;i++){if(i===array.length-1){   result+=array[i].lat+\" \"+array[i].lng+\")\";}else{   result+=array[i].lat+\" \"+array[i].lng+\",\";}}result=\"LINESTRING\"+result;console.log(result);$('#geom').val(result);}if (type === 'rectangle' || type === 'polygon' ) {\nvar array=layer.getLatLngs();var first=\"\";var result=\"(\";for(var i=0;i<array[0].length;i++){if(i===array[0].length-1){   result+=array[0][i].lat+\" \"+array[0][i].lng+\",\"+first+\")\";}else{if(i===0){first= array[0][i].lat+\" \"+array[0][i].lng;}   result+=array[0][i].lat+\" \"+array[0][i].lng+\",\";}}result=\"POLYGON(\"+result+\")\";$('#geom').val(result);}if(type === 'marker' || type === 'circlemarker'){var point=\"POINT(\"+layer.getLatLng().lat+\" \"+layer.getLatLng().lng+\")\";$('#geom').val(point);}        });    </script>";
    public static String legende = "<script>var legend = L.control({ position: \"bottomright\" });\n\t\t\tlegend.onAdd = function(mymap) {\n\t\t\t\tvar div = L.DomUtil.create(\"div\", \"legend\");\n\t\t\t\tdiv.innerHTML += \"<h4>L&eacute;gendes</h4>\";\n\t\t\t\tdiv.innerHTML += '<i style=\"background: red;\"></i><span>Incident</span><br>';\n\t\t\t\tdiv.innerHTML += '<i class=\"icon\" style=\"background-image: url(https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.5.1/images/marker-icon.png);background-repeat: no-repeat;\"></i><span>Personne</span><br>';\n\t\t\t\treturn div;\n\t\t\t}\n\t\t\t  \n\t\t\tlegend.addTo(map);</script>";
    public static String printable = "<script>L.easyPrint({\n      tileLayer: tiles,\n      sizeModes: ['CurrentSize', 'A4Landscape', 'A4Portrait'],\n      filename: 'myMap'\n\t\t}).addTo(map);</script>";
    public static String dialog = "<script>var dialog = L.control.dialog(options)\n              .setContent(\"<p>Hello! Welcome to your nice new dialog box!</p>\")\n              .addTo(map);dialog.open();</script>";

    public static String getElementBaseEssai() {
        return elementBaseEssai;
    }

    public static String getGetPosition() {
        return getPosition;
    }

    public static String getShowModal() {
        return showModal;
    }

    public static String getClickInfo() {
        return clickInfo;
    }

    public static String getElementBase() {
        return elementBase;
    }

    public static String getElementCreate() {
        return elementCreate;
    }
}

