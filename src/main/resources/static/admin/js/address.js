const host = "https://provinces.open-api.vn/api/";
var callAPI = (api) => {
    return axios.get(api)
        .then((response) => {
            renderData(response.data, "province");
        });
}
callAPI('https://provinces.open-api.vn/api/?depth=1');
var callApiDistrict = (api) => {
    return axios.get(api)
        .then((response) => {
            renderData(response.data.districts, "district");
        });
}
var callApiWard = (api) => {
    return axios.get(api)
        .then((response) => {
            renderData(response.data.wards, "ward");
        });
}

var renderData = (array, select) => {
    let row = '<option disable value="0">Chọn</option>';
    array.forEach(element => {
        row += `<option value="${element.code}">${element.name}</option>`
    });
    document.querySelector("#" + select).innerHTML = row;
}

$("#province").change(() => {
    callApiDistrict(host + "p/" + $("#province").val() + "?depth=2");
});
$("#district").change(() => {
    callApiWard(host + "d/" + $("#district").val() + "?depth=2");
});
$("#ward").change(() => {
    printResult();
});

var printResult = () => {
    if ($("#district").val() != "" && $("#province").val() != "" &&
        $("#ward").val() != "") {
	
		let tinh = $("#province option:selected").text();
		$("#tinh").val(tinh);
		
		let quan = $("#district option:selected").text();
		$("#quan").val(quan);
		
		let phuong = $("#ward option:selected").text();
		$("#phuong").val(phuong);
    }
}

