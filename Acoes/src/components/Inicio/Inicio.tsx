import styles from './Inicio.module.css';
import logo from '../../assets/logo.png';

const Inicio = () => {
    return (
        <>
            <div className={styles.content}>
                <section className={styles.heroSection}>
                    <div className={styles.heroContent}>
                        <h2 className={styles.heroTitle}>BEM VINDO</h2>
                        <p className={styles.heroDescription}>
                            Precisa de um serviço? Aqui na PROX WORK é fácil encontrar o que você procura.
                            Com um catálogo completo de prestadores, conectamos você a resolução do seu problema.
                        </p>
                    </div>
                    <div className={styles.centerLogo}>
                        <img className={styles.headerlogo} src={logo} alt="ProxWork Logo" />
                    </div>
                </section>
                
                
            </div>
        </>
    );
};

export default Inicio;