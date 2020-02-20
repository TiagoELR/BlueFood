function searchRest(categoriaId) {
    var t = document.getElementById("searchType");
    if(categoriaId === null){
        t.value = "Texto";  
    }else{
        t.value = "Categoria";
        document.getElementById("categoriaId").value = categoriaId;
    }
    document.getElementById("form").submit();
}

function setCmd(cmd) {
    document.getElementById("cmd").value = cmd;
    document.getElementById("form").submit();
}
function filterCardapio(categoria) {
    document.getElementById("categoria").value = categoria;
    document.getElementById("filterForm").submit();
}