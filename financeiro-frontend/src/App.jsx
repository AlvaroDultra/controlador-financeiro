import axios from "axios";
import { useEffect, useState } from "react";

import TransactionModal from "./components/TransactionModal";
import {
  getReceitas,
  getDespesas,
  postReceita,
  postDespesa,
  getSaldo,
} from "./api";

function App() {
  const [modalOpen, setModalOpen] = useState(false);
  const [modalType, setModalType] = useState("receita");
  const [transacoes, setTransacoes] = useState([]);
  const [saldo, setSaldo] = useState(0);
  const [modoRemocaoIndividual, setModoRemocaoIndividual] = useState(false);

  const carregarTransacoes = async () => {
    try {
      const [rRec, rDes] = await Promise.all([getReceitas(), getDespesas()]);

      const receitas = (rRec.data || []).map((r) => ({
        id: r.id,
        tipo: "receita",
        data: r.data,
        descricao: r.descricao,
        categoria: r.categoria?.nome || `#${r.categoria?.id ?? ""}`,
        valor: Number(r.valor),
      }));

      const despesas = (rDes.data || []).map((d) => ({
        id: d.id,
        tipo: "despesa",
        data: d.data,
        descricao: d.descricao,
        categoria: d.categoria?.nome || `#${d.categoria?.id ?? ""}`,
        valor: Number(d.valor),
      }));

      const merged = [...receitas, ...despesas].sort(
        (a, b) => new Date(b.data) - new Date(a.data)
      );
      setTransacoes(merged);
    } catch (e) {
      console.error("Erro ao carregar transações:", e);
    }
  };

  const carregarSaldo = async () => {
    try {
      const resp = await getSaldo();
      setSaldo(Number(resp.data));
    } catch (e) {
      console.error("Erro ao carregar saldo:", e);
      alert("Erro ao carregar saldo. Veja o console.");
    }
  };

  const atualizarTudo = async () => {
    await Promise.all([carregarTransacoes(), carregarSaldo()]);
  };

  useEffect(() => {
    atualizarTudo();
  }, []);

  const handleNovaTransacao = (tipo) => {
    setModalType(tipo);
    setModalOpen(true);
  };

  const handleSalvarTransacao = async (nova) => {
    try {
      if (nova.tipo === "receita") {
        await postReceita({
          descricao: nova.descricao,
          valor: nova.valor,
          data: nova.data,
          categoria: nova.categoria,
        });
      } else {
        await postDespesa({
          descricao: nova.descricao,
          valor: nova.valor,
          data: nova.data,
          categoria: nova.categoria,
        });
      }
      await atualizarTudo();
    } catch (e) {
      console.error("Erro ao salvar:", e);
      alert("Erro ao salvar. Veja o console.");
    }
  };

  const handleLimparTudo = async () => {
    if (!confirm("Tem certeza que deseja remover todas as receitas e despesas?")) return;

    try {
      await axios.delete("http://localhost:8080/resumo/clear-all");
      alert("Tudo removido com sucesso!");
      await atualizarTudo();
    } catch (error) {
      alert("Erro ao limpar tudo: " + (error.response?.data || error.message));
      console.error(error);
    }
  };

  const handleRemoverIndividual = async (transacao) => {
    const confirmacao = confirm(`Excluir ${transacao.tipo} "${transacao.descricao}"?`);
    if (!confirmacao) return;

    try {
      const endpoint =
        transacao.tipo === "receita"
          ? `http://localhost:8080/receitas/${transacao.id}`
          : `http://localhost:8080/despesas/${transacao.id}`;

      await axios.delete(endpoint);
      await atualizarTudo();
    } catch (error) {
      alert("Erro ao excluir: " + (error.response?.data || error.message));
      console.error(error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <h1 className="text-3xl font-bold text-center text-blue-600 mb-8">
        Controle Financeiro Pessoal
      </h1>

      <div className="bg-white shadow rounded-lg p-6 mb-6 text-center">
        <p className="text-lg text-gray-600">Saldo Total</p>
        <p className={`text-4xl font-bold ${saldo >= 0 ? "text-green-600" : "text-red-600"}`}>
          R$ {saldo.toFixed(2)}
        </p>
      </div>

      <div className="flex flex-wrap justify-center gap-4 mb-8">
        <button
          className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
          onClick={() => handleNovaTransacao("receita")}
        >
          Nova Receita
        </button>
        <button
          className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
          onClick={() => handleNovaTransacao("despesa")}
        >
          Nova Despesa
        </button>
        <button
          onClick={handleLimparTudo}
          className="bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600"
        >
          Limpar Tudo
        </button>
        <button
          onClick={() => setModoRemocaoIndividual(!modoRemocaoIndividual)}
          className={`${
            modoRemocaoIndividual ? "bg-gray-700" : "bg-blue-500"
          } text-white px-4 py-2 rounded hover:opacity-90`}
        >
          {modoRemocaoIndividual ? "Cancelar Remoção" : "Remover Individualmente"}
        </button>
      </div>

      <div className="overflow-x-auto">
        <table className="min-w-full bg-white shadow rounded-lg">
          <thead>
            <tr className="bg-gray-200 text-gray-600">
              <th className="py-2 px-4 text-left">Data</th>
              <th className="py-2 px-4 text-left">Descrição</th>
              <th className="py-2 px-4 text-left">Categoria</th>
              <th className="py-2 px-4 text-left">Valor</th>
              {modoRemocaoIndividual && (
                <th className="py-2 px-4 text-left">Ação</th>
              )}
            </tr>
          </thead>
          <tbody>
            {transacoes.map((t, idx) => (
              <tr key={idx} className="border-t">
                <td className="py-2 px-4">
                  {new Date(t.data).toLocaleDateString("pt-BR")}
                </td>
                <td className="py-2 px-4">{t.descricao}</td>
                <td className="py-2 px-4">{t.categoria}</td>
                <td
                  className={`py-2 px-4 font-semibold ${
                    t.tipo === "receita" ? "text-green-600" : "text-red-600"
                  }`}
                >
                  {t.tipo === "receita" ? "+" : "-"}R$ {t.valor.toFixed(2)}
                </td>
                {modoRemocaoIndividual && (
                  <td className="py-2 px-4">
                    <button
                      onClick={() => handleRemoverIndividual(t)}
                      className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                    >
                      Excluir
                    </button>
                  </td>
                )}
              </tr>
            ))}
            {transacoes.length === 0 && (
              <tr>
                <td colSpan={modoRemocaoIndividual ? 5 : 4} className="py-6 text-center text-gray-500">
                  Nenhuma transação ainda.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <TransactionModal
        isOpen={modalOpen}
        onClose={() => setModalOpen(false)}
        onSave={handleSalvarTransacao}
        type={modalType}
      />
    </div>
  );
}

export default App;
