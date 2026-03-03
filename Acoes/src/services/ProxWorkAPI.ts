import axios from "axios";
import type { UsuarioCompletoData } from "../interfaces/UsuarioCompletoDataSenha";
import type { UsuarioCadastroData } from "../interfaces/UsuarioCadastroData";
import type { ServicosData } from "../interfaces/ServicosData";
import type { UsuarioUpdateData } from "../interfaces/UsuarioUpdateData";
import type { FiltroData } from "../interfaces/FiltroData";

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: {
        'Content-Type': 'application/json'
    }
})


// Ex.: rotas públicas do seu backend
const PUBLIC_PATHS = [
  '/provedor/cadastrarProvedor',
  '/pessoa/cadastrarPessoa',
  '/servicoPrestado/cadastrarServicoPrestado',
  '/auth/login',
  '/auth/refresh',
  '/provedor/listarProvedores',
  '/servico/listarservicos',
  // importante: nunca tente refresh no refresh
  // adicione outras se necessário
];


// Helper: checa se a URL do request é pública
function isPublicUrl(url?: string): boolean {
  if (!url) return false;
  try {
    // Caso use baseURL do axios, `config.url` pode ser só o path
    const pathname = new URL(url, apiClient.defaults.baseURL || window.location.origin).pathname;
    return PUBLIC_PATHS.some((p) => pathname.startsWith(p));
  } catch {
    // Se não conseguir parsear, faça uma checagem simples
    return PUBLIC_PATHS.some((p) => url.startsWith(p));
  }
}




apiClient.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('accessToken');

  // Se a rota é pública, não anexar Authorization
  if (!isPublicUrl(config.url)) {
    if (token) {
      // garanta objeto headers
      config.headers = config.headers ?? {};
      (config.headers as Record<string, string>).Authorization = `Bearer ${token}`;
    }
  }

  // Evite cache em POST se quiser
  // (config.method === 'post') && (config.headers['Cache-Control'] = 'no-cache');

  return config;
});


apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    // Proteção: error.response pode não existir (erros de rede / CORS)
    const status = error?.response?.status;
    const originalConfig = error?.config;

    // Se não há resposta, rejeite (pode ser network/CORS)
    if (!status || !originalConfig) {
      return Promise.reject(error);
    }

    // Se a rota é pública, não faça refresh; apenas repasse o erro
    if (isPublicUrl(originalConfig.url)) {
      return Promise.reject(error);
    }

    // Evitar loop: marque que já tentou refresh uma vez
    if (status === 401 && !originalConfig._retry) {
      originalConfig._retry = true;

      const refreshToken = sessionStorage.getItem('refreshToken');
      if (!refreshToken) {
        // Sem refresh token: não há o que fazer; trate como não autenticado
        // Opcional: limpar tokens
        // sessionStorage.removeItem('accessToken');
        // sessionStorage.removeItem('refreshToken');
        window.location.href = `/login?`;
        return Promise.reject(error);
      }

      try {
        // Use o mesmo client ou axios “cru”, mas cuidado com baseURL
        const refreshResponse = await axios.post(
          'http://localhost:8080/auth/refresh',
          { refreshToken },
          { withCredentials: false } // ajuste conforme seu backend
        );

        const newAccessToken = refreshResponse.data?.accessToken;
        if (!newAccessToken) {
          return Promise.reject(error);
        }

        sessionStorage.setItem('accessToken', newAccessToken);

        // Reaplique auth header na requisição original (não pública)
        originalConfig.headers = originalConfig.headers ?? {};
        originalConfig.headers.Authorization = `Bearer ${newAccessToken}`;

        // Refaça a chamada original
        return apiClient.request(originalConfig);
      } catch (refreshErr) {
        // Refresh falhou: limpe tokens e, opcionalmente, direcione ao login (manual)
        // sessionStorage.removeItem('accessToken');
        // sessionStorage.removeItem('refreshToken');
        // Não redirecione automaticamente; deixe a UI decidir
        return Promise.reject(refreshErr);
      }
    }

    // Para outros casos ou 401 já tentado: repasse
    return Promise.reject(error);
  }
);


