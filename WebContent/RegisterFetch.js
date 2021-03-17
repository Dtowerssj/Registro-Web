var formulario = document.getElementById("registerForm");

formulario.addEventListener('submit', function(e){
    e.preventDefault();
    var formD = new FormData(formulario);

    var data ={
        method: "POST",
        body: formD
    };

    fetch('https://pantalla-registro.herokuapp.com/Register', data)
    .then(respuesta => respuesta.json())
    .then(dataServ => {
        if(dataServ.status == 200){
            window.open("_self");
            alert(dataServ.message);
        }else{
            window.open("_self");
            alert(dataServ.message);
        }
    })
    .catch(error => console.error())
});