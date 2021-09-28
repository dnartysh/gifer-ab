const general_url = './gifer/';

function initGifs() {
    $.ajax({
        url: general_url + 'code',
        method: 'GET',
        complete: function (data) {
            let codes = JSON.parse(data.responseText);
            let select = document.querySelector("#codes_select");
            select.innerHTML = '';
            for (let i = 0; i < codes.length; i++) {
                let option = document.createElement("option");
                option.value = codes[i];
                option.text = codes[i];
                select.insertAdjacentElement("beforeend", option);
            }
        }
    })
}

function loadGifs() {
    let code = $("#codes_select").val();
    $.ajax({
        url: general_url + 'gif/' + code,
        method: 'GET',
        dataType: "json",
        complete: function (data) {
            let content = JSON.parse(data.responseText);
            let img = document.createElement("img");
            let name = document.createElement("p");
            let key = document.createElement("p");
            img.src = content.data.images.original.url;
            let out = document.querySelector("#img");
            out.innerHTML = '';
            out.insertAdjacentElement("afterbegin", img);
            out.insertAdjacentElement("afterbegin", name);
            out.insertAdjacentElement("afterbegin", key);
        }
    })
}
