/**
 * Created by Adam on 2014-11-24.
 */

function onMapLoad()
{
    if (isConnected)
    {
        //load the Google API
        var fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src",
        "http://maps.googleapis.com/maps/api/js?sensor=true&callback=" +
                "getGeolocation");
        document.getElementsByTagName("head")[0].appendChild(fileref);
    } else
    {
        alert("Must be connected to the Internet");
    }
}

function getGeolocation()
{
    //get the user's gps coordinates and display map
    var options =
    {
        maximumAge: 3000,
        timeout: 5000,
        enableHighAccuracy: true
    };
    navigator.geolocation.getCurrentPosition(loadMap, geoError, options);
}

function loadMap(position)
{
    var latlng = new google.maps.LatLng(
        position.coords.latitude, position.coords.longitude
    );

    var myOptions =
    {
        zoom: 8,
        center: new google.maps.LatLng(51.107897, 17.033063),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var mapObj = document.getElementById("map_canvas");
    var map = new google.maps.Map(mapObj, myOptions);

    var marker = new google.maps.Marker
    ({
        position: latlng,
        map: map,
        title: "You"
    });
}

function geoError(error)
{
    alert('code: ' + error.code + '\n' +
    'message: ' + error.message + '\n');
}