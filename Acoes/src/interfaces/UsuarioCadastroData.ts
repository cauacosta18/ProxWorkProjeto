export interface UsuarioCadastroData {
    nome: string;
    email: string;
    senha: string;
    telefone: string;
    cpf: string;
    dataNascimento: string;
    genero: number;
    cidade: string;
    estado: string;
    caminhoCarteira: string;
    caminhoCertidao: string;
    whatsapp: string;
    servicos: Array<number>;
    valorMax: number;
    valorMin: number;
}