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

function consultarFormasPagamento() {
  $.ajax({
    url: "http://localhost:8080/formas-pagamento",
    type: "get",

    success: function(response) {
      preencherTabelaFormasPagamento(response);
    }
  });
}

function preencherTabelaFormasPagamento(formasPagamento) {
  $("#tabela tbody").remove();

  $.each(formasPagamento, function(i, formaPagamento) {
    var linha = $("<tr>");

    linha.append(
      $("<td>").text(formaPagamento.id),
      $("<td>").text(formaPagamento.descricao)
    );

    linha.appendTo("#tabela");
  })
}

$("#btn-consultar-restaurantes").click(consultarRestaurantes);
$("#btn-fechar-restaurante").click(fecharRestaurantes);
$("#btn-consultar-formas-pagamento").click(consultarFormasPagamento);