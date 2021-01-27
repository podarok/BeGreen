metadata {
    definition (name: "Begreen AirHome", namespace: "hubitat", author: "Andrii Podanenko") {
        capability "Carbon Dioxide Measurement"
        capability "Relative Humidity Measurement"
        capability "Temperature Measurement"
        capability "Pressure Measurement"
        attribute "attribHtml", "String"
        command "setCarbonDioxide", ["Number"]
        command "setRelativeHumidity", ["Number"]
        command "setTemperature", ["Number"]
        command "setPressure", ["Number"]
    }
    preferences {
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
    }
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

def installed() {
    log.warn "installed..."

    runIn(1800,logsOff)
}

def updated() {
    log.info "updated..."
    log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
    if (logEnable) runIn(1800,logsOff)
}

def parse(String description) {
}




def setCarbonDioxide(CO2) {
    def descriptionText = "${device.displayName}  Carbon Dioxide is ${CO2} ppm"
    if (txtEnable) log.info "${descriptionText}"
    sendEvent(name: "carbonDioxide", value: CO2, descriptionText: descriptionText, unit: "ppm")
    sendEvent(name: "attribHtml", value: "<p>Pressure: ${device.currentValue("pressure")}</p><p>Temperature: ${device.currentValue("temperature")}</p><p>Humidity: ${device.currentValue("humidity")}</p><p>CO2: ${device.currentValue("carbonDioxide")}</p>");

}

def setRelativeHumidity(humid) {
    def descriptionText = "${device.displayName} is ${humid}% humidity"
    if (txtEnable) log.info "${descriptionText}"
    sendEvent(name: "humidity", value: humid, descriptionText: descriptionText, unit: "RH%")
    sendEvent(name: "attribHtml", value: "<p>Pressure: ${device.currentValue("pressure")}</p><p>Temperature: ${device.currentValue("temperature")}</p><p>Humidity: ${device.currentValue("humidity")}</p><p>CO2: ${device.currentValue("carbonDioxide")}</p>");

}

def setTemperature(temp) {
    def unit = "°${location.temperatureScale}"
    def descriptionText = "${device.displayName} is ${temp}${unit}"
    if (txtEnable) log.info "${descriptionText}"
    sendEvent(name: "temperature", value: temp, descriptionText: descriptionText, unit: unit)
    sendEvent(name: "attribHtml", value: "<p>Pressure: ${device.currentValue("pressure")}</p><p>Temperature: ${device.currentValue("temperature")}</p><p>Humidity: ${device.currentValue("humidity")}</p><p>CO2: ${device.currentValue("carbonDioxide")}</p>");

}

def setPressure(Pressure) {
    def descriptionText = "${device.displayName}  Pressure is ${Pressure} mmhg"
    if (txtEnable) log.info "${descriptionText}"
    sendEvent(name: "pressure", value: Pressure, descriptionText: descriptionText, unit: "mmhg")
    sendEvent(name: "attribHtml", value: "<p>Pressure: ${device.currentValue("pressure")}</p><p>Temperature: ${device.currentValue("temperature")}</p><p>Humidity: ${device.currentValue("humidity")}</p><p>CO2: ${device.currentValue("carbonDioxide")}</p>");
}
