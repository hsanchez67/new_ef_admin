Date.prototype.format = function (formatter) {
    if (!formatter || formatter == "") {
        formatter = "yyyy-MM-dd";
    }
    var year = this.getFullYear().toString();
    var month = (this.getMonth() + 1).toString();
    var day = this.getDate().toString();
    var yearMarker = formatter.replace(/[^y|Y]/g, '');
    if (yearMarker.length == 2) {
        year = year.substring(2, 4);
    }
    var monthMarker = formatter.replace(/[^m|M]/g, '');
    if (monthMarker.length > 1) {
        if (month.length == 1) {
            month = "0" + month;
        }
    }
    var dayMarker = formatter.replace(/[^d]/g, '');
    if (dayMarker.length > 1) {
        if (day.length == 1) {
            day = "0" + day;
        }
    }
    return formatter.replace(yearMarker, year).replace(monthMarker, month).replace(dayMarker, day);
}

Date.parseString = function (dateString, formatter) {
    var today = new Date();
    if (!dateString || dateString == "") {
        return today;
    }
    if (!formatter || formatter == "") {
        formatter = "yyyy-MM-dd";
    }
    var yearMarker = formatter.replace(/[^y|Y]/g, '');
    var monthMarker = formatter.replace(/[^m|M]/g, '');
    var dayMarker = formatter.replace(/[^d]/g, '');
    var yearPosition = formatter.indexOf(yearMarker);
    var yearLength = yearMarker.length;
    var year = dateString.substring(yearPosition, yearPosition + yearLength) * 1;
    if (yearLength == 2) {
        if (year < 50) {
            year += 2000;
        }
        else {
            year += 1900;
        }
    }
    var monthPosition = formatter.indexOf(monthMarker);
    var month = dateString.substring(monthPosition, monthPosition + monthMarker.length) * 1 - 1;
    var dayPosition = formatter.indexOf(dayMarker);
    var day = dateString.substring(dayPosition, dayPosition + dayMarker.length) * 1;
    return new Date(year, month, day);
}

Date.prototype.add=function(unit, interval)
{
    var dtTmp = this;
    switch (unit) {
        case 's' :return new Date(Date.parse(dtTmp) + (1000 * interval));
        case 'm' :return new Date(Date.parse(dtTmp) + (60000 * interval));
        case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * interval));
        case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * interval));
        case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * interval));
        case 'M' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth() + interval), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
        case 'y' :return new Date((dtTmp.getFullYear() + interval), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
    }
}