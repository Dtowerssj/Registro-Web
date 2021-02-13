var form = document.getElementById("form");
var botom = document.getElementById("boton");

form.addEventListener('submit', function(e){
    e.preventDefault();
    var fd = new FormData(form);

    var datos ={
        method: "POST",
        body: fd
    }

    fetch('http://localhost:8080/RegistroWebII/', datos)
    .then(res => res.json())
    .then(data => {
        if(data.status == 200){
            window.open("http://localhost:8080/si.html", "_self");
            alert(data.message);
        }else{
            window.open("http://localhost:8080/no.html", "_self");
            alert(data.message);
        }
    })
    .catch(error => console.error()),
});