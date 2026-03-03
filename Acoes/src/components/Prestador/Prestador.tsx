import { useState, useEffect } from 'react';
import Button from '../UI/Button';
import styles from '../../Styles/Perfil.module.css';
import type { UsuarioCompletoData } from '../../interfaces/UsuarioCompletoData';
import { Getme, GetPrestador, GetServicos } from '../../services/ProxWorkAPI';
import { useLocation, useNavigate } from 'react-router-dom';

interface meData {
    nome: string;
    email: string;
    provedor: boolean;
}

const Prestador = () => {

    const navigate = useNavigate()

    const location = useLocation();

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

    const { email } = location.state as { email: string };

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

    useEffect(() => {
            fetchPrestador();
    }, []);

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
            fetchServicos();
    }, []);

    
    // Normaliza o WhatsApp para formato internacional +55XXXXXXXXXXX
    const copiarWhatsapp = async () => {
    const raw = UsuarioCompletoData?.provedorDto?.whatsapp;

    if (!raw) {
        alert('WhatsApp não disponível');
        return;
    }
    try {
        if (navigator.clipboard?.writeText) {
        await navigator.clipboard.writeText(raw);
        } else {
        // Fallback para navegadores antigos
        const textarea = document.createElement('textarea');
        textarea.value = raw;
        textarea.style.position = 'fixed';
        textarea.style.opacity = '0';
        document.body.appendChild(textarea);
        textarea.focus();
        textarea.select();
        document.execCommand('copy');
        document.body.removeChild(textarea);
        }

        alert(`Copiado: ${raw}`);
    } catch (err) {
        console.error('Falha ao copiar:', err);
        alert('Não foi possível copiar. Tente novamente.');
    }


    return (
        <Button
        type="button"
        variant="primary"
        className={styles.perfilbutton}
        onClick={copiarWhatsapp}
        >
        {UsuarioCompletoData?.provedorDto?.whatsapp || 'não'}
        </Button>
    );
    }

    const copiarEmail = async () => {
    const raw = UsuarioCompletoData?.usuarioDto?.email;

    if (!raw) {
        alert('WhatsApp não disponível');
        return;
    }
    try {
        if (navigator.clipboard?.writeText) {
        await navigator.clipboard.writeText(raw);
        } else {
        // Fallback para navegadores antigos
        const textarea = document.createElement('textarea');
        textarea.value = raw;
        textarea.style.position = 'fixed';
        textarea.style.opacity = '0';
        document.body.appendChild(textarea);
        textarea.focus();
        textarea.select();
        document.execCommand('copy');
        document.body.removeChild(textarea);
        }

        alert(`Copiado: ${raw}`);
    } catch (err) {
        console.error('Falha ao copiar:', err);
        alert('Não foi possível copiar. Tente novamente.');
    }


    return (
        <Button
        type="button"
        variant="primary"
        className={styles.perfilbutton}
        onClick={copiarEmail}
        >
        {UsuarioCompletoData?.provedorDto?.whatsapp || 'não'}
        </Button>
    );
    }

    
    return (
        <>
            <div className={styles.perfilContainer}>
                {/* container nome cliente*/}
                <div className={styles.perfiltext}>
                    <ul>
                        <div className={styles.perfilHeader}>
                            <p className={styles.perfiltitle}>{UsuarioCompletoData.usuarioDto.nome}</p>
                        </div>
                        <li className={styles.perfilist}>Email: <strong>{UsuarioCompletoData.usuarioDto.email}</strong></li>
                        <li className={styles.perfilist}>Telefone: <strong>{UsuarioCompletoData.usuarioDto.telefone}</strong></li>
                        <li className={styles.perfilist}>CPF: <strong>{UsuarioCompletoData.pessoaDto.cpf}</strong></li>
                        <li className={styles.perfilist}>Whatsapp: <strong>{UsuarioCompletoData.provedorDto.whatsapp}</strong></li>
                        <li className={styles.perfilist}>Estado: <strong>{UsuarioCompletoData.enderecoDto.estado}</strong></li>
                        <li className={styles.perfilist}>Cidade: <strong>{UsuarioCompletoData.enderecoDto.cidade}</strong></li>
                        <li className={styles.perfilist}>Data de Nascimento: <strong>{UsuarioCompletoData.pessoaDto.dataNascimento}</strong></li>
                        <li className={styles.perfilist}>Gênero: <strong>{UsuarioCompletoData.pessoaDto.genero}</strong></li>
                        <li className={styles.perfilist}>Serviços Prestados:</li>
                        <ul className={styles.ul+`, `+styles.check}>
                            {servicos && servicos.length > 0 ? (
                                servicos.map((item, index) =>(
                                    <li key={index} className={styles.prestadorlist}>{item}</li>
                                ))
                            ): (
                                <p>Nenhum serviço encontrado</p>
                            )
                            
                            } 
                            
                        </ul>
                        <li className={styles.perfilist}>Valor máximo cobrado: <strong>R$ {UsuarioCompletoData.provedorDto.valorMax}</strong></li>
                        <li className={styles.perfilist}>Valor mínimo cobrado: <strong>R$ {UsuarioCompletoData.provedorDto.valorMin}</strong></li>
                        <div className={styles.perfilbuttons}>
                            <p className={styles.perfilist}><strong>Whatsapp:</strong></p>
                            <Button 
                                type="button" 
                                variant="primary" 
                                className={`${styles.perfilbutton} ${styles.copiar}`}
                                onClick={copiarWhatsapp}
                            >
                                {UsuarioCompletoData.provedorDto?.whatsapp || 'não'}
                            </Button>
                            <p className={styles.perfilist}><strong>Email:</strong></p>
                            <Button 
                                type="button" 
                                variant="secondary" 
                                className={`${styles.perfilbutton} ${styles.copiar}`}
                                onClick={copiarEmail}
                            >
                                {UsuarioCompletoData.usuarioDto.email}
                            </Button>
                        </div>
                    </ul>
                </div>
            </div>
                
        </>
    );
}

export default Prestador; 