export const Getme = async () => {
    try{
        const response = await apiClient.get(`/auth/me`);
        return response.data;
        
    } catch (error) {
        console.error("Erro ao buscar:", error);
        throw error;
    }
}

export const GetPrestadores = async () => {
    
    try{
        const response = await apiClient.get(`/provedor/listarProvedores`);
        return response.data;
        
    } catch (error) {
        console.error("Erro ao buscar prestadores:", error);
        throw error;
    }
}

export const GetPrestador = async (email: string) => {

    try{
        const response = await apiClient.get(`/provedor/encontrarProvedor`, {
            params: {email}
        });
        return response.data;
        
    } catch (error) {
        console.error("Erro ao buscar prestadores:", error);
        throw error;
    }
}

export const GetServicos = async (email: string) => {

    try{
        const response = await apiClient.get(`/servicoPrestado/listarServicosPrestadosPorPrestador`, {
            params: {email}
        });
        return response.data;
        
    } catch (error) {
        console.error("Erro ao buscar servicos:", error);
        throw error;
    }
}

export const GetPessoa = async (email:string) => {
    try {
        const response = await apiClient.get(`/pessoa/encontrarPessoa`, {
            params: {email}
        })
        return response.data;
    } catch (error) {
        console.error("Erro ao buscar pessoa:", error);
        throw error;
    }
}

export const UpdatePessoaAPI = async (update: UsuarioUpdateData, email: String) => {
    try {
        const pessoa: UsuarioCompletoData = {
            usuarioDto: {
                nome: update.nome,
                email: update.email,
                telefone: update.telefone,
                senha: update.senha,
            },
            pessoaDto: {
                cpf: update.cpf,
                dataNascimento: update.dataNascimento,
                genero: update.genero,
            },
            provedorDto: {
                caminhoCarteira: '',
                caminhoCertidao: '',
                whatsapp: '',
                valorMax: 0,
                valorMin: 0,
            },
            enderecoDto: {
                cidade: update.cidade,
                estado: update.estado,
            }
        }
        console.log(pessoa.pessoaDto.dataNascimento)
        const response = await apiClient.put(`/pessoa/atualizarPessoa`, pessoa, {
            params: {email}
        });

        
        if (response.status !== 201 && response.status !== 200) {
            // Se seu backend sempre devolve 201 Created no sucesso, pode manter só 201.
            throw new Error("Falha ao cadastrar prestador.");
        }
        
        
        return response;

    } catch (error) {
        console.error("Erro ao cadastrar prestador:", error);
        alert("Erro ao cadastrar usuário");
        throw error;
    }
}

export const CadastroPessoaAPI = async (cadastro: UsuarioCadastroData) => {
    try {
        const pessoa: UsuarioCompletoData = {
            usuarioDto: {
                nome: cadastro.nome,
                email: cadastro.email,
                telefone: cadastro.telefone,
                senha: cadastro.senha,
            },
            pessoaDto: {
                cpf: cadastro.cpf,
                dataNascimento: cadastro.dataNascimento,
                genero: cadastro.genero,
            },
            provedorDto: {
                caminhoCarteira: '',
                caminhoCertidao: '',
                whatsapp: cadastro.whatsapp,
                valorMax: cadastro.valorMax,
                valorMin: cadastro.valorMin,
            },
            enderecoDto: {
                cidade: cadastro.cidade,
                estado: cadastro.estado,
            }
        }
        
        const response = await apiClient.post(`/pessoa/cadastrarPessoa`, pessoa);

        
        if (response.status !== 201 && response.status !== 200) {
            // Se seu backend sempre devolve 201 Created no sucesso, pode manter só 201.
            throw new Error("Falha ao cadastrar pessoa.");
        }
                
        return response;

    } catch (error) {
        console.error("Erro ao cadastrar pessoa:", error);
        alert("Erro ao cadastrar usuário");
        throw error;
    }
}

