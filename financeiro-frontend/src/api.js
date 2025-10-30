import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // backend na 8080
});

export const getReceitas = () => api.get("/receitas");
export const getDespesas = () => api.get("/despesas");

export const postReceita = (payload) => api.post("/receitas", payload);
export const postDespesa = (payload) => api.post("/despesas", payload);

export const getSaldo = () => api.get("/resumo/saldo"); // opcional, se quiser exibir o saldo do backend

export const listarCategorias = async () => {
  const res = await api.get("/categorias");
  return res.data;
};

export const criarCategoria = async (categoria) => {
  const res = await api.post("/categorias", categoria);
  return res.data;
};
export const removerCategoria = async (id) => {
  await api.delete(`/categorias/${id}`);
};