$.fn.serializeJson = function () {
    var serializeObject = {};
    var arr = this.serializeArray();
    $(arr).each(function () {
        if(serializeObject[this.name]){
            if($.isArray(serializeObject[this.name])){
                serializeObject[this.name].put(this.value);
            }else {
                serializeObject[this.name] = [serializeObject[this.name], this.value];
            }
        }else {
            serializeObject[this.name] = this.value;
        }
    });
    return serializeObject;
};