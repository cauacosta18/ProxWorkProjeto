import GridPrestadores from "../../components/GridPrestadores/GridPrestadores";
import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";

const Prestadores = () => {

    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>PRESTADORES</h1>
            </div>
            <div className={styles.main}>
                <GridPrestadores />
            </div>
            <Footer />
        </div>      
    );
      
}

export default Prestadores;