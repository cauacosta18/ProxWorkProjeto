import styles from './Hometwo.module.css';
import logo from '../../assets/logo.png';
const Home = () => {
    return (
        <div className={styles.homePage}>            
            {/* Conteúdo Principal */}
            <main className={styles.mainContent}>
                <section className={styles.heroSection}>
                    <div className={styles.heroContent}>
                        <h2 className={styles.heroTitle}>BEM-VINDO</h2>
                        <p className={styles.heroDescription}>
                            Precisa de um serviço? Aqui na PROX WORK é fácil encontrar o que você procura.
                            Com um catálogo completo de prestadores, conectamos você à resolução do seu problema.
                        </p>
                    </div>
                </section>

                {/* Logo no centro */}
                <div className={styles.centerLogo}>
                    <img className={styles.headerlogo} src={logo} alt="ProxWork Logo" />
                </div>
            </main> 
        </div>
    );
};

export default Home;