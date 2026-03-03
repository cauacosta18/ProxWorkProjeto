import type { enderecoDto } from "./EnderecoData";
import type { pessoaDto } from "./PessoaData";
import type { usuarioDto } from "./UsuarioDataSenha";
import type { ProvedorDto } from "./PrestadorData";

export interface CadastroData {
    enderecoDto: enderecoDto;
    usuarioDto: usuarioDto;
    pessoaDto: pessoaDto;
    provedorDto: ProvedorDto;
}
