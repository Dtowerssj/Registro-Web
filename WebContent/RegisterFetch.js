var form = document.getElementById("register");

form.addEventListener('submit', function(e){
    e.preventDefault();
    var fd = new FormData(form);

    var datos ={
        method: "POST",
        body: fd
    }

    fetch('https://pantalla-registro.herokuapp.com/Register', datos)
    .then(res => res.json())
    .then(data => {
        if(data.status == 200){
            window.open("_self");
            alert(data.message);
        }else{
            window.open("_self");
            alert(data.message);
        }
    })
    .catch(error => console.error())
});