export const UpdatePrestadorAPI = async (update: UsuarioUpdateData, email: String) => {
    try {
        const prestador: UsuarioCompletoData = {
            usuarioDto: {
                nome: update.nome,
                email: update.email,
                telefone: update.telefone,
                senha: update.senha,
            },
            pessoaDto: {
                cpf: update.cpf,
                dataNascimento: update.dataNascimento,
                genero: update.genero,
            },
            provedorDto: {
                caminhoCarteira: '',
                caminhoCertidao: '',
                whatsapp: update.whatsapp,
                valorMax: update.valorMax,
                valorMin: update.valorMin,
            },
            enderecoDto: {
                cidade: update.cidade,
                estado: update.estado,
            }
        }
        const servicos: ServicosData = {
            usuarioEmail: update.email,
            servicos: update.servicos,
        }
        console.log(prestador.pessoaDto.dataNascimento)
        const response = await apiClient.put(`/provedor/atualizarProvedor`, prestador, {
            params: {email}
        });

        
        if (response.status !== 201 && response.status !== 200) {
            // Se seu backend sempre devolve 201 Created no sucesso, pode manter só 201.
            throw new Error("Falha ao cadastrar prestador.");
        }
        
        const response2 = await apiClient.put(`/servicoPrestado/atualizarServicoPrestado`, servicos);
        
        return {
        prestador: response,
        servico: response2,
        };

    } catch (error) {
        console.error("Erro ao cadastrar prestador:", error);
        alert("Erro ao cadastrar usuário");
        throw error;
    }
}

export const CadastroPrestadorAPI = async (cadastro: UsuarioCadastroData) => {
    try {
        const prestador: UsuarioCompletoData = {
            usuarioDto: {
                nome: cadastro.nome,
                email: cadastro.email,
                telefone: cadastro.telefone,
                senha: cadastro.senha,
            },
            pessoaDto: {
                cpf: cadastro.cpf,
                dataNascimento: cadastro.dataNascimento,
                genero: cadastro.genero,
            },
            provedorDto: {
                caminhoCarteira: '',
                caminhoCertidao: '',
                whatsapp: cadastro.whatsapp,
                valorMax: cadastro.valorMax,
                valorMin: cadastro.valorMin,
            },
            enderecoDto: {
                cidade: cadastro.cidade,
                estado: cadastro.estado,
            }
        }
        const servicos: ServicosData = {
            usuarioEmail: cadastro.email,
            servicos: cadastro.servicos,
        }
        const response = await apiClient.post(`/provedor/cadastrarProvedor`, prestador);

        
        if (response.status !== 201 && response.status !== 200) {
            // Se seu backend sempre devolve 201 Created no sucesso, pode manter só 201.
            throw new Error("Falha ao cadastrar prestador.");
        }
        
        const response2 = await apiClient.post(`/servicoPrestado/cadastrarServicoPrestado`, servicos);
        
        return {
        prestador: response,
        servico: response2,
        };

    } catch (error) {
        console.error("Erro ao cadastrar prestador:", error);
        alert("Erro ao cadastrar usuário");
        throw error;
    }
}

export const GetAllServicosAPI = async () => {
    try {

        const response = await apiClient.get(`/servico/listarServicos`);
        console.log(response.data);
        return response.data;

    } catch (error) {
        console.error("Erro ao buscar serviços:", error);
        alert("Erro ao buscar serviços");
        throw error;
    }
}

export const GetPrestadoresFiltro = async (filtro: FiltroData) => {
    try{
        const response = await apiClient.post(`/provedor/listarProvedoresFiltro`, filtro);
        return response;
        
    } catch (error) {
        console.error("Erro ao buscar prestadores:", error);
        throw error;
    }
}




