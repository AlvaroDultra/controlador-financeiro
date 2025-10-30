import { useEffect, useState } from "react";
import {
  listarCategorias,
  criarCategoria,
  removerCategoria,
} from "../api"; // ajuste o caminho

function TransactionModal({ isOpen, onClose, onSave, type }) {
  const [descricao, setDescricao] = useState("");
  const [valor, setValor] = useState("");
  const [categoriaId, setCategoriaId] = useState("");
  const [data, setData] = useState("");
  const [categorias, setCategorias] = useState([]);

  useEffect(() => {
    if (isOpen) {
      listarCategorias()
        .then(setCategorias)
        .catch(() => alert("Erro ao carregar categorias"));
    }
  }, [isOpen]);

  const handleCriarCategoria = async () => {
    const nome = prompt("Digite o nome da nova categoria:");
    if (!nome) return;
    try {
      const nova = await criarCategoria({ nome });
      setCategorias((prev) => [...prev, nova]);
      setCategoriaId(nova.id.toString());
    } catch {
      alert("Erro ao criar categoria");
    }
  };

  const handleRemoverCategoria = async (id) => {
    if (!window.confirm("Tem certeza que deseja remover esta categoria?")) return;
    try {
      await removerCategoria(id);
      setCategorias((prev) => prev.filter((cat) => cat.id !== id));
      if (categoriaId === id.toString()) {
        setCategoriaId(""); // limpa se for a categoria selecionada
      }
    } catch {
      alert("Erro ao remover categoria");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({
      descricao,
      valor: parseFloat(valor),
      data,
      categoria: { id: Number(categoriaId) },
      tipo: type,
    });

    setDescricao("");
    setValor("");
    setCategoriaId("");
    setData("");
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center z-50">
      <div className="bg-white p-6 rounded shadow-lg w-full max-w-md">
        <h2 className="text-xl font-semibold mb-4">
          Nova {type === "receita" ? "Receita" : "Despesa"}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Descrição"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            required
            className="w-full border px-3 py-2 rounded"
          />
          <input
            type="number"
            step="0.01"
            placeholder="Valor"
            value={valor}
            onChange={(e) => setValor(e.target.value)}
            required
            className="w-full border px-3 py-2 rounded"
          />
          
          <div className="space-y-2">
            <div className="flex items-center gap-2">
              <select
                value={categoriaId}
                onChange={(e) => setCategoriaId(e.target.value)}
                required
                className="flex-1 border px-3 py-2 rounded"
              >
                <option value="">Selecione a categoria</option>
                {categorias.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.nome}
                  </option>
                ))}
              </select>
              <button
                type="button"
                onClick={handleCriarCategoria}
                className="px-2 py-1 bg-blue-500 text-white rounded"
              >
                + Cat
              </button>
            </div>

            {/* lista de categorias com botões para remover */}
            <div className="text-sm text-gray-700">
              {categorias.map((cat) => (
                <div key={cat.id} className="flex items-center justify-between">
                  <span>{cat.nome}</span>
                  <button
                    type="button"
                    onClick={() => handleRemoverCategoria(cat.id)}
                    className="text-red-500 hover:underline"
                  >
                    Remover
                  </button>
                </div>
              ))}
            </div>
          </div>

          <input
            type="date"
            value={data}
            onChange={(e) => setData(e.target.value)}
            required
            className="w-full border px-3 py-2 rounded"
          />

          <div className="flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className={`px-4 py-2 rounded text-white ${
                type === "receita" ? "bg-green-500" : "bg-red-500"
              } hover:opacity-90`}
            >
              Salvar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default TransactionModal;
