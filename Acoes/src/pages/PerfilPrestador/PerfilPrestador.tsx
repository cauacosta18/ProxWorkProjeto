import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";
import PerfilPrestador from "../../components/PerfilPrestador/PerfilPrestador";

const PerfilPrestadorPage = () => {
    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>Perfil</h1>
            </div>
            <div className={styles.main}>
                <PerfilPrestador />
            </div>
            <Footer />
        </div>  
    );
};

export default PerfilPrestadorPage;