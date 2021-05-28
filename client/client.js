function consultarRestaurantes() {
  $.ajax({
    url: "http://localhost:8080/restaurantes",
    type: "get",

    success: function(response) {
      $("#conteudo").text(response);
    }
  });
}

$("#botao").click(consultarRestaurantes);