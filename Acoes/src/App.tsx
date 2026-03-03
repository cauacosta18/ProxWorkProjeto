import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import './App.css'
import CadastrarPrestador from './pages/CadastrarPrestador/CadastrarPrestador';
import AtualizarPrestador from './pages/AtualizarPrestador/AtualizarPrestador';
import CadastrarPessoa from './pages/CadastrarPessoa/CadastrarPessoa';
import AtualizarPessoa from './pages/AtualizarPessoa/AtualizarPessoa';
import Inicio from './pages/InicioPagina/InicioPagina';
import Prestadores from './pages/Prestadores/Prestadores';
import PerfilCliente from './pages/PerfilCliente/PerfilCliente';
import PerfilPrestador from './pages/PerfilPrestador/PerfilPrestador';
import Prestador from './pages/Prestador/Prestador';
import AreaLogin from './pages/AreaLogin/AreaLogin';

function App() {

  return (
    <Router>
      <Routes>
        <Route path='/' element={<Inicio/>}/>
        <Route path='/prestadores' element={<Prestadores/>}/>
        <Route path='/inicio' element={<Inicio/>}/>
        <Route path='/cadastrarPessoa' element={<CadastrarPessoa/>} />
        <Route path='/atualizarPessoa' element={<AtualizarPessoa/>} />
        <Route path='/cadastrarPrestador' element={<CadastrarPrestador/>} />
        <Route path='/atualizarPrestador' element={<AtualizarPrestador/>} />
        <Route path='/login' element={<AreaLogin/>} />
        <Route path="*" element={<h1>⚠️404 Not Found⚠️</h1>} />
        <Route path="/suporte" element={<h1>⚠️404 Not Found⚠️<br /> <br />👉Suporte está em descanso no momento aguarde 48h, e entre em contato novamente😉!</h1>} />
        <Route path="/perfilCliente" element={<><PerfilCliente /></>} />
        <Route path="/perfilPrestador" element={<><PerfilPrestador /></>} />
        <Route path="/prestadorPerfil" element={<><Prestador /></>} />  
      </Routes>
    </Router>
  )
}

export default App
