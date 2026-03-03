import Button from '../UI/Button';
import styles from '../../Styles/Perfil.module.css';
import { useEffect, useState } from 'react';
import { Getme, GetPrestador, GetServicos } from '../../services/ProxWorkAPI';
import type { UsuarioCompletoData } from '../../interfaces/UsuarioCompletoData';
import { clearTokens } from '../../services/TokenService';
import { useNavigate } from 'react-router-dom';

interface meData {
    nome: string;
    email: string;
    provedor: boolean;
}

const PerfilPrestador = () => {

    const navigate = useNavigate();

    const [me, setMe] = useState<meData>({
        nome: "",
        email: "",
        provedor: false,
    })

    const fetchMe = async () => {
        try {
            const data = await Getme();
            
            if (data) {
          
                setMe(data);
            
            } else {
            alert("Nenhum prestador encontrado.");
            }
        } catch (error) {
            navigate("/login");
        }
    };

    useEffect(() => {
        fetchMe();

    }, []);
    
    useEffect(() => {
        if (me.email && me.provedor === false) {
            navigate("/perfilcliente");
        }
        }, [me.email, me.provedor]);

    const email = me.email;

    const [servicos, setServicos] = useState<string[]>([]);

    const [UsuarioCompletoData, setUsuarioCompleto] = useState<UsuarioCompletoData>({
        enderecoDto: {
            cidade: '',
            estado: '',
        },
        usuarioDto: {
            nome: '',
            email: '',
            telefone: '',
        },
        pessoaDto: {
            cpf: '',
            dataNascimento: '',
            genero: 0,
        },
        provedorDto: {
            caminhoCarteira: '',
            caminhoCertidao: '',
            whatsapp: '',
            valorMax: 0,
            valorMin: 0,
        }
    });

    const fetchPrestador = async () => {
        try {
            const data = await GetPrestador(email);
            
            if (data) {
            setUsuarioCompleto(data);
            } else {
            alert("Nenhum prestador encontrado.");
            }
        } catch (error) {
            console.error("Erro ao buscar prestadores:", error);
            alert("Erro ao carregar prestadores.");
        }
    };

    const fetchServicos = async () => {
        try {
            const data = await GetServicos(email);
            
            if (data && data.length > 0) {
            setServicos(data);
            } else {
            alert("Nenhum servico encontrado.");
            }
        } catch (error) {
            console.error("Erro ao buscar servicos:", error);
            alert("Erro ao carregar servicos.");
        }
    }

    useEffect(() => {
    if (me.email) {
        fetchPrestador();
        fetchServicos();
    }
    }, [me.email]);

    const sair = async () => {
        try {
            await clearTokens();
            navigate('/login');
        } catch (error) {
            console.error("Erro ao sair:", error);
            alert("Erro ao sair.");
        }
    }

    const atualizar = () => {
        navigate("/atualizarPrestador");
    }

    let genero: String

    if (UsuarioCompletoData.pessoaDto.genero === 1) {
        genero = "Feminino";
    } else if (UsuarioCompletoData.pessoaDto.genero === 2) {
        genero = "Masculino";
    } else {
        genero = "Não definido";
    }

    return (
        <>    
                
            <div className={styles.perfilContainer}>
                {/* container nome cliente*/}
                
                    <ul className={styles.ul}>
                        
                        <li className={styles.perfilHeader}>
                            <p className={styles.perfiltitle}>{UsuarioCompletoData.usuarioDto.nome}</p>
                        </li>
                        <li className={styles.perfilist}>Email: <strong>{UsuarioCompletoData.usuarioDto.email}</strong></li>
                        <li className={styles.perfilist}>Telefone: <strong>{UsuarioCompletoData.usuarioDto.telefone}</strong></li>
                        <li className={styles.perfilist}>CPF: <strong>{UsuarioCompletoData.pessoaDto.cpf}</strong></li>
                        <li className={styles.perfilist}>Whatsapp: <strong>{UsuarioCompletoData.provedorDto?.whatsapp}</strong></li>
                        <li className={styles.perfilist}>Estado: <strong>{UsuarioCompletoData.enderecoDto.estado}</strong></li>
                        <li className={styles.perfilist}>Cidade: <strong>{UsuarioCompletoData.enderecoDto.cidade}</strong></li>
                        <li className={styles.perfilist}>Data de Nascimento: <strong>{UsuarioCompletoData.pessoaDto.dataNascimento}</strong></li>
                        <li className={styles.perfilist}>Gênero: {genero}</li>
                        <li className={styles.perfilist}><strong>Serviços Prestados</strong></li>
                        <ul className={`${styles.check} ${styles.ul}`}>
                            {servicos && servicos.length > 0 ? (
                                servicos.map((item, index) =>(
                                    <li key={index} >{item}</li>
                                ))
                            ): (
                                <p>Nenhum serviço encontrado</p>
                            )
                            
                            } 
                            
                        </ul>
                        <li className={styles.perfilist}>Valor máximo cobrado: <strong>R$ {UsuarioCompletoData.provedorDto.valorMax}</strong></li>
                        <li className={styles.perfilist}>Valor mínimo cobrado: <strong>R$ {UsuarioCompletoData.provedorDto.valorMin}</strong></li>

                        <div className={styles.perfilbuttons}>
                            <Button 
                                type="button"
                                variant="primary" 
                                className={styles.perfilbutton}
                                onClick={atualizar}
                                
                            >
                                Atualizar
                            </Button>
                            <Button 
                                type="button" 
                                variant="secondary" 
                                className={styles.perfilbutton}

                                onClick={sair}
                            >
                                Sair
                            </Button>
                        </div>
                    </ul>
                
            </div>
          
        </>
    );
}

export default PerfilPrestador; // Corrigido o nome da exportação