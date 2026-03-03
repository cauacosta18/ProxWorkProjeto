import { Link } from 'react-router-dom';
import styles from './Footer.module.css'

const Footer = () => {
    return (
        <>
            <footer className={styles.footer}>
                <div className={styles.footerContent}>
                    <Link to="/cadastrarPrestador">Cadastrar-se como Prestador</Link>
                    <Link to="/cadastrarPessoa">Cadatrar-se como Cliente</Link>
                    <Link to="/inicio" className={styles.footerLink}>Início</Link>
                    <Link to="/suporte" className={styles.footerLink}>Suporte</Link>
                </div>
            </footer>
        </>
    )   
}

export default Footer;