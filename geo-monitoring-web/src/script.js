const serverBaseUrl = "http://164.90.184.120:8081"
const refreshInMinutes = 1

function refresh() {
    console.log("Refreshing...")

    let now = new Date()
    let accessToken = document.getElementById('token').value
    localStorage.setItem("token", accessToken)
    let intervalInHours = document.getElementById('interval').value
    let object = document.getElementById('object').value
    if (object === "ALL") {
        objectIds = null
    } else {
        objectIds = [object]
    }

    fetch(serverBaseUrl + "/points/search", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": accessToken,
        },
        body: JSON.stringify({
            "objectIds": objectIds,
            "utcDateTimeRange": {
                "from": new Date(now - intervalInHours * 60 * 60 * 1000),
                "to": now
            },
        })
    })
        .then(response => response.json())
        .then(draw)
}

function draw(geoPoints) {
    cleanMap()
    let geoPointsByObjectId = Map.groupBy(geoPoints, geoPoint => geoPoint.objectId)
    geoPointsByObjectId.forEach((geoPoints, objectId) => {
        let coordinates = geoPoints.sort(geoPoint => geoPoint.utcDateTime)
            .map(geoPoint => [geoPoint.latitude, geoPoint.longitude])
        let lastCoordinates = coordinates[coordinates.length - 1]
        L.polyline(coordinates).addTo(features)
        L.marker(lastCoordinates).addTo(features).bindPopup(objectId).openPopup()
    })
    map.fitBounds(features.getBounds(), {padding: [20, 20]})
    console.log("Refreshed")
}

function cleanMap() {
    features.clearLayers()
}

let map = L.map('map').setView([60, 30], 10)
let features = L.featureGroup().addTo(map);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map)

document.getElementById("token").value = localStorage.getItem("token")
refresh()
setInterval(() => {
    let auto = document.getElementById('auto').checked
    if (auto) refresh()
}, refreshInMinutes * 60 * 1000)
