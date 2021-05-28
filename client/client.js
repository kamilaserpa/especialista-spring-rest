function consultarRestaurantes() {
  $.ajax({
    url: "http://localhost:8080/restaurantes",
    type: "get",
    headers: {
      "X-teste": "Abc"
    },

    success: function(response) {
      $("#conteudo").text(JSON.stringify(response));
    }
  });
}

function fecharRestaurantes() {
  $.ajax({
    url: "http://localhost:8080/restaurantes/1/fechamento",
    type: "put",

    success: function(response) {
      alert("Restaurante foi fechado!");
    }
  });
}

$("#botao-consultar").click(consultarRestaurantes);
$("#botao-fechar").click(fecharRestaurantes);