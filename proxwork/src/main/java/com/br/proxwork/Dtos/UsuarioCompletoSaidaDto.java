package com.br.proxwork.Dtos;

public class UsuarioCompletoSaidaDto {
    private UsuarioSaidaDto usuarioDto;
    private EnderecoDto enderecoDto;
    private PessoaDto pessoaDto;
    private ProvedorDto provedorDto;

    public UsuarioCompletoSaidaDto() {
    }

    public UsuarioCompletoSaidaDto(UsuarioSaidaDto usuarioDto, EnderecoDto enderecoDto, PessoaDto pessoaDto,
            ProvedorDto provedorDto) {
        this.usuarioDto = usuarioDto;
        this.enderecoDto = enderecoDto;
        this.pessoaDto = pessoaDto;
        this.provedorDto = provedorDto;
    }

    public UsuarioSaidaDto getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(UsuarioSaidaDto usuarioDto) {
        this.usuarioDto = usuarioDto;
    }

    public EnderecoDto getEnderecoDto() {
        return enderecoDto;
    }

    public void setEnderecoDto(EnderecoDto enderecoDto) {
        this.enderecoDto = enderecoDto;
    }

    public PessoaDto getPessoaDto() {
        return pessoaDto;
    }

    public void setPessoaDto(PessoaDto pessoaDto) {
        this.pessoaDto = pessoaDto;
    }

    public ProvedorDto getProvedorDto() {
        return provedorDto;
    }

    public void setProvedorDto(ProvedorDto provedorDto) {
        this.provedorDto = provedorDto;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
