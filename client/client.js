function consultarRestaurantes() {
  $.ajax({
    url: "http://localhost:8080/restaurantes",
    type: "get",
    headers: {
      "X-teste": "Abc"
    },

    success: function (response) {
      $("#conteudo").text(JSON.stringify(response));
    }
  });
}

function fecharRestaurantes() {
  $.ajax({
    url: "http://localhost:8080/restaurantes/1/fechamento",
    type: "put",

    success: function (response) {
      alert("Restaurante foi fechado!");
    }
  });
}

function consultarFormasPagamento() {
  $.ajax({
    url: "http://localhost:8080/formas-pagamento",
    type: "get",

    success: function (response) {
      preencherTabelaFormasPagamento(response);
    }
  });
}

function cadastrarFormasPagamento() {
  var formaPagamentoJson = JSON.stringify({
    "descricao": $("#campo-descricao").val()
  });

  $.ajax({
    url: "http://localhost:8080/formas-pagamento",
    type: "post",
    data: formaPagamentoJson,
    contentType: "application/json",

    success: function (response) {
      alert("Forma de pagamento adicionada!")
      consultarFormasPagamento(response);
    },

    error: function (error) {
      if (error.status = 400) {
        var problem = JSON.parse(error.responseText);
        alert(problem.userMessage);
      } else {
        alert("Erro ao cadastrar forma de pagamento.");
      }
    }
  });
}

function excluirFormaPagamento(formaPagamento) {
  var formaPagamentoJson = JSON.stringify({
    "descricao": $("#campo-descricao").val()
  });

  $.ajax({
    url: "http://localhost:8080/formas-pagamento/" + formaPagamento.id,
    type: "delete",
    data: formaPagamentoJson,
    contentType: "application/json",

    success: function (response) {
      alert("Forma de pagamento excluída com sucesso!")
      consultarFormasPagamento(response);
    },

    error: function (error) {
      // tratando todos os error da categoria 4xx
      if (error.status >=400 || error.status <= 499) {
        var problem = JSON.parse(error.responseText);
        alert(problem.userMessage);
      } else {
        alert("Erro ao remover forma de pagamento.");
      }
    }
  });
}

function preencherTabelaFormasPagamento(formasPagamento) {
  $("#tabela tbody tr").remove();

  $.each(formasPagamento, function (i, formaPagamento) {
    var linha = $("<tr>");

    var linkAcao = $("<a href='#'>")
      .text("Excluir")
      .click(function (event) {
        event.preventDefault();
        excluirFormaPagamento(formaPagamento)
      });

    linha.append(
      $("<td>").text(formaPagamento.id),
      $("<td>").text(formaPagamento.descricao),
      $("<td>").append(linkAcao)
    );

    linha.appendTo("#tabela");
  })
}

$("#btn-consultar-restaurantes").click(consultarRestaurantes);
$("#btn-fechar-restaurante").click(fecharRestaurantes);
$("#btn-consultar-formas-pagamento").click(consultarFormasPagamento);
$("#btn-cadastrar-forma-pagamento").click(cadastrarFormasPagamento);