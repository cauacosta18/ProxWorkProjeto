import Inicio from "../../components/Inicio/Inicio";
import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";

const InicioPagina = () => {

    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>INÍCIO</h1>
            </div>
            <div className={styles.main}>
                <Inicio />
            </div>
            <Footer />
        </div>      
    );
      
}

export default InicioPagina;