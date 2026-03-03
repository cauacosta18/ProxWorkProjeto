import logo from "../../assets/logo.png";
import { Link } from 'react-router-dom';
import styles from './Header.module.css'

const Header = () => {

    return (
        <>
            <header className={styles.homeHeader}>
                <div className={styles.headerContent}>
                    <img className={styles.headerLogo} src={logo} alt="ProxWork Logo" />
                    <nav className={styles.nav}>
                        
                        <Link to="/inicio" className={styles.navLink}>Início</Link>
                        <Link to="/prestadores" className={styles.navLink}>Prestadores</Link>
                        <Link to="/login" className={styles.navLink}>Login</Link>
                        <Link to="/perfilcliente" className={styles.navLink}>Perfil</Link>
                        <Link to="/inicio" className={styles.navLink}>Sobre</Link>

                    </nav>
                </div>
            </header>
        </>
    )
}

export default Header;