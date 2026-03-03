import type React from 'react';
import styles from './CardPrestador.module.css';
import { useNavigate } from 'react-router-dom';

interface CardPrestadorProps {
  nome: string;
  cidade: string;
  estado: string;
  email: string;
  valorMax: number;
  valorMin: number;
}

const CardPrestador: React.FC<CardPrestadorProps> = ({nome, cidade, estado, email, valorMax, valorMin}) => {

    const navigate = useNavigate();

    const media = ((valorMax + valorMin)/2).toFixed(2);

    const handleClick = () => {
        navigate(`/prestadorPerfil/`, { state: { email }});
    }
    

    return (
        <>
            <div className={styles.card} onClick={handleClick}>
                <div className={styles.prestadorInfo}>
                    <h3>{nome}</h3>
                    <p>{estado} - {cidade}</p>
                </div>
                <div className={styles.orcamentoInfo}>
                    <p>Média:</p>
                    <p className={styles.orcamento}>R$ {media}</p>
                </div>
            </div>
        </>
    )
}

export default CardPrestador;